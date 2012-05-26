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
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.util.MockHelper;

/**
 * Tests course persistence facade
 * 
 * @author Staskata
 * 
 */
public class CoursePersistenceTest {

	private CoursePersistence coursePersistence;
	@Mock
	private Query mockedQuery;
	private EntityManager mockedEntityMgr;

	@Before
	public void setUP() {
		MockitoAnnotations.initMocks(this);
		EntityManagerFactory mockedEntityMgrFactory = MockHelper
				.getEntityManagerFactory();
		coursePersistence = new CoursePersistence(mockedEntityMgrFactory);
		mockedEntityMgr = mockedEntityMgrFactory.createEntityManager();
	}

	@After
	public void setDown() {
		coursePersistence = null;
		mockedQuery = null;
		mockedEntityMgr = null;
	}

	@Test
	public void testGetCourseById() {
		when(
				mockedEntityMgr
						.createQuery(CoursePersistence.SELECT_COURSE_BY_COURSE_ID))
				.thenReturn(mockedQuery);
		Course course = getCourse();
		int courseId = course.getCourseId();
		when(mockedQuery.setParameter("courseId", courseId)).thenReturn(
				mockedQuery);
		// case 0: test with valid course list.
		List<Course> courses = new ArrayList<>();
		courses.add(course);
		when(mockedQuery.getResultList()).thenReturn(courses);
		Course actualCourse = coursePersistence.getCourseById(courseId);
		Assert.assertEquals(course, actualCourse);
		// case 1: course list contains more than one course.
		Course secondCourse = new Course();
		secondCourse.setCourseId(2);
		secondCourse.setCourseName("Project Management");
		secondCourse.setCourseAnnotation("course 2 annotation");
		secondCourse.setExaminationForm("written & tests 2");
		secondCourse.setLiterature("Web Services 2");
		secondCourse.setPreliminaryRequirements("good programming skills 2");
		secondCourse.setSummary("Course summary 2");
		secondCourse.setWorkload(2);
		secondCourse.setYear(2013);
		secondCourse.setRating(3D);
		secondCourse.setRatingCounter(34);
		courses.add(secondCourse);
		actualCourse = coursePersistence.getCourseById(courseId);
		Assert.assertNull(actualCourse);
		// case 2: course list is empty
		courses.clear();
		actualCourse = coursePersistence.getCourseById(courseId);
		Assert.assertNull(actualCourse);
		// case 3: course list is NULL
		courses = null;
		actualCourse = coursePersistence.getCourseById(courseId);
		Assert.assertNull(actualCourse);
	}

	public void testGetAllCourses() {
		// TODO To be implemented
	}

	public void testGetSemesters() {
		// TODO To be implemented
	}

	public void testGetSpecialties() {
		// TODO To be implemented
	}

	public void testGetGrades() {
		// TODO To be implemented
	}

	private Course getCourse() {
		Course course = new Course();
		course.setCourseId(1);
		course.setCourseName("Service Oriented Architecture");
		course.setCourseAnnotation("course annotation");
		course.setExaminationForm("written & tests");
		course.setLiterature("Web Services");
		course.setPreliminaryRequirements("good programming skills");
		course.setSummary("Course summary");
		course.setWorkload(1);
		course.setYear(2015);
		course.setRating(4D);
		course.setRatingCounter(12);
		return course;
	}
}
