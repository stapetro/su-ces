package bg.sofia.uni.fmi.ces.model.util;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class MockHelper {

	public static EntityManagerFactory getEntityManagerFactory() {
		EntityManagerFactory mockedEntityMgrFactory = mock(EntityManagerFactory.class);
		when(mockedEntityMgrFactory.isOpen()).thenReturn(true);
		EntityManager mockedEntityMgr = mock(EntityManager.class);
		when(mockedEntityMgr.isOpen()).thenReturn(true);
		EntityTransaction mockedTransaction = mock(EntityTransaction.class);
		when(mockedEntityMgr.getTransaction()).thenReturn(mockedTransaction);
		when(mockedEntityMgrFactory.createEntityManager()).thenReturn(
				mockedEntityMgr);
		return mockedEntityMgrFactory;
	}

}
