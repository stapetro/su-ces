package bg.sofia.uni.fmi.ces.model.course;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the specialty database table.
 * 
 */
@Entity
@Table(name="specialty")
public class Specialty implements Serializable {
	private static final long serialVersionUID = 1L;
	private int specialtyId;
	private String specialtyName;
	private List<Course> courses;

    public Specialty() {
    }


	@Id
	@SequenceGenerator(name="SPECIALTY_SPECIALTYID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SPECIALTY_SPECIALTYID_GENERATOR")
	@Column(name="specialty_id", unique=true, nullable=false)
	public int getSpecialtyId() {
		return this.specialtyId;
	}

	public void setSpecialtyId(int specialtyId) {
		this.specialtyId = specialtyId;
	}


	@Column(name="specialty_name", nullable=false, length=255)
	public String getSpecialtyName() {
		return this.specialtyName;
	}

	public void setSpecialtyName(String specialtyName) {
		this.specialtyName = specialtyName;
	}


	//bi-directional many-to-many association to Course
	@ManyToMany(mappedBy="specialties")
	public List<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
}