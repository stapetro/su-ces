package bg.sofia.uni.fmi.ces.model.facade.course;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
import bg.sofia.uni.fmi.ces.model.util.SucesTestUtil;

/**
 * Tests course assessment persistence facade.
 * 
 * @author Staskata
 * 
 */
public class CourseAssessmentPersistenceTest {

	private static final String[] USER_NAMES = { "testValid@test.bg",
			"stanislavp@uni-sofia.bg", "kbailov@uni-sofia.bg",
			"kristiyanm@uni-sofia.bg" };
	private CourseAssessmentPersistence courseAssessmentPersistence;
	private Query mockedQuery;
	private EntityManager mockedEntityMgr;
	private Random random;

	// This commented code is left to show how to initialize and use logger in
	// test classes.
	// private Logger logger;

	@Before
	public void setUP() {
		EntityManagerFactory mockedEntityMgrFactory = MockHelper
				.getEntityManagerFactory();
		courseAssessmentPersistence = new CourseAssessmentPersistence(
				mockedEntityMgrFactory);
		mockedEntityMgr = mockedEntityMgrFactory.createEntityManager();
		mockedQuery = org.mockito.Mockito.mock(Query.class);
		this.random = new Random(System.currentTimeMillis());
		// this.logger = SucesTestUtil.getLogger(this.getClass());
	}

	@After
	public void setDown() {
		courseAssessmentPersistence = null;
		mockedQuery = null;
		mockedEntityMgr = null;
		this.random = null;
		// this.logger = null;
	}

	/**
	 * Tests selecting course assessment by course ID and user name.
	 */
	@Test
	public void testGetCourseAssassment() {
		when(
				mockedEntityMgr
						.createQuery(CourseAssessmentPersistence.SELECT_COURSE_ASSESSMENT_BY_USER_AND_COURSE_ID))
				.thenReturn(mockedQuery);
		CourseAssessment courseAssessment = getRandomCourseAssessment(1);
		when(
				mockedQuery.setParameter("userName",
						courseAssessment.getUsersUserEmail())).thenReturn(
				mockedQuery);
		when(
				mockedQuery.setParameter("courseId", courseAssessment
						.getCourse().getCourseId())).thenReturn(mockedQuery);
		// case 0: test list that contains one valid course assessment
		List<CourseAssessment> validList = new ArrayList<>();
		validList.add(courseAssessment);
		when(mockedQuery.getResultList()).thenReturn(validList);
		Course course = courseAssessment.getCourse();
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
		// case 5: test when query result list is NULL
		validList = null;
		foundCourseAssessment = courseAssessmentPersistence
				.getCourseAssassment(courseAssessment.getUsersUserEmail(),
						course.getCourseId());
		Assert.assertNull(foundCourseAssessment);
	}

	/**
	 * Test selecting course assessments by course ID.
	 */
	@Test
	public void testGetCourseAssessments() {
		when(
				mockedEntityMgr
						.createQuery(CourseAssessmentPersistence.SELECT_COURSE_ASSESSMENT_BY_COURSE_ID))
				.thenReturn(mockedQuery);
		// Course IDs should be sorted in ASC way
		final Integer[] courseIds = { 1, 2 };
		final int[] numberOfCourseAssessments = { 5, 3 };
		for (int courseId : courseIds) {
			when(mockedQuery.setParameter("courseId", courseId)).thenReturn(
					mockedQuery);
		}
		// case 0: Positive test with valid data.
		Map<Integer, List<CourseAssessment>> courseAssessmentData = new HashMap<>();
		for (int index = 0; index < courseIds.length; index++) {
			int currNumberOfCourseAssessments = numberOfCourseAssessments[index];
			List<CourseAssessment> courseAssessments = new ArrayList<>(
					currNumberOfCourseAssessments);
			int currCourseId = courseIds[index];
			int counter = 0;
			while (counter < currNumberOfCourseAssessments) {
				CourseAssessment courseAssessment = getRandomCourseAssessment(currCourseId);
				courseAssessments.add(courseAssessment);
				counter++;
			}
			courseAssessmentData.put(currCourseId, courseAssessments);
		}
		Integer selectCourseId = SucesTestUtil
				.getRandomValue(random, courseIds);
		List<CourseAssessment> expectedCourseAssessments = courseAssessmentData
				.get(selectCourseId);
		when(mockedQuery.getResultList()).thenReturn(expectedCourseAssessments);
		List<CourseAssessment> actualCourseAssessments = courseAssessmentPersistence
				.getCourseAssessments(selectCourseId);
		Assert.assertNotNull(actualCourseAssessments);
		Assert.assertEquals(expectedCourseAssessments, actualCourseAssessments);
		// Mock query for negative test cases
		when(mockedQuery.getResultList()).thenReturn(null);
		// case 1: Negative - does not found course assessments with valid
		// courseId
		Integer newCourseId = SucesTestUtil.getRandomValue(random, courseIds,
				selectCourseId);
		actualCourseAssessments = courseAssessmentPersistence
				.getCourseAssessments(newCourseId);
		Assert.assertNull(actualCourseAssessments);
		// case 2: Negative - pass invalid course id
		selectCourseId = courseIds[courseIds.length - 1] + 5;
		actualCourseAssessments = courseAssessmentPersistence
				.getCourseAssessments(newCourseId);
		Assert.assertNull(actualCourseAssessments);
	}

	/**
	 * Gets random course assessment test data.
	 * 
	 * @param courseId
	 *            Course id to be specified.
	 * @return Course assessment instance
	 */
	private CourseAssessment getRandomCourseAssessment(int courseId) {
		CourseAssessment courseAssessment = new CourseAssessment();
		Course course = new Course();
		course.setCourseId(courseId);
		courseAssessment.setCourse(course);
		courseAssessment.setUsersUserEmail(SucesTestUtil.getRandomValue(random,
				USER_NAMES));
		final int maxAssessmentValue = 6;
		courseAssessment.setAssessmentCorrectness(SucesTestUtil
				.getPositiveNonZeroNumber(random, maxAssessmentValue));
		courseAssessment.setCourseAttitude(SucesTestUtil
				.getPositiveNonZeroNumber(random, maxAssessmentValue));
		courseAssessment.setCourseDifficulty(SucesTestUtil
				.getPositiveNonZeroNumber(random, maxAssessmentValue));
		courseAssessment.setCourseEngagements(SucesTestUtil
				.getPositiveNonZeroNumber(random, maxAssessmentValue));
		courseAssessment.setCourseUnderstanding(SucesTestUtil
				.getPositiveNonZeroNumber(random, maxAssessmentValue));
		courseAssessment.setCourseInPlan(SucesTestUtil
				.getPositiveNonZeroNumber(random, maxAssessmentValue));
		courseAssessment.setCourseOrganization(SucesTestUtil
				.getPositiveNonZeroNumber(random, maxAssessmentValue));
		courseAssessment.setCoursePresentation(SucesTestUtil
				.getPositiveNonZeroNumber(random, maxAssessmentValue));
		courseAssessment.setCourseUsefullness(SucesTestUtil
				.getPositiveNonZeroNumber(random, maxAssessmentValue));
		courseAssessment.setStudentsAttitude(SucesTestUtil
				.getPositiveNonZeroNumber(random, maxAssessmentValue));
		boolean courseRated = random.nextBoolean();
		courseAssessment.setCourseRated(courseRated);
		if (courseRated) {
			courseAssessment.setCourseRating(SucesTestUtil
					.getPositiveNonZeroNumber(random, 7));
		}
		return courseAssessment;
	}
}
