package packt.book.jee.eclipse.ch4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import packt.book.jee.eclipse.ch4.bean.Course;
import packt.book.jee.eclipse.ch4.bean.Teacher;
import packt.book.jee.eclipse.ch4.db.connection.DatabaseConnectionFactory;

public class CourseDAO {
	
	public static void addCourse(Course course) throws SQLException {
		// Get connection from connection pool
		Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
		try {
			final String sql = "insert into Course (name, credits, Teacher_id) values (?,?,?)";
			
			// Create the prepared statement with an option to get auto-generated keys
			PreparedStatement stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			// Set parameters
			stmt.setString(1, course.getName());
			stmt.setInt(2, course.getCredits());
			if (course.getTeacherId() == 0) {
				stmt.setNull(3, Types.INTEGER);
			}
			else {
				stmt.setInt(3, course.getTeacherId());
			}
			stmt.execute();
			
			// Get auto-generated keys
			ResultSet rs = stmt.getGeneratedKeys();
			
			if (rs.next())
				course.setId(rs.getInt(1));
			
			rs.close();
			stmt.close();
		}
		finally {
			con.close();
		}
	}

	public static List<Course> getCourses() throws SQLException {
		// Get connection from connection pool
		Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
		List<Course> courses = new ArrayList<Course>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			// Create SQL statement using left outer join
			StringBuilder sb = new StringBuilder("select course.id as courseId, ")
					.append("course.name as courseName, ")
					.append("course.credits as credits, ")
					.append("teacher.id as teacherId, ")
					.append("teacher.first_name as firstName, ")
					.append("teacher.last_name as lastName, ")
					.append("teacher.designation as designation ")
					.append("from course left outer join teacher on ")
					.append("course.teacher_id=teacher.id ")
					.append("order by course.name");
			// Execute the query
			rs = stmt.executeQuery(sb.toString());
			// Iterate over result set and create Course objects 
			// Add them to course list
			while  (rs.next()) {
				Course course = new Course();
				course.setId(rs.getInt("courseId"));
				course.setName(rs.getString("courseName"));
				course.setCredits(rs.getInt("credits"));
				courses.add(course);
				int teacherId = rs.getInt("teacherId");
				// Check wether teacher id was null in the table
				if (rs.wasNull()) // No teacher set for this course
					continue;
				Teacher teacher = new Teacher();
				teacher.setId(teacherId);
				teacher.setFirstName(rs.getString("firstName"));
				teacher.setLastName(rs.getString("lastName"));
				teacher.setDesignation(rs.getString("designation"));
				course.setTeacher(teacher);
			}
			return courses;
		}
		finally {
			try {if (rs != null) rs.close();} catch (SQLException e) {}
			try {if (stmt != null) stmt.close();} catch (SQLException e) {}
			try {con.close();} catch (SQLException e) {}
		}
	}
	
	public static int getNumStudentsInCourse(int courseId) throws SQLException {
		// Get connection from connection pool
		Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
		int numStudentsInCourse = 0;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			// Create SQL statement 
			StringBuilder sb = new StringBuilder("select count(*) as numStudents ")
					.append("from course_student ")
					.append("where Course_id=?");
			stmt = con.prepareStatement(sb.toString());
			stmt.setInt(1, courseId);
			// Execute the query
			rs = stmt.executeQuery(sb.toString());
			if  (rs.next()) {
				numStudentsInCourse = rs.getInt("numStudents");
			}
			return numStudentsInCourse;
		}
		finally {
			try {if (rs != null) rs.close();} catch (SQLException e) {}
			try {if (stmt != null) stmt.close();} catch (SQLException e) {}
			try {con.close();} catch (SQLException e) {}
		}
	}
	
	public static void enrolStudentsInCourse(int courseId, int studentId) throws SQLException {
		// Get connection from connection pool
		Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
		try {
			final String sql = "insert into course_student (Course_id, Student_id) values (?,?)";
			
			// Create the prepared statement
			PreparedStatement stmt = con.prepareStatement(sql);
			
			// Set parameters
			stmt.setInt(1, courseId);
			stmt.setInt(2, studentId);
			stmt.execute();
			stmt.close();
		}
		finally {
			con.close();
		}
	}
}
