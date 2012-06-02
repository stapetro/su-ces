package bg.sofia.uni.fmi.ces.model.facade.lecturer;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import bg.sofia.uni.fmi.ces.model.course.Lecturer;
import bg.sofia.uni.fmi.ces.model.facade.ModelFacade;

public class LecturerPersistence extends ModelFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6089915796603828443L;

	public static final String SELECT_ALL_LECTURERS = "SELECT l FROM Lecturer l";

	public LecturerPersistence() {
		super();
	}

	public LecturerPersistence(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
	}

	public List<Lecturer> getAllLecturers() {
		Query query = entityManager.createQuery(SELECT_ALL_LECTURERS);
		List<Lecturer> lecturersList = (List<Lecturer>) query.getResultList();

		return lecturersList;
	}

}
