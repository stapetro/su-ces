package bg.sofia.uni.fmi.ces.model.facade.course;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.course.Semester;
import bg.sofia.uni.fmi.ces.model.course.Specialty;
import bg.sofia.uni.fmi.ces.model.facade.ModelFacade;

/**
 * Class providing basic set of methods for working with courses
 */
public class CoursePersistence extends ModelFacade implements Serializable {

	private static final long serialVersionUID = 6029218129335321213L;

	/**
	 * Select statement for obtaining the <code>Course</code> object by its ID
	 */
	private static final String SELECT_COURSE_BY_COURSE_ID = "SELECT c "
			+ "FROM Course c " + "WHERE c.courseId = :courseId";

	private static final String SELECT_SEMESTERS = "SELECT s "
			+ "FROM Semester s ";

	private static final String SELECT_SPECIALTIES = "SELECT s "
			+ "FROM Specialty s";

	public CoursePersistence() {
		super();
	}

	/**
	 * Get the course referenced by the provided course ID
	 * 
	 * @param courseId
	 *            ID of the course to search for
	 * @return the course matching the ID. If no course is present NULL will be
	 *         returned
	 */
	public Course geCourseById(int courseId) {
		if (courseId < 0) {
			return null;
		}

		Query query = entityManager.createQuery(SELECT_COURSE_BY_COURSE_ID);
		query.setParameter("courseId", courseId);

		List<Course> courseList = (List<Course>) query.getResultList();

		Course course = null;
		if (courseList != null && courseList.size() == 1) {
			course = courseList.get(0);
		}

		return course;
	}

	public List<Semester> getSemesters() {
		Query query = entityManager.createQuery(SELECT_SEMESTERS);
		List<Semester> semestersList = (List<Semester>) query.getResultList();

		return semestersList;
	}

	public List<Specialty> getSpecialties() {
		Query query = entityManager.createQuery(SELECT_SPECIALTIES);
		List<Specialty> specialtyList = (List<Specialty>) query.getResultList();

		return specialtyList;
	}

}
