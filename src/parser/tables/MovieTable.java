package parser.tables;

import java.sql.Connection;
import java.sql.SQLException;

import parser.SimpleTable;

public class MovieTable extends AbstractTable {

	private String[] columnNames = { "imdbId", "mname", "pyear", "rating",
			"votings", "runtime" };

	public MovieTable(Connection connection) {
		super("movie", connection);
	}

	@Override
	public void createTable() throws SQLException {
		String columnA = columnNames[0]+" varchar(16)";
		String columnB = columnNames[1]+" varchar(256)";
		String columnC = columnNames[2]+" varchar(16)";
		String columnD = columnNames[3]+" varchar(16)";
		String columnE = columnNames[4]+" varchar(16)";
		String columnF = columnNames[5]+" varchar(16)";
		String columnG = "PRIMARY KEY (imdbId)";
		createTableHelper(columnA, columnB, columnC, columnD, columnE, columnF,
				columnG);
	}

	public void fillTable(SimpleTable sTable) throws SQLException {
		fillTableHelper(columnNames, sTable);
	}
}
