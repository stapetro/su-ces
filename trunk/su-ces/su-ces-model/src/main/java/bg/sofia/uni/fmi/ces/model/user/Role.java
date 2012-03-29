package bg.sofia.uni.fmi.ces.model.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column(name="role_name")
	private String name;
	private String description;
}
