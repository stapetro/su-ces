package bg.sofia.uni.fmi.ces.model.user;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Table(name="roles")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	private int roleId;
	private String description;
	private String roleName;
	private List<User> users;

    public Role() {
    	this.users = new ArrayList<>();
    }

    public Role(String roleName, String desc) {
    	this();
    	this.roleName = roleName;
    	this.description = desc;
    }

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id", unique=true, nullable=false)
	public int getRoleId() {
		return this.roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}


	@Column(length=256)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Column(name="role_name", unique=true, nullable=false, length=64)
	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	//bi-directional many-to-many association to User
	@ManyToMany(mappedBy="roles")
	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		if(this.users.contains(user) == false) {
			this.users.add(user);
		}
		List<Role> roles = user.getRoles();
		if(roles.contains(this) == false) {
			roles.add(this);
		}
	}
	
	@Override
	public String toString() {
		return String.format("{\"name\" : \"%s\"}", this.roleName);
	}
}