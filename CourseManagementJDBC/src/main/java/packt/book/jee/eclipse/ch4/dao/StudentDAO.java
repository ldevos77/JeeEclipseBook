package packt.book.jee.eclipse.ch4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import packt.book.jee.eclipse.ch4.bean.Student;
import packt.book.jee.eclipse.ch4.db.connection.DatabaseConnectionFactory;

public class StudentDAO {

	public static void addStudent(Student student) throws SQLException {
		// Get connection from connection pool
		Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
		try {
			final String sql = "insert into Student (first_name, last_name, enrolled_since) values (?,?,?)";
			
			// Create the prepared statement with an option to get auto-generated keys
			PreparedStatement stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			// Set parameters
			stmt.setString(1, student.getFirstName());
			stmt.setString(2, student.getLastName());
			stmt.setLong(3, student.getEnrolledSince());
			
			stmt.execute();
			
			// Get auto-generated keys
			ResultSet rs = stmt.getGeneratedKeys();
			
			if (rs.next())
				student.setId(rs.getInt(1));
			
			rs.close();
			stmt.close();
		}
		finally {
			con.close();
		}
	}

	public static List<Student> getStudents() throws SQLException {
		// Get connection from connection pool
		Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
		List<Student> students = new ArrayList<Student>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			// Create SQL statement using left outer join
			StringBuilder sb = new StringBuilder("select id as id, first_name as firstName,  ")
					.append("last_name as lastName, enrolled_since as enrolledSince ")
					.append("from student ")
					.append("order by firstName");
			// Execute the query
			rs = stmt.executeQuery(sb.toString());
			// Iterate over result set and create Student objects 
			// Add them to student list
			while  (rs.next()) {
				Student student = new Student();
				student.setId(rs.getInt("id"));
				student.setFirstName(rs.getString("firstName"));
				student.setLastName(rs.getString("lastName"));
				student.setEnrolledSince(rs.getLong("enrolledSince"));
				students.add(student);
			}
			return students;
		}
		finally {
			try {if (rs != null) rs.close();} catch (SQLException e) {}
			try {if (stmt != null) stmt.close();} catch (SQLException e) {}
			try {con.close();} catch (SQLException e) {}
		}
	}
	
}
