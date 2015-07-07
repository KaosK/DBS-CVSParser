package parser;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The creator class for the database connection.
 *
 */
public class ConnectionFactory {

	// Database - Setup
	private static String dbServer = "localhost";
	private static String dbPort = "15432";
	private static String dbName = "myapp";
	private static String dbUser = "myapp";
	private static String password = "dbpass";

	/**
	 * A factory method for creating a connection to the database.
	 * 
	 * @return Connection The connection to the database.
	 */
	public static Connection getConnection() {
		try {
			// loading the driver
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException cnfe) {
			System.out.println("The postgresql driver could not be loaded.");

			// print stacktrace
			cnfe.printStackTrace();
			System.exit(0);
		}

		Connection conn = null;

		try {
			// connect to the database
			conn = DriverManager.getConnection("jdbc:postgresql://" + dbServer
					+ ":" + dbPort + "/" + dbName, dbUser, password);
		} catch (SQLException sqle) {
			System.out.println("The connection could not be established.");

			// print stacktrace
			sqle.printStackTrace();
			System.exit(0);
		}

		System.out.println("Connection to database " + dbName + "@" + dbServer
				+ ":" + dbPort + " successfully established.");
		// System.out.println(conn.getMetaData().getDatabaseProductName());

		return conn;
	}
}
