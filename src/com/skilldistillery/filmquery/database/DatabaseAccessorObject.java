package com.skilldistillery.filmquery.database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;
import com.skilldistillery.filmquery.entities.Language;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false";
	private String user = "student";
	private String pass = "student";

	public DatabaseAccessorObject() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Error loading database driver:");
			System.err.println(e);
			e.printStackTrace();
		}
	}

	@Override
	public Film findFilmById(int filmId) {
		// TODO use JDBC code to retrieve film, create Film object
		Film film = null;

		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT film.id, film.title, film.description, film.release_year,"
					+ " film.language_id, film.rental_duration, film.rental_rate, film.length,"
					+ " film.replacement_cost, film.rating, film.special_features, language.name"
					+ " FROM film JOIN language ON language.id = film.language_id WHERE film.id = ?";

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet filmResult = stmt.executeQuery();
			if (filmResult.next()) {
				film = new Film();
				Language language = new Language();
				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getInt("release_year"));
				film.setLanguageId(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				language.setName(filmResult.getString("language.name"));
				film.setLanguage(language);
				DatabaseAccessorObject db = new DatabaseAccessorObject();
				List<Actor> cast = db.findActorsByFilmId(filmId);
				film.setFilmCast(cast);      
			}
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Database error:");
			System.err.println("Id not found.");
			System.err.println(e);
		}
		return film;
	}
	@Override
	public List<Film> findFilmByKeyword(String keyword) {
		 List<Film> filmList = new ArrayList<Film>();
		String searchPhrase = keyword;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);

			String sql = "SELECT film.id, film.title, film.description, film.release_year,"
					+ " film.language_id, film.rental_duration, film.rental_rate, film.length,"
					+ " film.replacement_cost, film.rating, film.special_features, language.name "
					+ " FROM film JOIN language ON language.id = film.language_id"
					+ " WHERE film.title LIKE ? OR film.description LIKE ?;";
	

			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%" + searchPhrase + "%");
			stmt.setString(2, "%" + searchPhrase + "%");
			ResultSet filmResult = stmt.executeQuery();
		
			while (filmResult.next()) {
				Film film = new Film();
				Language language = new Language();
				film.setId(filmResult.getInt("id"));
				film.setTitle(filmResult.getString("title"));
				film.setDescription(filmResult.getString("description"));
				film.setReleaseYear(filmResult.getInt("release_year"));
				film.setLanguageId(filmResult.getInt("language_id"));
				film.setRentalDuration(filmResult.getInt("rental_duration"));
				film.setRentalRate(filmResult.getDouble("rental_rate"));
				film.setLength(filmResult.getInt("length"));
				film.setReplacementCost(filmResult.getDouble("replacement_cost"));
				film.setRating(filmResult.getString("rating"));
				film.setSpecialFeatures(filmResult.getString("special_features"));
				language.setName(filmResult.getString("language.name"));
				film.setLanguage(language);
				film.setFilmCast(findActorsByFilmId(filmResult.getInt("id")));
				filmList.add(film);
			}
			filmResult.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			System.err.println("Database error:");
			System.err.println(e);
		}
		return filmList;
	
	}

	@Override
	public Actor findActorById(int actorId) {
		Actor actor = null;
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT id, first_name, last_name FROM actor WHERE id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, actorId);
			ResultSet actorResult = stmt.executeQuery();
			if (actorResult.next()) {
				actor = new Actor(); // Create the object
				// Here is our mapping of query columns to our object fields:
				actor.setActorId(actorResult.getInt("id"));
				actor.setFirstName(actorResult.getString("first_name"));
				actor.setLastName(actorResult.getString("last_name"));
			}
			actorResult.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {

		}
		return actor;
	}

	@Override
	public List<Actor> findActorsByFilmId(int filmId) {
		List<Actor> filmCast = new ArrayList<>();
		try {
			Connection conn = DriverManager.getConnection(URL, user, pass);
			String sql = "SELECT actor.id, actor.first_name, actor.last_name"
					+ " FROM actor JOIN film_actor ON actor.id = film_actor.actor_id"
					+ " JOIN film ON film.id = film_actor.film_id WHERE film.Id = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setInt(1, filmId);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				int actorId = rs.getInt("actor.id");
				String firstName = rs.getString("actor.first_name");
				String lastName = rs.getString("actor.last_name");
				Actor actor = new Actor(actorId, firstName, lastName);
				filmCast.add(actor);
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (Exception e) {

		}
		return filmCast;
	}
	
	// Need to return explanation if film search is Null
	// Need to display language
	// Need to fix keyword search
}
