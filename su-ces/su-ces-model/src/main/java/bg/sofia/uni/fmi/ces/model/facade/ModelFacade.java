package bg.sofia.uni.fmi.ces.model.facade;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class ModelFacade {

	public void persist(Object object) {
		if (object == null) {
			return;
		}

		EntityManagerFactory emFactory = Persistence
				.createEntityManagerFactory("sucesPU");
		EntityManager entityMgr = emFactory.createEntityManager();
		EntityTransaction transaction = entityMgr.getTransaction();
		transaction.begin();

		entityMgr.persist(object);

		entityMgr.flush();

		transaction.commit();
		entityMgr.close();
		emFactory.close();
	}
}
