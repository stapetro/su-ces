package bg.sofia.uni.fmi.ces.model.facade.course;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.course.CourseAssessment;
import bg.sofia.uni.fmi.ces.model.util.MockHelper;

/**
 * Tests course assessment persistence facade.
 * @author Staskata
 *
 */
public class CourseAssessmentPersistenceTest {

	private CourseAssessmentPersistence courseAssessmentPersistence;
	private CourseAssessment courseAssessment;
	private Query mockedQuery;

	@Before
	public void setUP() {
		EntityManagerFactory mockedEntityMgrFactory = MockHelper
				.getEntityManagerFactory();
		courseAssessmentPersistence = new CourseAssessmentPersistence(
				mockedEntityMgrFactory);
		EntityManager mockedEntityMgr = mockedEntityMgrFactory
				.createEntityManager();
		mockedQuery = org.mockito.Mockito.mock(Query.class);
		when(
				mockedEntityMgr
						.createQuery(CourseAssessmentPersistence.SELECT_COURSE_ASSESSMENT_BY_USER_AND_COURSE_ID))
				.thenReturn(mockedQuery);

		courseAssessment = getCourseAssessment();
		when(
				mockedQuery.setParameter("userName",
						courseAssessment.getUsersUserEmail())).thenReturn(
				mockedQuery);
		when(
				mockedQuery.setParameter("courseId", courseAssessment
						.getCourse().getCourseId())).thenReturn(mockedQuery);
	}

	@After
	public void setDown() {
		courseAssessmentPersistence = null;
		courseAssessment = null;
		mockedQuery = null;
	}

	/**
	 * Tests selecting course assessment by course ID and user name.
	 */
	@Test
	public void testGetCourseAssassment() {
		// case 0: test list that contains one valid course assessment
		List<CourseAssessment> validList = new ArrayList<>();
		validList.add(courseAssessment);
		when(mockedQuery.getResultList()).thenReturn(validList);
		Course course = courseAssessment.getCourse();
		course.setCourseId(1);
		CourseAssessment foundCourseAssessment = courseAssessmentPersistence
				.getCourseAssassment(courseAssessment.getUsersUserEmail(),
						course.getCourseId());
		Assert.assertNotNull(foundCourseAssessment);
		Assert.assertEquals(courseAssessment, foundCourseAssessment);
		// case 1: test with list that contains more than one course assessment
		CourseAssessment secondCourseAssessment = new CourseAssessment(
				courseAssessment);
		validList.add(secondCourseAssessment);
		foundCourseAssessment = courseAssessmentPersistence
				.getCourseAssassment(courseAssessment.getUsersUserEmail(),
						course.getCourseId());
		Assert.assertNull(foundCourseAssessment);
		// case 2: test when user name is empty
		foundCourseAssessment = courseAssessmentPersistence
				.getCourseAssassment("", course.getCourseId());
		Assert.assertNull(foundCourseAssessment);
		foundCourseAssessment = courseAssessmentPersistence
				.getCourseAssassment(null, course.getCourseId());
		Assert.assertNull(foundCourseAssessment);
		// case 4: test when query result list is empty
		validList.clear();
		foundCourseAssessment = courseAssessmentPersistence
				.getCourseAssassment(courseAssessment.getUsersUserEmail(),
						course.getCourseId());
		Assert.assertNull(foundCourseAssessment);
	}

	/**
	 * Gets course assessment test data.
	 * 
	 * @return Course assessment instance.
	 */
	private CourseAssessment getCourseAssessment() {
		CourseAssessment courseAssessment = new CourseAssessment();
		courseAssessment.setAssessmentCorrectness(1);
		courseAssessment.setCourseAttitude(2);
		courseAssessment.setCourseDifficulty(3);
		courseAssessment.setCourseEngagements(4);
		courseAssessment.setCourseUnderstanding(5);
		courseAssessment.setCourseInPlan(5);
		courseAssessment.setCourseOrganization(4);
		courseAssessment.setCoursePresentation(3);
		courseAssessment.setCourseUsefullness(2);
		courseAssessment.setStudentsAttitude(1);
		String userName = "testValid@test.bg";
		courseAssessment.setUsersUserEmail(userName);
		Course course = new Course();
		course.setCourseId(1);
		courseAssessment.setCourse(course);
		return courseAssessment;
	}
}
