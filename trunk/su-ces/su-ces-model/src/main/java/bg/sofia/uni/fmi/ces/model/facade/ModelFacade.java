package bg.sofia.uni.fmi.ces.model.facade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public abstract class ModelFacade implements IModelFacade {
	
	private static final String PERSISTANCE_UNIT_NAME = "sucesPU";

	protected EntityManagerFactory entityManagerFactory;
	protected EntityManager entityManager;

	public ModelFacade() {
		entityManagerFactory = Persistence
				.createEntityManagerFactory(PERSISTANCE_UNIT_NAME);
		entityManager = entityManagerFactory.createEntityManager();
	}

	@Override
	public void persist(Object object) {
		if (object == null) {
			return;
		}

		// EntityTransaction transaction = entityManager.getTransaction();
		// transaction.begin();

		entityManager.persist(object);

		// entityManager.flush();
		// transaction.commit();
	}

	@Override
	public void close() {
		entityManager.close();
		entityManagerFactory.close();

	}

	@Override
	public void beginTransaction() {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
	}

	@Override
	public void commitTransaction() {
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.commit();
	}

}
