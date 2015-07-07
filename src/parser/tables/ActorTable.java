package parser.tables;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

public class ActorTable extends AbstractTable {

	public ActorTable(Connection connection) {
		super("actor", connection);
	}

	@Override
	public void createTable() throws SQLException {
		String columnA = "actorID serial NOT NULL";
		String columnB = "aname text NOT NULL";
		String columnC = "PRIMARY KEY (actorID)";
		createTableHelper(columnA, columnB, columnC);
	}
	
	public void fillTable(Set<String> data) throws SQLException {
		String columnName = "aname";
			fillTableHelper(columnName, data);	
	}

}
