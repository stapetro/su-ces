package bg.sofia.uni.fmi.ces.model.facade.user;

import bg.sofia.uni.fmi.ces.model.facade.ModelFacade;
import bg.sofia.uni.fmi.ces.model.user.Role;

/**
 * User roles perstistence facade.
 * @author Staskata
 *
 */
public class RolePersistence extends ModelFacade {

	public static final String STUDENT_ROLE_NAME = "student";
	public static final String ADMIN_ROLE_NAME = "admin";

	public RolePersistence() {
		super();
	}

	/**
	 * Gets user role from persistence.
	 * 
	 * @param roleName
	 *            Role name to be specified. (PK)
	 * @return Existing user role or null - if role name is null or empty or
	 *         role does not exist with that name.
	 */
	public Role getRole(String roleName) {
		if (roleName == null || roleName.isEmpty()) {
			return null;
		}
		assert (this.entityManager != null) : "Entity manager is NULL";
		Role role = this.entityManager.find(Role.class, roleName);
		return role;
	}
}
