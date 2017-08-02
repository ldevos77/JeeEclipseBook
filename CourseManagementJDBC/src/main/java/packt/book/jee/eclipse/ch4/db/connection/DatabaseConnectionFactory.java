package packt.book.jee.eclipse.ch4.db.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

/**
 * Singleton Factory class to create JDBC database connections
 */
public class DatabaseConnectionFactory {
	
	//Singleton instance
	private static DatabaseConnectionFactory conFactory = new DatabaseConnectionFactory();

	private DataSource dataSource = null;
	
	//Make the construction private
	private DatabaseConnectionFactory() {
	}
	
	/**
	 * Must be call before any other method in this class.
	 * Initialize the data source and save it in a instance variable.
	 * 
	 * @throws IOException
	 */
	public synchronized void init() throws IOException {
		//Check if init was already called
		if (dataSource != null)
			return;
		
		//Load db.properties file first
		InputStream inStream = this.getClass().getClassLoader().getResourceAsStream("db.properties");
		Properties dbProperties = new Properties();
		dbProperties.load(inStream);
		inStream.close();
		
		//Control if the JDBC driver is in class path
		try {
			Class.forName(dbProperties.getProperty("db_driver_class_name"));
		}
		catch (ClassNotFoundException e) {
			// TODO : Throw Exception  
			return;
		}
		
		//Create Tomcat specific pool properties
		PoolProperties p = new PoolProperties();
		p.setUrl("jdbc:mysql://"+dbProperties.getProperty("db_host")
				+ ":" + dbProperties.getProperty("db_port")
				+ "/" + dbProperties.getProperty("db_name")
				+ "?useSSL=" + dbProperties.getProperty("db_use_SSL"));
		
		p.setDriverClassName(dbProperties.getProperty("db_driver_class_name"));
		
		p.setUsername(dbProperties.getProperty("db_user_name"));
		p.setPassword(dbProperties.getProperty("db_password"));
		p.setMaxActive(10);
		p.setMaxIdle(p.getMaxActive());
		
		dataSource = new DataSource();
		dataSource.setPoolProperties(p);
	}
	
	//Provides access to singleton instance
	public static DatabaseConnectionFactory getConnectionFactory() {
		return conFactory;
	}
	
	//Returns database connection object
	public Connection getConnection() throws SQLException {
		if (dataSource == null)
			throw new SQLException("Error initializing datasource");
		return dataSource.getConnection();
	}
}
