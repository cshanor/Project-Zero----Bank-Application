package com.revature;

import java.util.InputMismatchException;
import java.util.Scanner;
import com.revature.screens.MenuScreen;
import com.revature.screens.Screen;
import com.revature.utils.AppState;

public class BankDriver {

	public static void main(String[] args) {
		runApp();
	}
	
	public static void runApp() {
		
		System.out.println("[LOG] - Initializing system...");

		// Change the application state to running
		AppState.setAppRunning(true);
		AppState.setRestartingApp(false);
		
		Screen screen = new MenuScreen();
		Scanner scan = new Scanner(System.in);
		
		while(AppState.isAppRunning()) {
			screen.start(scan);
		}
		
		try {
			scan.close();
		} catch (InputMismatchException ime) {
			ime.printStackTrace();
		}
	}
}
