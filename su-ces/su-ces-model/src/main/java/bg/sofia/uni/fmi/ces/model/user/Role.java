package bg.sofia.uni.fmi.ces.model.user;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Represents user role.
 * @author Staskata
 *
 */
@Entity
@Table(
		name="roles",
        uniqueConstraints=
            @UniqueConstraint(columnNames={"role_name"})
    )
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
	private Long id;
	@Column(name="role_name", length=128)
	private String name;
	private String description;
	@ManyToMany(mappedBy="roles")
	private Collection<User> users;
	
	public Role() {
		this.users = new ArrayList<>();
	}
	
	public Role(String name, String desc) {
		this();
		this.name = name;
		this.description = desc;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Collection<User> getUsers() {
		return users;
	}
	public void setUsers(Collection<User> users) {
		this.users = users;
	}
	
	public void addUser(User user) {
		if(this.users.contains(user) == false) {
			this.users.add(user);
		}
		Collection<Role> userRoles = user.getRoles();
		if(userRoles.contains(this) == false) {
			userRoles.add(this);
		}
	}
}
