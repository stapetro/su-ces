package bg.sofia.uni.fmi.ces.model.facade.course;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.course.Grade;
import bg.sofia.uni.fmi.ces.model.course.Semester;
import bg.sofia.uni.fmi.ces.model.course.Specialty;
import bg.sofia.uni.fmi.ces.model.util.MockHelper;
import bg.sofia.uni.fmi.ces.model.util.SucesTestUtil;

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
	private Random random;

	@Before
	public void setUP() {
		MockitoAnnotations.initMocks(this);
		EntityManagerFactory mockedEntityMgrFactory = MockHelper
				.getEntityManagerFactory();
		coursePersistence = new CoursePersistence(mockedEntityMgrFactory);
		mockedEntityMgr = mockedEntityMgrFactory.createEntityManager();
		this.random = new Random(System.currentTimeMillis());
	}

	@After
	public void setDown() {
		coursePersistence = null;
		mockedQuery = null;
		mockedEntityMgr = null;
		this.random = null;
	}

	@Test
	public void testGetCourseById() {
		when(
				mockedEntityMgr
						.createQuery(CoursePersistence.SELECT_COURSE_BY_COURSE_ID))
				.thenReturn(mockedQuery);
		int courseId = 1;
		Course course = getCourse(courseId);
		when(mockedQuery.setParameter("courseId", courseId)).thenReturn(
				mockedQuery);
		// case 0: test with valid course list.
		List<Course> courses = new ArrayList<>();
		courses.add(course);
		when(mockedQuery.getResultList()).thenReturn(courses);
		Course actualCourse = coursePersistence.getCourseById(courseId);
		assertEquals(course, actualCourse);
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
		assertNull(actualCourse);
		// case 2: course list is empty
		courses.clear();
		actualCourse = coursePersistence.getCourseById(courseId);
		assertNull(actualCourse);
		// case 3: course list is NULL
		courses = null;
		actualCourse = coursePersistence.getCourseById(courseId);
		assertNull(actualCourse);
		// case 4: negative course ID
		courseId = -10;
		actualCourse = coursePersistence.getCourseById(courseId);
		assertNull(actualCourse);
	}

	@Test
	public void testGetAllCourses() {
		when(mockedEntityMgr.createQuery(CoursePersistence.SELECT_ALL_COURSES))
				.thenReturn(mockedQuery);
		final Integer[] courseIDs = { 1, 2, 3, 4, 5 };
		List<Course> courses = new ArrayList<>();
		for (Integer courseId : courseIDs) {
			Course course = getCourse(courseId);
			courses.add(course);
		}
		when(mockedQuery.getResultList()).thenReturn(courses);
		// case 0: course list with courses
		List<Course> actualCourses = coursePersistence.getAllCourses();
		assertEquals(courses, actualCourses);
		// case 1: course list is empty
		courses.clear();
		actualCourses = coursePersistence.getAllCourses();
		assertEquals(courses, actualCourses);
		// case 2: course list is NULL
		courses = null;
		when(mockedQuery.getResultList()).thenReturn(courses);
		actualCourses = coursePersistence.getAllCourses();
		assertNull(actualCourses);
	}

	@Test
	public void testGetSemesters() {
		when(mockedEntityMgr.createQuery(CoursePersistence.SELECT_SEMESTERS))
				.thenReturn(mockedQuery);
		final Integer[] semesterIDs = { 1, 2, 3 };
		List<Semester> semesters = new ArrayList<>();
		for (Integer semesterID : semesterIDs) {
			Semester semester = getSemester(semesterID);
			semesters.add(semester);
		}
		when(mockedQuery.getResultList()).thenReturn(semesters);
		// case 0: semester list with semesters
		List<Semester> actualSemesters = coursePersistence.getSemesters();
		assertEquals(semesters, actualSemesters);
		// case 1: semester list is empty
		semesters.clear();
		actualSemesters = coursePersistence.getSemesters();
		assertEquals(semesters, actualSemesters);
		// case 2: semester list is NULL
		semesters = null;
		when(mockedQuery.getResultList()).thenReturn(semesters);
		actualSemesters = coursePersistence.getSemesters();
		assertNull(actualSemesters);
	}

	@Test
	public void testGetSpecialties() {
		when(mockedEntityMgr.createQuery(CoursePersistence.SELECT_SPECIALTIES))
				.thenReturn(mockedQuery);
		final int[] specialtyIDs = { 1, 2, 3, 4, 5 };
		List<Specialty> specialties = new ArrayList<>();
		for (int specialtyID : specialtyIDs) {
			Specialty specialty = getSpecialty(specialtyID);
			specialties.add(specialty);
		}
		when(mockedQuery.getResultList()).thenReturn(specialties);
		// case 0:
		List<Specialty> actualSpecialties = coursePersistence.getSpecialties();
		assertEquals(specialties, actualSpecialties);
		// case 1: empty list
		specialties.clear();
		actualSpecialties = coursePersistence.getSpecialties();
		assertEquals(specialties, actualSpecialties);
		// case 2: NULL list
		specialties = null;
		when(mockedQuery.getResultList()).thenReturn(specialties);
		actualSpecialties = coursePersistence.getSpecialties();
		assertNull(actualSpecialties);
	}

	@Test
	public void testGetGrades() {
		when(mockedEntityMgr.createQuery(CoursePersistence.SELECT_GRADES))
				.thenReturn(mockedQuery);
		final int[] gradeIDs = { 1, 2, 3, 4, 5 };
		List<Grade> grades = new ArrayList<>();
		for (int gradeID : gradeIDs) {
			Grade grade = getGrade(gradeID);
			grades.add(grade);
		}
		when(mockedQuery.getResultList()).thenReturn(grades);
		// case 0:
		List<Grade> actualGrades = coursePersistence.getGrades();
		// assertEquals(grades, actualGrades);
		assertThat(actualGrades, is(grades));
		// case 1: empty list
		grades.clear();
		actualGrades = coursePersistence.getGrades();
		// assertEquals(grades, actualGrades);
		assertThat(actualGrades, is(grades));
		// case 2: NULL list
		grades = null;
		when(mockedQuery.getResultList()).thenReturn(grades);
		actualGrades = coursePersistence.getGrades();
		// assertNull(actualGrades);
		assertThat(actualGrades, is(nullValue()));
	}

	private Course getCourse(int courseId) {
		Course course = new Course();
		course.setCourseId(courseId);
		course.setCourseName("Service Oriented Architecture");
		course.setCourseAnnotation("course annotation");
		course.setExaminationForm("written & tests");
		course.setLiterature("Web Services");
		course.setPreliminaryRequirements("good programming skills");
		course.setSummary("Course summary");
		course.setWorkload(SucesTestUtil.getPositiveNonZeroNumber(random, 6));
		course.setYear(SucesTestUtil.getGreaterPositiveNumber(random, 2200,
				1971));
		course.setRating(SucesTestUtil.getPositiveNonZeroNumber(random, 7));
		course.setRatingCounter(SucesTestUtil.getPositiveNonZeroNumber(random,
				100));
		return course;
	}

	private Semester getSemester(int semesterId) {
		Semester semester = new Semester();
		semester.setSemesterId(semesterId);
		semester.setSemesterName("Semester constant name");
		return semester;
	}

	private Specialty getSpecialty(int specialtyId) {
		Specialty specialty = new Specialty();
		specialty.setSpecialtyId(specialtyId);
		specialty.setSpecialtyName("Specialty constant name");
		return specialty;
	}

	private Grade getGrade(int gradeID) {
		Grade grade = new Grade();
		grade.setGradeId(gradeID);
		grade.setGradeNumber(SucesTestUtil.getPositiveNonZeroNumber(random, 5));
		return grade;
	}
}
