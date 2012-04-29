package bg.sofia.uni.fmi.ces.model.facade.user;

import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import bg.sofia.uni.fmi.ces.model.user.Role;
import bg.sofia.uni.fmi.ces.model.user.User;
import bg.sofia.uni.fmi.ces.model.util.MockHelper;

/**
 * Tests user persister.
 * 
 * @author Staskata
 * 
 */
public class UserPersistenceTest {

	private UserPersistence userPersistence;
	private User existingUserData;

	@Before
	public void setUP() {
		EntityManagerFactory mockedEntityMgrFactory = MockHelper
				.getEntityManagerFactory();
		userPersistence = new UserPersistence(mockedEntityMgrFactory);
		EntityManager mockedEntityMgr = mockedEntityMgrFactory
				.createEntityManager();
		existingUserData = getExistingUserTestData();
		when(mockedEntityMgr.find(User.class, existingUserData.getUserEmail()))
				.thenReturn(existingUserData);
	}

	@After
	public void setDown() {
		userPersistence.close();
		userPersistence = null;
		existingUserData = null;
	}

	/**
	 * Tests creating new user and updating existing one.
	 */
	@Test
	public void testSaveUser() {
		User[] newUsers = getNewUsersTestData();
		for (User newExpectedUser : newUsers) {
			User actualUser = userPersistence.save(newExpectedUser);
			Assert.assertNotNull(actualUser);
			Assert.assertEquals(newExpectedUser.getUserEmail(),
					actualUser.getUserEmail());
			Assert.assertEquals(newExpectedUser.getPassword(),
					actualUser.getPassword());
			Assert.assertNotNull(actualUser.getCreateDate());
		}
		Role testAdminRole = new Role("testAdminRole", "Test admin role desc");
		existingUserData.addRole(testAdminRole);
		String newPass = "newTestPass";
		existingUserData.setPassword(newPass);
		User updatedUser = userPersistence.save(existingUserData);
		Assert.assertNotNull(updatedUser);
		Assert.assertEquals(newPass, updatedUser.getPassword());
		Assert.assertEquals(existingUserData.getUserEmail(),
				updatedUser.getUserEmail());
		List<Role> updatedRoles = updatedUser.getRoles();
		Assert.assertEquals(existingUserData.getRoles().size(),
				updatedRoles.size());
		for (Role currRole : updatedRoles) {
			if (currRole.getRoleName().equals(testAdminRole.getRoleName())
					&& currRole.getDescription().equals(
							testAdminRole.getDescription())) {
				return;
			}
		}
		Assert.assertTrue(String.format("Role '%s' not found for user '%s'",
				testAdminRole.getRoleName(), updatedUser.getUserEmail()), false);

	}

	/**
	 * Tests with existing and non-existing users.
	 */
	@Test
	public void testIsUserExists() {
		boolean isUserExists = userPersistence.isUserExists(existingUserData
				.getUserEmail());
		Assert.assertTrue(isUserExists);
		isUserExists = userPersistence.isUserExists("nonExistingUserEmail");
		Assert.assertFalse(isUserExists);
	}

	private User getExistingUserTestData() {
		User user = new User("existingUserEmail", "existingPass");
		Calendar todayCal = Calendar.getInstance();
		Timestamp today = new Timestamp(todayCal.getTimeInMillis());
		user.setCreateDate(today);
		Role testStudentRole = new Role("testStudentRole",
				"Test student role desc");
		user.addRole(testStudentRole);
		return user;
	}

	private User[] getNewUsersTestData() {
		User newUser = new User("test@uni-sofia.bg", "Pa$$w0rd");
		Role testStudentRole = new Role("testStudentRole",
				"Test student role desc");
		List<Role> roles = new ArrayList<Role>();
		roles.add(testStudentRole);
		roles.add(new Role("testAdminRole", "Test admin role desc"));
		newUser.setRoles(roles);
		User newUser1 = new User("test1@fmi.com", "passFMI");
		newUser1.addRole(testStudentRole);
		User[] newUsers = { newUser, newUser1 };
		return newUsers;
	}
}
