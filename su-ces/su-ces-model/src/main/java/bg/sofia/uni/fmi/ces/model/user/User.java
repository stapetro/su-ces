package bg.sofia.uni.fmi.ces.model.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
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
 * Represents user.
 * 
 * @author Staskata
 * 
 */
@Entity
@Table(name = "users", uniqueConstraints = @UniqueConstraint(columnNames = { "email" }))
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;
	private String email;
	@Column(length = 128)
	private String password;
	@Column(name = "create_date", nullable=false)
	private java.sql.Timestamp createDate;
	@Column(name = "last_login_date")
	private java.sql.Timestamp lastLoginDate;
	@ManyToMany
	private Collection<Role> roles;
	
	public User() {
		this.roles = new ArrayList<>();
	}
	
	public User(String email, String password) {
		this();
		this.email = email;
		this.password = password;
		long todayInMillis = Calendar.getInstance().getTimeInMillis();
		this.createDate = new Timestamp(todayInMillis);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public java.sql.Timestamp getCreateDate() {
		return createDate;
	}

	public void setCreateDate(java.sql.Timestamp createDate) {
		this.createDate = createDate;
	}

	public java.sql.Timestamp getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(java.sql.Timestamp lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	public void addRole(Role role) {
		if (this.roles.contains(role) == false) {
			this.roles.add(role);
		}
		 Collection<User> users = role.getUsers();
		if (users.contains(this) == false) {
			users.add(this);
		}
	}
}
