package bg.sofia.uni.fmi.ces.model.facade.user;

import java.io.Serializable;

import javax.persistence.EntityManagerFactory;

import bg.sofia.uni.fmi.ces.model.facade.ModelFacade;
import bg.sofia.uni.fmi.ces.model.user.Role;

/**
 * User roles perstistence facade.
 * 
 * @author Staskata
 * 
 */
public class RolePersistence extends ModelFacade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7836918311975717937L;
	public static final String STUDENT_ROLE_NAME = "student";
	public static final String ADMIN_ROLE_NAME = "admin";

	public RolePersistence() {
		super();
	}

	/**
	 * Makes role persistence testable.
	 * @param entityManagerFactory Entity manager factory to be specified.
	 */
	public RolePersistence(EntityManagerFactory entityManagerFactory) {
		super(entityManagerFactory);
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
