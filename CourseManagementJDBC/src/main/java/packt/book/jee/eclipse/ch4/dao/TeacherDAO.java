package packt.book.jee.eclipse.ch4.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import packt.book.jee.eclipse.ch4.bean.Teacher;
import packt.book.jee.eclipse.ch4.db.connection.DatabaseConnectionFactory;

public class TeacherDAO {

	public static void addTeacher(Teacher teacher) throws SQLException {
		// Get connection from connection pool
		Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
		try {
			final String sql = "insert into Teacher (first_name, last_name, designation) values (?,?,?)";
			
			// Create the prepared statement with an option to get auto-generated keys
			PreparedStatement stmt = con.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			
			// Set parameters
			stmt.setString(1, teacher.getFirstName());
			stmt.setString(2, teacher.getLastName());
			stmt.setString(3, teacher.getDesignation());
			
			stmt.execute();
			
			// Get auto-generated keys
			ResultSet rs = stmt.getGeneratedKeys();
			
			if (rs.next())
				teacher.setId(rs.getInt(1));
			
			rs.close();
			stmt.close();
		}
		finally {
			con.close();
		}
	}

	public static List<Teacher> getTeachers() throws SQLException {
		// Get connection from connection pool
		Connection con = DatabaseConnectionFactory.getConnectionFactory().getConnection();
		List<Teacher> teachers = new ArrayList<Teacher>();
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = con.createStatement();
			// Create SQL statement using left outer join
			StringBuilder sb = new StringBuilder("select id as id, first_name as firstName, ")
					.append("last_name as lastName, designation as designation ")
					.append("from teacher ")
					.append("order by firstName");
			// Execute the query
			rs = stmt.executeQuery(sb.toString());
			// Iterate over result set and create Teacher objects 
			// Add them to teacher list
			while  (rs.next()) {
				Teacher teacher = new Teacher();
				teacher.setId(rs.getInt("id"));
				teacher.setFirstName(rs.getString("firstName"));
				teacher.setLastName(rs.getString("lastName"));
				teacher.setDesignation(rs.getString("designation"));
				teachers.add(teacher);
			}
			return teachers;
		}
		finally {
			try {if (rs != null) rs.close();} catch (SQLException e) {}
			try {if (stmt != null) stmt.close();} catch (SQLException e) {}
			try {con.close();} catch (SQLException e) {}
		}
	}

}
