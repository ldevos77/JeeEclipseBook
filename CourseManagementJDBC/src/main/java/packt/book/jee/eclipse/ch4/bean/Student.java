package packt.book.jee.eclipse.ch4.bean;

import java.sql.SQLException;
import java.util.List;

import packt.book.jee.eclipse.ch4.dao.StudentDAO;

public class Student extends Person {
	private long enrolledSince;

	public long getEnrolledSince() {
		return enrolledSince;
	}

	public void setEnrolledSince(long enrolledSince) {
		this.enrolledSince = enrolledSince;
	}

	public boolean isValidStudent() {
		return this.getFirstName() != null && enrolledSince != 0;
	}
	
	public void addStudent() throws SQLException {
		StudentDAO.addStudent(this);
	}
	
	public List<Student> getStudents() throws SQLException {
		return StudentDAO.getStudents();
	}
}
