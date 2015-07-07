package parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class CSVReader {

	private Path csvFile;
	private String seperator;
	private int columnCount;

	public CSVReader(Path csvFile, String seperator) {
		this.csvFile = csvFile;
		this.seperator = seperator;
		columnCount();
		System.out.println("File \""+csvFile.toString()+"\" initialized.");
		System.out.println();
	}

	private void columnCount() {
		try (BufferedReader reader = Files.newBufferedReader(csvFile)) {
			String line = null;
			line = reader.readLine();
			System.out.println("1st Row of "+csvFile.toString()+":");
			System.out.println(line);
			System.out.println();
			String[] firstRow = line.split(seperator);
			columnCount = firstRow.length;
		} catch (IOException x) {
			System.err.format("IOException: %s%n", x);
		}
	}
	
		public int getColumnCount() {
		return columnCount;
	}

	public void fillTable(SimpleTable sTable) throws Exception {
		if (sTable.getColumnCount()!=this.columnCount) {
			throw new Exception("Table has different column count!");
		}
		try (BufferedReader reader = Files.newBufferedReader(csvFile)) {
		    String line = null;
		    while ((line = reader.readLine()) != null) {
		    	String[] row = line.split(seperator);
				sTable.addRow(row);
		    }
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		}
	}
	
	

}
