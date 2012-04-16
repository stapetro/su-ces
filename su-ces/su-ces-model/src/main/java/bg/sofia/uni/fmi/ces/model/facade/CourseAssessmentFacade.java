package bg.sofia.uni.fmi.ces.model.facade;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.course.CourseAssessment;

/**
 * Provide a common interface for working with course assessment specific
 * operations.
 */
public class CourseAssessmentFacade extends ModelFacade {

	/**
	 * Query for selecting the course assessment results.
	 */
	private static final String SELECT_COURSE_ASSESSMENT_BY_USER_AND_COURSE_ID = "SELECT ca "
			+ "FROM CourseAssessment ca "
			+ "WHERE ca.usersUserEmail = :userName "
			+ "AND ca.cours.courseId = :courseId";

	public CourseAssessmentFacade() {
		super();
	}

	/**
	 * Find the course assessment records based on the provided user name and
	 * course information
	 * 
	 * @param userName
	 *            user name of the user who uploaded the course assessment
	 * @param course
	 *            course that was assessed
	 * @return the corresponding records. NULL if the input parameters are
	 *         invalid or no record is found
	 */
	public CourseAssessment getCourseAssassment(String userName, Course course) {
		if (userName == null || userName.trim().length() == 0 || course == null) {
			return null;
		}

		Query query = entityManager
				.createQuery(SELECT_COURSE_ASSESSMENT_BY_USER_AND_COURSE_ID);
		int courseId = course.getCourseId();
		query.setParameter("userName", userName);
		query.setParameter("courseId", courseId);

		List<CourseAssessment> resultList = (List<CourseAssessment>) query
				.getResultList();

		CourseAssessment courseAssessment = null;
		if (resultList != null && resultList.size() == 1) {
			// the result set is expected to contain a single record or no
			// records at all
			// otherwise something wrong has happened with the DB restrictions
			//entityManager.getTransaction().begin();
			courseAssessment = resultList.get(0);
		}

		return courseAssessment;
	}
}
