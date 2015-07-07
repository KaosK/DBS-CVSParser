package parser;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Set;

import parser.tables.ActorTable;
import parser.tables.DirectorTable;
import parser.tables.GenreTable;
import parser.tables.MovieActor;
import parser.tables.MovieDirector;
import parser.tables.MovieGenre;
import parser.tables.MovieTable;

public class Main {

	static Path csv_FilePath;
	static String[] CSV_HEADERS = { "imdbId", "mname", "pyear", "rating",
			"votings", "runtime", "director", "casting", "genre" };

	public static void main(String[] args) throws SQLException {
		if (args.length == 0) {
			System.out.println("Usage: CSVParser filename");
			System.exit(0);
		}
		csv_FilePath = Paths.get(args[0]);
		if (Files.notExists(csv_FilePath)) {
			System.out.println("Error: "+csv_FilePath.getFileName()+" does not exist.");
			System.exit(0);
		}
		System.out.println("Starting...");
		System.out.println();
		
		CSVReader reader = new CSVReader(csv_FilePath, "\t");
		SimpleTable alldata = new SimpleTable(CSV_HEADERS);

		// build a connection to a database
		Connection conn = ConnectionFactory.getConnection();

		// create table from csv file
		try {
			reader.fillTable(alldata);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println();

		// make movie table & fill it
		SimpleTable movie = alldata.extractColumns(1, 2, 3, 4, 5, 6);
		MovieTable movieTable = new MovieTable(conn);
		movieTable.createTable();
		movieTable.fillTable(movie);

		//create&fill relations-tables:
		SimpleTable movieDirectorData = alldata.extractColumns(1, 7).unnest(
				"|", 2);
		MovieDirector movieDirectorTable = new MovieDirector(conn);
		movieDirectorTable.createTable();
		movieDirectorTable.fillTable(movieDirectorData);

		SimpleTable movieActorData = alldata.extractColumns(1, 8)
				.unnest("|", 2);
		MovieActor movieActorTable = new MovieActor(conn);
		movieActorTable.createTable();
		movieActorTable.fillTable(movieActorData);

		SimpleTable movieGenreData = alldata.extractColumns(1, 9)
				.unnest("|", 2);
		MovieGenre movieGenreTable = new MovieGenre(conn);
		movieGenreTable.createTable();
		movieGenreTable.fillTable(movieGenreData);

		// get the set of directors, actors and genres
		Set<String> directors = alldata.returnSetFromColumn(7, "|");
		Set<String> actors = alldata.returnSetFromColumn(8, "|");
		Set<String> genres = alldata.returnSetFromColumn(9, "|");

		// create the directors, actors and genres tables
		DirectorTable directorTable = new DirectorTable(conn);
		directorTable.createTable();
		ActorTable actorTable = new ActorTable(conn);
		actorTable.createTable();
		GenreTable genreTable = new GenreTable(conn);
		genreTable.createTable();

		// fill the directors, actors and genres tables
		directorTable.fillTable(directors);
		actorTable.fillTable(actors);
		genreTable.fillTable(genres);

		// SQL Operations
		SimpleSQLCommand command = new SimpleSQLCommand(conn);
		command.createTableAs("directs", "imdbId, directorId", "director",
				"moviedirector", "dname");
		command.createTableAs("acts_in", "imdbId, actorId", "actor",
				"movieactor", "aname");
		command.createTableAs("genre_of", "imdbId, genreId", "genre",
				"moviegenre", "gname");

		// drop unused Tables
		movieDirectorTable.drop();
		movieActorTable.drop();
		movieGenreTable.drop();
		
		System.out.println("...Done!");
		System.out.println();
	}
}
