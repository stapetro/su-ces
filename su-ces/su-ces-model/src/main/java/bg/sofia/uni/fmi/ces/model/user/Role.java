package bg.sofia.uni.fmi.ces.model.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


/**
 * The persistent class for the roles database table.
 * 
 */
@Entity
@Table(name="roles")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;
	private String roleName;
	private String description;
	private List<User> users;

    public Role() {
    	this.users = new ArrayList<>();
    }

    public Role(String roleName, String desc) {
    	this();
    	this.roleName = roleName;
    	this.description = desc;
    }

	@Column(length=256)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


	@Id
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