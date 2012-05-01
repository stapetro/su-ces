package bg.sofia.uni.fmi.ces.model.facade.course;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Parameter;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.course.CourseAssessment;
import bg.sofia.uni.fmi.ces.model.util.MockHelper;

public class CourseAssessmentPersistenceTest {

	private CourseAssessmentPersistence courseAssessmentPersistence;
	private CourseAssessment courseAssessment;

	@Before
	public void setUP() {
		EntityManagerFactory mockedEntityMgrFactory = MockHelper
				.getEntityManagerFactory();
		courseAssessmentPersistence = new CourseAssessmentPersistence(
				mockedEntityMgrFactory);
		EntityManager mockedEntityMgr = mockedEntityMgrFactory
				.createEntityManager();
		Query validQuery = org.mockito.Mockito.mock(Query.class);
		when(
				mockedEntityMgr
						.createQuery(CourseAssessmentPersistence.SELECT_COURSE_ASSESSMENT_BY_USER_AND_COURSE_ID))
				.thenReturn(validQuery);
		List<CourseAssessment> validList = new ArrayList<>();
		courseAssessment = getCourseAssessment();
		validList.add(courseAssessment);

		when(validQuery.getResultList()).thenReturn(validList);

		when(
				validQuery.setParameter("userName",
						courseAssessment.getUsersUserEmail())).thenReturn(
				validQuery);
		when(
				validQuery.setParameter("courseId", courseAssessment
						.getCourse().getCourseId())).thenReturn(validQuery);
	}

	@After
	public void setDown() {
		courseAssessmentPersistence = null;
	}

	@Test
	public void testGetCourseAssassment() {
		// test list that contains one valid course assessment
		Course course = courseAssessment.getCourse();
		course.setCourseId(1);
		CourseAssessment foundCourseAssessment = courseAssessmentPersistence
				.getCourseAssassment(courseAssessment.getUsersUserEmail(),
						course.getCourseId());
		Assert.assertNotNull(foundCourseAssessment);
		Course expectedCourse = courseAssessment.getCourse();
		Course actualCourse = foundCourseAssessment.getCourse();
		// TODO fully compare course objects
		Assert.assertEquals(expectedCourse.getCourseId(),
				actualCourse.getCourseId());
		Assert.assertEquals(courseAssessment.getAssessmentCorrectness(),
				foundCourseAssessment.getAssessmentCorrectness());
		Assert.assertEquals(courseAssessment.getCourseAttitude(),
				foundCourseAssessment.getCourseAttitude());
		Assert.assertEquals(courseAssessment.getCourseDifficulty(),
				foundCourseAssessment.getCourseDifficulty());
		Assert.assertEquals(courseAssessment.getCourseEngagements(),
				foundCourseAssessment.getCourseEngagements());
		Assert.assertEquals(courseAssessment.getCourseInderstanding(),
				foundCourseAssessment.getCourseInderstanding());
		Assert.assertEquals(courseAssessment.getCourseInPlan(),
				foundCourseAssessment.getCourseInPlan());
		Assert.assertEquals(courseAssessment.getCourseOrganization(),
				foundCourseAssessment.getCourseOrganization());
		Assert.assertEquals(courseAssessment.getCoursePresentation(),
				foundCourseAssessment.getCoursePresentation());
		Assert.assertEquals(courseAssessment.getCouseUsefullness(),
				foundCourseAssessment.getCouseUsefullness());
		Assert.assertEquals(courseAssessment.getStudentsAttitude(),
				foundCourseAssessment.getStudentsAttitude());
		Assert.assertEquals(courseAssessment.getUsersUserEmail(),
				foundCourseAssessment.getUsersUserEmail());
		// TODO test with list that contains more than one course assessment
		// TODO test when query result list is empty
		// TODO test when user name is empty
		Assert.fail("Test is not fully implemented");
	}

	private CourseAssessment getCourseAssessment() {
		CourseAssessment courseAssessment = new CourseAssessment();
		courseAssessment.setAssessmentCorrectness(1);
		courseAssessment.setCourseAttitude(2);
		courseAssessment.setCourseDifficulty(3);
		courseAssessment.setCourseEngagements(4);
		courseAssessment.setCourseInderstanding(5);
		courseAssessment.setCourseInPlan(5);
		courseAssessment.setCourseOrganization(4);
		courseAssessment.setCoursePresentation(3);
		courseAssessment.setCouseUsefullness(2);
		courseAssessment.setStudentsAttitude(1);
		String userName = "testValid@test.bg";
		courseAssessment.setUsersUserEmail(userName);
		Course course = new Course();
		course.setCourseId(1);
		courseAssessment.setCourse(course);
		return courseAssessment;
	}

	private Query getQuery() {
		Query query = new javax.persistence.Query() {

			@Override
			public List getResultList() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getSingleResult() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int executeUpdate() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Query setMaxResults(int maxResult) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getMaxResults() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Query setFirstResult(int startPosition) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getFirstResult() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Query setHint(String hintName, Object value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Map<String, Object> getHints() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Query setParameter(Parameter<T> param, T value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(Parameter<Calendar> param,
					Calendar value, TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(Parameter<Date> param, Date value,
					TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(String name, Object value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(String name, Calendar value,
					TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(String name, Date value,
					TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(int position, Object value) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(int position, Calendar value,
					TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setParameter(int position, Date value,
					TemporalType temporalType) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Set<Parameter<?>> getParameters() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Parameter<?> getParameter(String name) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Parameter<T> getParameter(String name, Class<T> type) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Parameter<?> getParameter(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> Parameter<T> getParameter(int position, Class<T> type) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isBound(Parameter<?> param) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public <T> T getParameterValue(Parameter<T> param) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getParameterValue(String name) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Object getParameterValue(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setFlushMode(FlushModeType flushMode) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public FlushModeType getFlushMode() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Query setLockMode(LockModeType lockMode) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public LockModeType getLockMode() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public <T> T unwrap(Class<T> cls) {
				// TODO Auto-generated method stub
				return null;
			}

		};
		return query;
	}

}
