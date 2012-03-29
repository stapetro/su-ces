package bg.sofia.uni.fmi.ces.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Represents user.
 * @author Staskata
 *
 */
@Entity
@Table(
		name="users",
        uniqueConstraints=
            @UniqueConstraint(columnNames={"email"})
    )
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	private String email;
	private String password;
	@Column(name="create_date")
	private java.sql.Date createDate;
	@Column(name="last_login_date")
	private java.sql.Date lastLoginDate;
}
