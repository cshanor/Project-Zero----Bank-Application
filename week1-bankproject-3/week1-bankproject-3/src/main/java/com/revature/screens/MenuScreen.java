package com.revature.screens;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.services.WeatherService;
import com.revature.utils.AppState;

public class MenuScreen implements Screen {

	WeatherService weather = new WeatherService();

	@Override
	public Screen start(Scanner scan) {

		System.out.println("    ____                  __                     ____              __  ");
		System.out.println("   / __ \\___ _   ______ _/ /___  __________     / __ )____ _____  / /__");
		System.out.println("  / /_/ / _ \\ | / / __ `/ __/ / / / ___/ _ \\   / __  / __ `/ __ \\/ //_/");
		System.out.println(" / _, _/  __/ |/ / /_/ / /_/ /_/ / /  /  __/  / /_/ / /_/ / / / / ,<   ");
		System.out.println("/_/ |_|\\___/|___/\\__,_/\\__/\\__,_/_/   \\___/  /_____/\\__,_/_/ /_/_/|_|  ");
		System.out.println();
		try {
			System.out.println(weather.getWeatherData());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println();
		System.out.println("Welcome!");
		System.out.println("Please make your selection:");
		System.out.println("-------------------------------------");
		System.out.println("1: Create a New Account");
		System.out.println("2: Login to Your Account");
		System.out.println("0: Exit the Bank");

		try {
			// Get the user's menu selection
			System.out.print("Selection: ");
			String userSelection = scan.nextLine();

			switch (userSelection) {
			case "1":
				// navigates to the Create Account screen;
				return new CreateAccountScreen().start(scan);
			case "2":
				// navigates to the Registration screen
				return new LoginScreen().start(scan);
			case "0":
				// exits the application
				System.out.println("Thank you for visiting Revature Bank");
				Thread.sleep(5000);
				AppState.setAppRunning(false);
				break;
			default:
				// Loop back to the beginning of the Home screen;
				System.out.println("[LOG] - Invalid selection!");
				return this.start(scan);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (InputMismatchException e) {

			// If any exceptions are thrown, restart the app
			System.out.println("[ERROR] - Error reading input from console");
			System.out.println("[LOG] - Restarting application...");
			AppState.setRestartingApp(true);
			AppState.setAppRunning(false);
			return null;
		}
		return null;
	}
}
