package packt.jee.course_management.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import packt.jee.course_management.dto.CourseDTO;

@Repository
public class CourseDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public List<CourseDTO> getCourses() {
		List<CourseDTO> courses = jdbcTemplate.query("select * from course", new CourseRowMapper());
		return courses;
	}

	public static final class CourseRowMapper implements RowMapper<CourseDTO> {
		@Override
		public CourseDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			CourseDTO course = new CourseDTO();
			course.setId(rs.getInt("id"));
			course.setName(rs.getString("name"));
			course.setCredits(rs.getInt("credits"));
			return course;
		}
	}
}

