package bg.sofia.uni.fmi.ces.jsf.beans;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import bg.sofia.uni.fmi.ces.model.course.Course;
import bg.sofia.uni.fmi.ces.model.course.Semester;
import bg.sofia.uni.fmi.ces.model.course.Specialty;
import bg.sofia.uni.fmi.ces.model.facade.course.CoursePersistence;

@ManagedBean(name = "courseBean")
@ViewScoped
public class CourseBean implements Serializable {

	private static final long serialVersionUID = -1373151780844385607L;

	private Course course;

	private CoursePersistence coursePersistence;

	public CourseBean() {
		coursePersistence = new CoursePersistence();

		// TODO getting course 1...when course searching is implemented this one
		// should be finished
		course = coursePersistence.geCourseById(1);
	}
	
	public void saveCourse(ActionEvent event){
		System.out.println("-------- SAVING COURSE ---------");
	}
	
	public List<Semester> getSemesterList(){
		List<Semester> semesterList = coursePersistence.getSemesters();
		return semesterList;
	}
	
	public List<Specialty> getSpecialtyList(){
		List<Specialty> specialtyList = coursePersistence.getSpecialties();
		
		return specialtyList;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
}
