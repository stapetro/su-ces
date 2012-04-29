package bg.sofia.uni.fmi.ces.model.facade.user;

import static org.mockito.Mockito.when;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bg.sofia.uni.fmi.ces.model.user.Role;
import bg.sofia.uni.fmi.ces.model.util.MockHelper;

/**
 * Tests role persistence. Fixture is implemented to support future test cases.
 * 
 * @author Staskata
 * 
 */
public class RolePersistenceTest {

	private RolePersistence rolePersistence;
	private Role[] roleTestData;

	/**
	 * Generates test data and creates role persistence mocks.
	 */
	@Before
	public void setUP() {
		EntityManagerFactory mockedEntityMgrFactory = MockHelper
				.getEntityManagerFactory();
		EntityManager mockedEntityMgr = mockedEntityMgrFactory
				.createEntityManager();
		roleTestData = getRoleData();
		when(mockedEntityMgr.find(Role.class, RolePersistence.ADMIN_ROLE_NAME))
				.thenReturn(roleTestData[0]);
		when(
				mockedEntityMgr.find(Role.class,
						RolePersistence.STUDENT_ROLE_NAME)).thenReturn(
				roleTestData[1]);
		rolePersistence = new RolePersistence(mockedEntityMgrFactory);
	}

	@After
	public void setDown() {
		rolePersistence.close();
		rolePersistence = null;
		roleTestData = null;
	}

	/**
	 * Tests retrieving roles from DB.
	 */
	@Test
	public void testGetRole() {
		for (Role expectedRole : roleTestData) {
			String expectedRoleName = expectedRole.getRoleName();
			Role currRole = rolePersistence.getRole(expectedRoleName);
			Assert.assertNotNull(currRole);
			Assert.assertEquals(expectedRoleName, currRole.getRoleName());
			String roleDesc = currRole.getDescription();
			Assert.assertEquals(expectedRole.getDescription(), roleDesc);
		}
		String fakeRoleName = "fakeRole";
		Role role = rolePersistence.getRole(fakeRoleName);
		Assert.assertNull(role);
	}

	/**
	 * Gets role-specific test data.
	 * 
	 * @return Test data for user roles.
	 */
	private Role[] getRoleData() {
		String adminRoleDesc = "Admin role desc";
		String studentRoleDesc = "Student role desc";
		Role[] roleTestData = {
				new Role(RolePersistence.ADMIN_ROLE_NAME, adminRoleDesc),
				new Role(RolePersistence.STUDENT_ROLE_NAME, studentRoleDesc) };
		return roleTestData;
	}
}
