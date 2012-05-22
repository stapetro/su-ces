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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

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
public class CourseBean implements Serializable {

	private static final long serialVersionUID = -1373151780844385607L;

	private transient Logger logger;
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

	private CourseAssessmentPersistence courseAssessmentFacade;

	private CourseAssessment courseAssessment;

	/**
	 * Stores rating value for a course.
	 */
	private double rating;

	/**
	 * Stores number of people who rated for a course.
	 */
	private int ratingCounter;

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

		this.rating = course.getRating();
		this.ratingCounter = course.getRatingCounter();

		this.courseAssessmentFacade = new CourseAssessmentPersistence();
		// TODO Look for possible improvements in persistence model for
		// better utilization of the same entity mgr

		// Course assessment persistence should use exactly the same entity
		// manager as course persistence.
		EntityManager coursePersistenceEntityMgr = coursePersistence
				.getEntityManager();
		this.courseAssessmentFacade
				.setEntityManager(coursePersistenceEntityMgr);
		String userName = SessionUtils.getLoggedUserName();
		this.courseAssessment = courseAssessmentFacade.getCourseAssassment(
				userName, courseId);
		if (courseAssessment == null) {
			courseAssessment = new CourseAssessment();
			courseAssessment.setCourse(course);
			courseAssessment.setUsersUserEmail(userName);
		}
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
		
		//TODO getLecturerById....и това ще го допиша утре може би:)
		
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
		double rating = getRating();
		if ((rating > 0D) && (isCourseRated() == false)) {
			if (course != null && courseAssessment != null) {
				double newRating = course.getRating() + rating;
				int ratingCounter = getRatingCounter() + 1;
				course.setRatingCounter(ratingCounter);
				course.setRating(newRating / ratingCounter);
				courseAssessment.setCourseRated(true);
				courseAssessment.setCourse(course);
				courseAssessmentFacade.beginTransaction();
				courseAssessmentFacade.persist(courseAssessment);
				courseAssessmentFacade.commitTransaction();
			}
		}
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
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public int getRatingCounter() {
		return ratingCounter;
	}

	public void setRatingCounter(int ratingCounter) {
		this.ratingCounter = ratingCounter;
	}

	public boolean isCourseRated() {
		return (this.courseAssessment != null && this.courseAssessment
				.isCourseRated());
	}

	private Logger getLogger() {
		if (logger == null) {
			logger = LogManager.getLogger(this.getClass());
		}
		return logger;
	}
}
