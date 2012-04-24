package bg.sofia.uni.fmi.ces.model.course;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the lecturer database table.
 * 
 */
@Entity
@Table(name="lecturer")
public class Lecturer implements Serializable {
	private static final long serialVersionUID = 1L;
	private int lecturerId;
	private String lecturerName;
	private List<Course> courses;

    public Lecturer() {
    }


	@Id
	@SequenceGenerator(name="LECTURER_LECTURERID_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="LECTURER_LECTURERID_GENERATOR")
	@Column(name="lecturer_id", unique=true, nullable=false)
	public int getLecturerId() {
		return this.lecturerId;
	}

	public void setLecturerId(int lecturerId) {
		this.lecturerId = lecturerId;
	}


	@Column(name="lecturer_name", nullable=false, length=255)
	public String getLecturerName() {
		return this.lecturerName;
	}

	public void setLecturerName(String lecturerName) {
		this.lecturerName = lecturerName;
	}


	//bi-directional many-to-one association to Course
	@OneToMany(mappedBy="lecturer")
	public List<Course> getCourses() {
		return this.courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
}