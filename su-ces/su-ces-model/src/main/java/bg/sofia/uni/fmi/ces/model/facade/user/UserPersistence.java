package bg.sofia.uni.fmi.ces.model.facade.user;

import java.io.Serializable;

import bg.sofia.uni.fmi.ces.model.facade.ModelFacade;
import bg.sofia.uni.fmi.ces.model.user.User;

public class UserPersistence extends ModelFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6537504030117485331L;

	public UserPersistence() {
		super();
	}

	/**
	 * Add or updates user.
	 * @param user New or existing user to be specified.
	 * @return Persisted user.
	 */
	public User save(User user) {
		if (user == null) {
			return user;
		}
		User existingUser = this.entityManager.find(user.getClass(),
				user.getUserEmail());
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

}
