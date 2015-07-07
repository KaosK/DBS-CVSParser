package parser.tables;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Set;

import parser.SimpleTable;

public abstract class AbstractTable {

	private String tableName;
	private Connection connection;

	public AbstractTable(String tableName, Connection connection) {
		this.tableName = tableName;
		this.connection = connection;
	}

	abstract public void createTable() throws SQLException;

	protected void createTableHelper(String... sqlCommands) throws SQLException {
		StringBuilder createTableString = new StringBuilder();
		createTableString.append("create table " + tableName + " (");
		for (int i = 0; i < sqlCommands.length; i++) {
			createTableString.append(sqlCommands[i]);
			if (i < sqlCommands.length - 1) {
				createTableString.append(", ");
			}
		}
		createTableString.append(")");

		System.out.println("EXECUTING: " + createTableString.toString());
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(createTableString.toString());
		if (stmt != null) {
			stmt.close();
		}
	}

	protected void fillTableHelper(String columnName, Set<String> data)
			throws SQLException {
		String fillTableString = "insert into " + tableName + " (" + columnName
				+ ") values(?)";

		PreparedStatement prepstmt = connection
				.prepareStatement(fillTableString);
		System.out.println("EXECUTING: " + fillTableString);
		for (String entry : data) {
			prepstmt.setString(1, entry);
			prepstmt.executeUpdate();
		}
		if (prepstmt != null) {
			prepstmt.close();
		}
	}

	protected void fillTableHelper(String[] columnNames, SimpleTable sTable)
			throws SQLException {
		StringBuilder fillTableString = new StringBuilder("insert into "
				+ tableName + " (");
		for (int i = 0; i < columnNames.length; i++) {
			fillTableString.append(columnNames[i]);
			if (i < columnNames.length - 1) {
				fillTableString.append(", ");
			}
		}
		fillTableString.append(") values(");
		for (int i = 0; i < columnNames.length; i++) {
			fillTableString.append("?");
			if (i < columnNames.length - 1) {
				fillTableString.append(", ");
			}
		}
		fillTableString.append(")");

		System.out.println("EXECUTING: " + fillTableString);

		Iterator<Object[]> rowsIterator = sTable.getIterator();

		PreparedStatement prepstmt = connection
				.prepareStatement(fillTableString.toString());
		while (rowsIterator.hasNext()) {
			String[] row = (String[]) rowsIterator.next();
			for (int i = 0; i < row.length; i++) {
				String value = row[i];
				prepstmt.setString(i + 1, value);
			}
			prepstmt.executeUpdate();
		}
		if (prepstmt != null) {
			prepstmt.close();
		}
	}
	
	public void drop() throws SQLException {
		String dropTableString = "DROP TABLE "+ tableName;

		System.out.println("EXECUTING: " + dropTableString);
		Statement stmt = connection.createStatement();
		stmt.executeUpdate(dropTableString);
		if (stmt != null) {
			stmt.close();
		}		
	}
}
