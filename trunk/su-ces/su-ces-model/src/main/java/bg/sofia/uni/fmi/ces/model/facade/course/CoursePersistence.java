package bg.sofia.uni.fmi.ces.model.facade.course;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.course.Grade;
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
	public static final String SELECT_COURSE_BY_COURSE_ID = "SELECT c "
			+ "FROM Course c " + "WHERE c.courseId = :courseId";

	public static final String SELECT_SEMESTERS = "SELECT s "
			+ "FROM Semester s ";

	public static final String SELECT_SPECIALTIES = "SELECT s "
			+ "FROM Specialty s";

	public static final String SELECT_GRADES = "SELECT g " + "FROM Grade g";

	public static final String SELECT_ALL_COURSES = "SELECT c "
			+ "FROM Course c ";

	public CoursePersistence() {
		super();
	}

	/**
	 * Makes role persistence testable.
	 * 
	 * @param entityManagerFactory
	 *            Entity manager factory to be specified.
	 */
	public CoursePersistence(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}

	/**
	 * Get the course referenced by the provided course ID
	 * 
	 * @param courseId
	 *            ID of the course to search for
	 * @return the course matching the ID. If no course is present NULL will be
	 *         returned
	 */
	public Course getCourseById(int courseId) {
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

	public List<Grade> getGrades() {
		Query query = entityManager.createQuery(SELECT_GRADES);
		List<Grade> gradeList = (List<Grade>) query.getResultList();

		return gradeList;
	}

	public List<Course> getAllCourses() {
		Query query = entityManager.createQuery(SELECT_ALL_COURSES);
		List<Course> gradeList = (List<Course>) query.getResultList();

		return gradeList;
	}

}
