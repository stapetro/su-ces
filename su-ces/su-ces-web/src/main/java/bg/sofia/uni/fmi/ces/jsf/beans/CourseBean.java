package bg.sofia.uni.fmi.ces.jsf.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.persistence.EntityManager;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.course.CourseAssessment;
import bg.sofia.uni.fmi.ces.model.course.Grade;
import bg.sofia.uni.fmi.ces.model.course.Lecturer;
import bg.sofia.uni.fmi.ces.model.course.Semester;
import bg.sofia.uni.fmi.ces.model.course.Specialty;
import bg.sofia.uni.fmi.ces.model.facade.course.CourseAssessmentPersistence;
import bg.sofia.uni.fmi.ces.model.facade.course.CoursePersistence;
import bg.sofia.uni.fmi.ces.model.facade.lecturer.LecturerPersistence;
import bg.sofia.uni.fmi.ces.utils.session.SessionUtils;

@ManagedBean(name = "courseBean")
@ViewScoped
public class CourseBean extends SucesBean implements Serializable {

	private static final long serialVersionUID = -1373151780844385607L;

	/**
	 * Course reference object for working with course data
	 */
	private Course course;

	/**
	 * List of all available semesters
	 */
	private List<Semester> semesterList;

	/**
	 * List of all available specialties
	 */
	private List<Specialty> specialtyList;

	/**
	 * List of all available grades
	 */
	private List<Grade> gradeList;

	/**
	 * List of all available lecturers
	 */
	private List<Lecturer> lecturerList;

	/**
	 * List of the ID values for the selected specialties
	 */
	private List<String> selectedSpecialties;

	/**
	 * List of the ID values of the selected grades
	 */
	private List<String> selectedGrades;

	/**
	 * ID of the value for the selected semester
	 */
	private int selectedSemesterId;

	/**
	 * Reference to the course persistence object used to managed work with the
	 * persistence entities and DB
	 */
	private CoursePersistence coursePersistence;

	private LecturerPersistence lecturerPersistence;

	private CourseAssessmentPersistence courseAssessmentPersistence;

	private CourseAssessment courseAssessment;

	/**
	 * Stores course rating value for the current user.
	 */
	private double courseUserRating;

	@PostConstruct
	public void init() {
		selectedSpecialties = new LinkedList<String>();
		selectedGrades = new LinkedList<String>();
		coursePersistence = new CoursePersistence();
		lecturerPersistence = new LecturerPersistence();

		// TODO getting course 1...when course searching is implemented this one
		// should be finished
		// Done - need to be tested and remove the comments
		int courseId = getInitialCourseId();
		course = coursePersistence.getCourseById(courseId);
		if (course != null) {
			for (Specialty specialty : course.getSpecialties()) {
				selectedSpecialties.add("" + specialty.getSpecialtyId());
			}

			for (Grade grade : course.getGrades()) {
				selectedGrades.add("" + grade.getGradeId());
			}

			selectedSemesterId = course.getSemester().getSemesterId();
		} else {
			this.course = new Course();
		}

		this.courseAssessmentPersistence = new CourseAssessmentPersistence();
		// TODO Look for possible improvements in persistence model for
		// better utilization of the same entity mgr

		// Course assessment persistence should use exactly the same entity
		// manager as course persistence.
		EntityManager coursePersistenceEntityMgr = coursePersistence
				.getEntityManager();
		this.courseAssessmentPersistence
				.setEntityManager(coursePersistenceEntityMgr);
		String userName = SessionUtils.getLoggedUserName();
		this.courseAssessment = courseAssessmentPersistence
				.getCourseAssassment(userName, courseId);
		if (courseAssessment == null) {
			courseAssessment = new CourseAssessment();
			courseAssessment.setCourse(course);
			courseAssessment.setUsersUserEmail(userName);
		}
		this.courseUserRating = courseAssessment.getCourseRating();
	}

	private int getInitialCourseId() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		String courseIdAsString = externalContext.getRequestParameterMap().get(
				"courseId");

		int courseId = 0;
		if (courseIdAsString != null) {
			courseId = Integer.parseInt(courseIdAsString);
		}

