package com.skilldistillery.filmquery.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
//		app.test();
    app.launch();
	}

	private void test() {
		Film film = db.findFilmById(1);
//		System.out.println(film);
		Actor actor = db.findActorById(1);
//		System.out.println(actor);
		List<Actor> filmCast = db.findActorsByFilmId(1);
		System.out.println(filmCast);
	}

	private void launch() {
		Scanner input = new Scanner(System.in);

		startUserInterface(input);

		input.close();
	}

	private void startUserInterface(Scanner input) {
// TODO: Application logic: menus, user input
//		The user is presented with a menu in which they can choose to:
//
//			Look up a film by its id.
//			Look up a film by a search keyword.
//			Exit the application.
		boolean menu = true;
		while (menu) {
			displayMenu();
			int option = input.nextInt();
			switch (option) {
			case 1: 
				System.out.println("Enter film id: ");
				System.out.println();
				int filmId = input.nextInt();
				System.out.println(db.findFilmById(filmId));
				break;
			case 2:
				System.out.println("Enter keyword: ");
				System.out.println();
				String keyword = input.next();
				System.out.println(((DatabaseAccessorObject) db).findFilmByKeyword(keyword));
				break;
			case 3:
				menu = false;
				System.out.println("Exiting program.");
				System.out.println("Thanks for trying us out!");
				break;
			default: 
				System.err.println("Invalid input! Please enter a number between 1-3");
			}
		}
	}

	private void displayMenu() {
		System.out.println("------------------------");
		System.out.println("1. Search film by id.");
		System.out.println("2. Search film by keyword.");
		System.out.println("3. Exit.");
		System.out.println("Please select an option number.");
		System.out.println("------------------------");
		System.out.println("");
		
	}

}
