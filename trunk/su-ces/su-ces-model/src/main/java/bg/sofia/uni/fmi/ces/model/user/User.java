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
	private int userId;
	private Timestamp createDate;
	private String email;
	private Timestamp lastLoginDate;
	private String password;
	private List<Role> roles;

    public User() {
    	this.roles = new ArrayList<>();
    }
    
    public User(String email, String password) {
    	this();
    	this.email = email;
    	this.password = password;
    	this.createDate = new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id", unique=true, nullable=false)
	public int getUserId() {
		return this.userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}


	@Column(name="create_date", nullable=false)
	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}


	@Column(unique=true, nullable=false, length=64)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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
			@JoinColumn(name="user_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="role_id", nullable=false)
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
				this.email, this.createDate);
	}
}