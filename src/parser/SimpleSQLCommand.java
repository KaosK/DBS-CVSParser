package parser;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SimpleSQLCommand {

	private Connection connection;

	public SimpleSQLCommand(Connection connection) {
		this.connection = connection;
	}

	public void createTableAs(String newTableName, String selectColumns,
			String firstTable, String secondTable, String compareBy)
			throws SQLException {
		String command = "CREATE TABLE " + newTableName + " AS SELECT "
				+ selectColumns + " FROM " + firstTable + ", " + secondTable
				+ " WHERE " + firstTable + "." + compareBy + " = "
				+ secondTable + "." + compareBy;

		executeStatement(command);
	}

	private void executeStatement(String command) throws SQLException {
		Statement stmt = connection.createStatement();
		System.out.println("EXECUTING: " + command);
		stmt.executeUpdate(command);
		if (stmt != null) {
			stmt.close();
		}
	}

}