		return courseId;
	}

	/**
	 * Action for saving course data
	 * 
	 * @param event
	 */
	public void saveCourse(ActionEvent event) {
		List<Specialty> selectedSpecialtyList = getSpecialtyListByIds(selectedSpecialties);
		course.setSpecialties(selectedSpecialtyList);

		List<Grade> selectedGradesList = getGradesListByIds(selectedGrades);
		course.setGrades(selectedGradesList);

		Semester selectedSemester = getSemesterById(selectedSemesterId);
		course.setSemester(selectedSemester);

		// TODO getLecturerById....и това ще го допиша утре може би:)

		coursePersistence.beginTransaction();
		coursePersistence.persist(course);
		coursePersistence.commitTransaction();
	}

	public void assessCourse() {
		FacesContext currContext = FacesContext.getCurrentInstance();
		ExternalContext externalCxt = currContext.getExternalContext();
		saveCourseRating();
		int courseId = course.getCourseId();
		try {
			externalCxt.redirect(String.format(
					"feedbackForm.xhtml?courseId=%d", courseId));
		} catch (IOException e) {
			getLogger().error(e);
		}
	}

	private void saveCourseRating() {
		if (course != null && courseAssessment != null) {
			boolean isRated = isCourseRated();
			double newUserRating = getUserRating();
			boolean cancelRating = (newUserRating == 0D || newUserRating == Double.NaN);
			if (cancelRating) {
				if (isRated == false) {
					return;
				} else {
					cancelUserCourseRating();
				}
			} else {
				double courseRating = getRating();
				if (courseRating == 0D) {
					if (isRated == false) {
						persistCourseRatingInfo(true, newUserRating,
								newUserRating, 1);
					} else {
						getLogger()
								.fatal("When course rating is zero, there is no one rated for the course");
						return;
					}
				} else if (courseRating > 0D) {
					updateUserCourseRating(isRated, newUserRating);
				} else {
					getLogger().fatal(
							"Course rating invalid value '" + courseRating
									+ "'");
					return;
				}
			}
		}
	}

	/**
	 * Updates course rating info for the current user. Assumes that user had
	 * given valid course rating value.
	 * 
	 * @param isRated
	 *            Specify value if user has rated a particular course.
	 * @param userCourseRating
	 *            Course rating value which current user had given.
	 */
	private void updateUserCourseRating(boolean isRated, double userCourseRating) {
		List<CourseAssessment> courseAssessments = courseAssessmentPersistence
				.getCourseAssessments(course.getCourseId());
		if (courseAssessments == null || courseAssessments.isEmpty()) {
			getLogger()
					.fatal("Course with rating should have assessments related to it!");
			return;
		}
		double sum = 0D;
		int ratingCounter = 0;
		for (CourseAssessment currAssessment : courseAssessments) {
			if (isRated
					&& currAssessment.getCourseAssessmentId() == courseAssessment
							.getCourseAssessmentId()) {
				sum += userCourseRating;
				ratingCounter++;
			} else if (currAssessment.isCourseRated()) {
				sum += currAssessment.getCourseRating();
				ratingCounter++;
			}
		}
		if (isRated == false) {
			sum += userCourseRating;
			ratingCounter++;
			isRated = true;
		}
		double courseRating = sum / (double) ratingCounter;
		persistCourseRatingInfo(isRated, userCourseRating, courseRating,
				ratingCounter);
	}

	/**
	 * Current user cancels/removes their rating.
	 */
	private void cancelUserCourseRating() {
		double courseRating = getRating();
		if (courseRating <= 0D) {
			getLogger().fatal(
					"Course rating should be > 0, but it is '" + courseRating
							+ "'");
			return;
		}
		List<CourseAssessment> courseAssessments = courseAssessmentPersistence
				.getCourseAssessments(course.getCourseId());
		if (courseAssessments == null || courseAssessments.isEmpty()) {
			getLogger().fatal(
					"Course with rating '" + courseRating
							+ "' should have assessments related to it!");
			return;
		}
		double sum = 0D;
		int ratingCounter = 0;
		for (CourseAssessment currAssessment : courseAssessments) {
			if (currAssessment.isCourseRated()
					&& currAssessment.getCourseAssessmentId() != courseAssessment
							.getCourseAssessmentId()) {
				sum += currAssessment.getCourseRating();
				ratingCounter++;
			}
		}
		double newCourseRating = (ratingCounter != 0) ? (sum / (double) ratingCounter)
				: 0D;
		persistCourseRatingInfo(false, 0D, newCourseRating, ratingCounter);
	}

	/**
	 * Saves course rating info.
	 * 
	 * @param isRated
	 *            Specify value if user has rated a particular course.
	 * @param userRating
	 *            Course rating value which current user had given.
	 * @param courseRating
	 *            Overall/Summarized course rating value.
	 * @param ratingCounter
	 *            How many people rated the current course.
	 */
	private void persistCourseRatingInfo(boolean isRated, double userRating,
			double courseRating, int ratingCounter) {
		course.setRating(courseRating);
		if (course.getRating() != courseRating) {
			// TODO Throw error message
			getLogger().error(
					"Invalid course rating value '" + courseRating + "'");
			return;
		}
		course.setRatingCounter(ratingCounter);
		if (course.getRatingCounter() != ratingCounter) {
			// TODO Throw error message
			getLogger().error(
					"Invalid course rating users counter'" + ratingCounter
							+ "'");
			return;
		}
		courseAssessment.setCourseRated(isRated);
		courseAssessment.setCourseRating(userRating);
		courseAssessment.setCourse(course);
		courseAssessmentPersistence.beginTransaction();
		coursePersistence.persist(course);
		courseAssessmentPersistence.persist(courseAssessment);
		courseAssessmentPersistence.commitTransaction();
	}

	/**
	 * Provide the list of Specialty object that refer to the selected ones
	 * 
	 * @param idList
	 *            list of the id values for the selected specialties
	 * @return the referring Specialties in list
	 */
	private List<Specialty> getSpecialtyListByIds(List<String> idList) {
		List<Specialty> specialtyMatchingList = new LinkedList<Specialty>();

		for (String stringId : idList) {
			int id = Integer.parseInt(stringId);

			for (Specialty specialty : specialtyList) {
				if (id == specialty.getSpecialtyId()) {
					specialtyMatchingList.add(specialty);
				}
			}
		}

		return specialtyMatchingList;
	}

	/**
	 * Provide the list of Grade object that refer to the selected ones
	 * 
	 * @param idList
	 *            list of the id values for the selected grades
	 * @return the referring Grades in List
	 */
	private List<Grade> getGradesListByIds(List<String> idList) {
		List<Grade> gradeMatchingList = new LinkedList<Grade>();

		for (String stringId : idList) {
			int id = Integer.parseInt(stringId);

			for (Grade grade : gradeList) {
				if (id == grade.getGradeId()) {
					gradeMatchingList.add(grade);
				}
			}
		}

		return gradeMatchingList;
	}

	/**
	 * Provide the Semester object the refers to the id of the selected one
	 * 
	 * @param id
	 *            ID of the selected course
	 * @return the corresponding Semester object
	 */
	private Semester getSemesterById(int id) {
		for (Semester semester : semesterList) {
			if (id == semester.getSemesterId()) {
				return semester;
			}
		}

		return null;
	}

	// ================ PROPERTY GETTERS AND SETTERS ===================

	public List<Semester> getSemesterList() {
		if (semesterList == null) {
			semesterList = coursePersistence.getSemesters();
		}

		return semesterList;
	}

	public List<Specialty> getSpecialtyList() {
		if (specialtyList == null) {
			specialtyList = coursePersistence.getSpecialties();
		}

		return specialtyList;
	}

	public List<Grade> getGradeList() {
		if (gradeList == null) {
			gradeList = coursePersistence.getGrades();
		}

		return gradeList;
	}

	public List<Lecturer> getLecturerList() {
		if (lecturerList == null) {
			lecturerList = lecturerPersistence.getAllLecturers();
		}

		return lecturerList;
	}

	public List<String> getSelectedSpecialties() {
		return selectedSpecialties;
	}

	public void setSelectedSpecialties(List<String> selectedSpecialties) {
		this.selectedSpecialties = selectedSpecialties;
	}

	public List<String> getSelectedGrades() {
		return selectedGrades;
	}

	public void setSelectedGrades(List<String> selectedGrades) {
		this.selectedGrades = selectedGrades;
	}

	public int getSelectedSemesterId() {
		return selectedSemesterId;
	}

	public void setSelectedSemesterId(int selectedSemesterId) {
		this.selectedSemesterId = selectedSemesterId;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public double getRating() {
		return this.course.getRating();
	}

	public void setRating(double rating) {
		this.course.setRating(rating);
	}

	public int getRatingCounter() {
		return this.course.getRatingCounter();
	}

	public void setRatingCounter(int ratingCounter) {
		this.course.setRatingCounter(ratingCounter);
	}

	public boolean isCourseRated() {
		return (this.courseAssessment != null && this.courseAssessment
				.isCourseRated());
	}

	public double getUserRating() {
		return this.courseUserRating;
	}

	public void setUserRating(double rating) {
		this.courseUserRating = rating;
	}
}
