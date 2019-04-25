package com.revature.utils;

import com.revature.models.Customer;

public class AppState {

	private static Customer currentUser;
	private static boolean appRunning;
	private static boolean restartingApp;
	
	private AppState() {
		super();
	}

	public static Customer getCurrentUser() {
		return currentUser;
	}

	public static void setCurrentUser(Customer currentUser) {
		AppState.currentUser = currentUser;
	}

	public static boolean isAppRunning() {
		return appRunning;
	}

	public static void setAppRunning(boolean appRunning) {
		AppState.appRunning = appRunning;
	}

	public static boolean isRestartingApp() {
		return restartingApp;
	}

	public static void setRestartingApp(boolean restartingApp) {
		AppState.restartingApp = restartingApp;
	}
}
