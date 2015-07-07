package parser.tables;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class GenreTable extends AbstractTable {

	public GenreTable(Connection connection) {
		super("genre", connection);
	}

	@Override
	public void createTable() throws SQLException {
		String columnA = "genreID serial NOT NULL";
		String columnB = "gname text NOT NULL";
		String columnC = "PRIMARY KEY (genreID)";
		createTableHelper(columnA, columnB, columnC);
	}
	
	public void fillTable(Set<String> data) throws SQLException {
		String columnName = "gname";
			fillTableHelper(columnName, data);	
	}

}
