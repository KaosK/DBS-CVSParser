package parser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * A simple Table Object for better handling of Query ResultSets.
 *
 */
public class SimpleTable {

	private String[] columnNames;
	private int[] columnWidth;
	private int columnCount;
	private ArrayList<Object[]> data;

	/**
	 * Constructor method for SimpleTable Creates an empty table.
	 * 
	 * @param columnNames
	 *            Takes multiple Strings as Header Input.
	 * @throws SQLException
	 */
	public SimpleTable(String... columnNames) {
		this.columnCount = columnNames.length;
		this.columnNames = columnNames;
		columnWidth = new int[columnCount];
		for (int i = 0; i < columnCount; i++) {
			columnWidth[i] = Math.max(columnNames[i].length(), columnWidth[i]);
		}
		data = new ArrayList<Object[]>();
	}

	public void addRow(Object[] row) {
		data.add(row);
		// might eat performance:
		for (int i = 0; i < row.length; i++) {
			columnWidth[i] = Math.max(((String) row[i]).length(),
					columnWidth[i]);
		}
	}

	public int getColumnCount() {
		return columnCount;
	}

	public Set<String> returnSetFromColumn(int columnNumber, String Seperator) {
		Set<String> columnSet = new TreeSet<>();
		for (Object[] row : data) {
			String[] entries = ((String) row[columnNumber - 1]).split(Pattern
					.quote(Seperator));
			for (String entry : entries) {
				columnSet.add(entry);
			}
		}
		return columnSet;
	}

	/**
	 * This method outputs the SimpleTable to the console.
	 * 
	 */
	public void printToConsole() {
		// Header:
		for (int i = 0; i < columnNames.length; i++) {
			int width = columnWidth[i] + 4;
			System.out.printf("%-" + width + "." + (width - 2) + "s",
					columnNames[i]);
		}
		System.out.println();
		// dividing line
		for (int i = 0; i < columnNames.length; i++) {
			int width = columnWidth[i] + 2;
			String repeated = new String(new char[width]).replace("\0", "-");
			System.out.print(repeated + "  ");
		}
		System.out.println();
		// Data:
		for (Object[] row : data) {
			for (int i = 0; i < row.length; i++) {
				int width = columnWidth[i] + 4;
				System.out.printf("%-" + width + "." + (width - 2) + "s",
						row[i]);
			}
			System.out.println();
		}
	}

	public SimpleTable unnest(String Seperator, int column) {
		SimpleTable newTable = new SimpleTable(columnNames);
		for (Object[] row : data) {
			String[] entries = ((String) row[column - 1]).split(Pattern
					.quote(Seperator));
			for (String entry : entries) {
				String[] newRow = (String[]) Arrays.copyOf(row, row.length);
				newRow[column - 1] = entry;
				newTable.addRow(newRow);
			}
		}
		return newTable;
	}

	public SimpleTable extractColumns(int... columns) {
		String[] newColumnNames = new String[columns.length];
		for (int i = 0; i < newColumnNames.length; i++) {
			newColumnNames[i] = columnNames[columns[i] - 1];
		}
		SimpleTable newTable = new SimpleTable(newColumnNames);
		for (Object[] row : data) {
			String[] newRow = new String[columns.length];
			for (int i = 0; i < newRow.length; i++) {
				newRow[i] = (String) row[columns[i] - 1];
			}
			newTable.addRow(newRow);
		}
		return newTable;
	}
	
	public Iterator<Object[]> getIterator() {
		return data.iterator();
	}

}
