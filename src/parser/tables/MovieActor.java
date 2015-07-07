package parser.tables;

import java.sql.Connection;
import java.sql.SQLException;

import parser.SimpleTable;

public class MovieActor extends AbstractTable {

	private String[] columnNames = { "imdbId", "aname"};

	public MovieActor(Connection connection) {
		super("movieActor", connection);
	}

	@Override
	public void createTable() throws SQLException {
		String columnA = columnNames[0]+" varchar(16)";
		String columnB = columnNames[1]+" text";
		createTableHelper(columnA, columnB);
	}

	public void fillTable(SimpleTable sTable) throws SQLException {
		fillTableHelper(columnNames, sTable);
	}
}
