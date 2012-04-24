package bg.sofia.uni.fmi.ces.model.course;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the semester database table.
 * 
 */
@Entity
@Table(name="semester")
public class Semester implements Serializable {
	private static final long serialVersionUID = 1L;
	private int semesterId;
	private String semesterName;
	private List<Course> courses;

    public Semester() {
    }


	@Id
	@SequenceGenerator(name="SEMESTER_SEMESTERID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SEMESTER_SEMESTERID_GENERATOR")
	@Column(name="semester_id", unique=true, nullable=false)
	public int getSemesterId() {
		return this.semesterId;
	}

	public void setSemesterId(int semesterId) {
		this.semesterId = semesterId;
	}


	@Column(name="semester_name", nullable=false, length=255)
	public String getSemesterName() {
		return this.semesterName;
	}

	public void setSemesterName(String semesterName) {
		this.semesterName = semesterName;
	}


	//bi-directional many-to-one association to Course
	@OneToMany(mappedBy="semester")
	public List<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
}