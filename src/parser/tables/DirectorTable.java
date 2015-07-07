package parser.tables;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class DirectorTable extends AbstractTable {

	public DirectorTable(Connection connection) {
		super("director", connection);
	}

	@Override
	public void createTable() throws SQLException {
		String columnA = "directorID serial NOT NULL";
		String columnB = "dname text NOT NULL";
		String columnC = "PRIMARY KEY (directorID)";
		createTableHelper(columnA, columnB, columnC);
	}
	
	public void fillTable(Set<String> data) throws SQLException {
		String columnName = "dname";
			fillTableHelper(columnName, data);	
	}

}
