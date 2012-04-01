package bg.sofia.uni.fmi.ces.model.user;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userEmail;
	private Timestamp createDate;
	private Timestamp lastLoginDate;
	private String password;
	private List<Role> roles;

    public User() {
    	this.roles = new ArrayList<>();
    }
    
    public User(String email, String password) {
    	this();
    	this.userEmail = email;
    	this.password = password;
    	this.createDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

	@Id
	@Column(name="user_email", unique=true, nullable=false, length=64)
	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	@Column(name="create_date", nullable=false)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}


	@Column(name="last_login_date")
	public Timestamp getLastLoginDate() {
		return this.lastLoginDate;
	}

	public void setLastLoginDate(Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}


	@Column(nullable=false, length=128)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}    

	//bi-directional many-to-many association to Role
    @ManyToMany
	@JoinTable(
		name="users_roles"
		, joinColumns={
			@JoinColumn(name="user_email", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_name", nullable=false)
			}
		)
	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		if(this.roles.contains(role) == false) {
			this.roles.add(role);
		}
		List<User> users = role.getUsers();
		if(users.contains(this) == false) {
			users.add(this);
		}
	}
	
	@Override
	public String toString() {
		return String.format("{\"name\" : \"%s\", \"created\" : \"%s\"}", 
				this.userEmail, this.createDate);
	}
}