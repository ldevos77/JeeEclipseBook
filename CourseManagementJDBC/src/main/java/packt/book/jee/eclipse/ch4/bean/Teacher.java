package packt.book.jee.eclipse.ch4.bean;

import java.sql.SQLException;
import java.util.List;

import packt.book.jee.eclipse.ch4.dao.TeacherDAO;

public class Teacher extends Person {
	private String designation;

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}
	
	public boolean isValidTeacher() {
		return this.getFirstName() != null && designation != null;
	}
	
	public void addTeacher() throws SQLException {
		TeacherDAO.addTeacher(this);
	}
	
	public List<Teacher> getTeachers() throws SQLException {
		return TeacherDAO.getTeachers();
	}
}
