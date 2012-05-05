package bg.sofia.uni.fmi.ces.jsf.beans;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionEvent;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.course.Grade;
import bg.sofia.uni.fmi.ces.model.course.Semester;
import bg.sofia.uni.fmi.ces.model.course.Specialty;
import bg.sofia.uni.fmi.ces.model.facade.course.CoursePersistence;

@ManagedBean(name = "courseBean")
@RequestScoped
public class CourseBean implements Serializable {

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

	public CourseBean() {
		selectedSpecialties = new LinkedList<String>();
		selectedGrades = new LinkedList<String>();
		coursePersistence = new CoursePersistence();

		// TODO getting course 1...when course searching is implemented this one
		// should be finished
		course = coursePersistence.geCourseById(1);
		if (course != null) {
			for (Specialty specialty : course.getSpecialties()) {
				selectedSpecialties.add("" + specialty.getSpecialtyId());
			}

			for (Grade grade : course.getGrades()) {
				selectedGrades.add("" + grade.getGradeId());
			}

			selectedSemesterId = course.getSemester().getSemesterId();
		}
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

		coursePersistence.beginTransaction();
		coursePersistence.persist(course);
		coursePersistence.commitTransaction();

		// course = coursePersistence.geCourseById(1);
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
		semesterList = coursePersistence.getSemesters();

		return semesterList;
	}

	public List<Specialty> getSpecialtyList() {
		specialtyList = coursePersistence.getSpecialties();

		return specialtyList;
	}

	public List<Grade> getGradeList() {
		gradeList = coursePersistence.getGrades();

		return gradeList;
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
}
