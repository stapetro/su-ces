package bg.sofia.uni.fmi.ces.model.facade.lecturer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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

import bg.sofia.uni.fmi.ces.model.course.Lecturer;
import bg.sofia.uni.fmi.ces.model.util.MockHelper;
import bg.sofia.uni.fmi.ces.model.util.SucesTestUtil;

/**
 * Tests lecturer persistence facade.
 * 
 * @author Staskata
 * 
 */
public class LecturerPersistenceTest {

	private static final String[] LECTURER_NAMES = { "Steve O`Connell",
			"Stewart Handsome", "Chris Duke", "Bruce Eckel" };
	private Random random;
	@Mock
	private Query mockedQuery;
	private EntityManager mockedEntityMgr;
	private LecturerPersistence lecturerPersistence;

	@Before
	public void setUP() {
		MockitoAnnotations.initMocks(this);
		EntityManagerFactory mockedEntityMgrFactory = MockHelper
				.getEntityManagerFactory();
		lecturerPersistence = new LecturerPersistence(mockedEntityMgrFactory);
		mockedEntityMgr = mockedEntityMgrFactory.createEntityManager();
		this.random = new Random(System.currentTimeMillis());
	}

	@After
	public void tearDown() {
		lecturerPersistence = null;
		mockedQuery = null;
		mockedEntityMgr = null;
		this.random = null;
	}

	@Test
	public void testGetAllLecturers() {
		when(
				mockedEntityMgr
						.createQuery(LecturerPersistence.SELECT_ALL_LECTURERS))
				.thenReturn(mockedQuery);
		final Integer[] lecturerIDs = { 1, 2, 3 };
		List<Lecturer> lecturers = new ArrayList<>();
		for (Integer lecturerID : lecturerIDs) {
			Lecturer lecturer = getLecturer(lecturerID);
			lecturers.add(lecturer);
		}
		when(mockedQuery.getResultList()).thenReturn(lecturers);
		// case 0: semester list with semesters
		List<Lecturer> actualLecturers = lecturerPersistence.getAllLecturers();
		assertEquals(lecturers, actualLecturers);
		// case 1: semester list is empty
		lecturers.clear();
		actualLecturers = lecturerPersistence.getAllLecturers();
		assertEquals(lecturers, actualLecturers);
		// case 2: semester list is NULL
		lecturers = null;
		when(mockedQuery.getResultList()).thenReturn(lecturers);
		actualLecturers = lecturerPersistence.getAllLecturers();
		assertNull(actualLecturers);

	}

	private Lecturer getLecturer(int lecturerID) {
		Lecturer lecturer = new Lecturer();
		lecturer.setLecturerId(lecturerID);
		lecturer.setLecturerName(SucesTestUtil.getRandomValue(random,
				LECTURER_NAMES));
		return lecturer;
	}

}
