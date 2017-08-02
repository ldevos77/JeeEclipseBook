package packt.book.jee.eclipse.ch4.bean;

import java.sql.SQLException;
import java.util.List;

import packt.book.jee.eclipse.ch4.dao.CourseDAO;

public class Course {
	private int id;
	private String name;
	private int credits;
	private int teacherId;
	private Teacher teacher;
	private int maxStudents;
	
	//private CourseDAO courseDAO = new CourseDAO();
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getCredits() {
		return credits;
	}
	
	public void setCredits(int credits) {
		this.credits = credits;
	}
	
	public boolean isValidCourse() {
		return name != null && credits != 0 && name.trim().length() > 0;
	}
	
	public void addCourse() throws SQLException {
		CourseDAO.addCourse(this);
	}
	
	public int getTeacherId() {
		return teacherId;
	}
	
	public void setTeacherId(int teacherId) {
		this.teacherId = teacherId;
	}
	
	public Teacher getTeacher() {
		return this.teacher;
	}
	
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public List<Course> getCourses() throws SQLException {
		return CourseDAO.getCourses();
	}
	
	public void addStudent(Student student) throws Exception, SQLException {
		// Get current enrolement first
		int currentEnrolment = CourseDAO.getNumStudentsInCourse(id);
		if (currentEnrolment >= this.getMaxStudents()) 
			throw new Exception("Course is full. Enrolment closed.");
		CourseDAO.enrolStudentsInCourse(getId(), student.getId());
	}

	public int getMaxStudents() {
		return maxStudents;
	}

	public void setMaxStudents(int maxStudents) {
		this.maxStudents = maxStudents;
	}
}
