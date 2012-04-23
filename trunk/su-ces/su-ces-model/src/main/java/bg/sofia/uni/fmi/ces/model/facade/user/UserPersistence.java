package bg.sofia.uni.fmi.ces.model.facade.user;

import java.io.Serializable;

import bg.sofia.uni.fmi.ces.model.facade.ModelFacade;
import bg.sofia.uni.fmi.ces.model.user.User;

public class UserPersistence extends ModelFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5823604968128009655L;

	public UserPersistence() {
		super();
	}

	/**
	 * Add or updates user.
	 * 
	 * @param user
	 *            New or existing user to be specified.
	 * @return Persisted user.
	 */
	public User save(User user) {
		if (user == null) {
			return user;
		}
		User existingUser = getUser(user.getClass(), user.getUserEmail());
		if (existingUser == null) {
			existingUser = user;
		} else {
			existingUser.setUserEmail(user.getUserEmail());
			existingUser.setPassword(user.getPassword());
		}
		try {
			beginTransaction();
			persist(existingUser);
			commitTransaction();
		} catch (Exception exception) {
			getLogger().error(exception);
			rollbackTransaction();
		} finally {
			close();
		}
		return existingUser;
	}

	public boolean isUserExists(String email) {
		if (email == null || email.isEmpty()) {
			getLogger().error("Email is NULL or empty");
			return false;
		}
		User existingUser = getUser(User.class, email);
		if (existingUser != null) {
			return true;
		}
		return false;
	}

	private User getUser(Class<? extends User> entityClass, String email) {
		User user = this.entityManager.find(entityClass, email);
		return user;
	}

}
