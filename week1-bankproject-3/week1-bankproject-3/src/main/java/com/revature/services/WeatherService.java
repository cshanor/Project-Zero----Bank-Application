package com.revature.services;

import org.apache.commons.lang3.StringUtils;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

public class WeatherService {

	private static int temp;

	static {
		temp = 0;
	}

	/**
	 * @return Returns two Strings, one containing a description of weather conditions
	 * 			and the other containing the temperature.
	 * @throws Exception
     * @author Christopher Shanor (cshanor@zoho.com)
	 */
	public String getWeatherData() throws Exception {

		// Create a new HttpResponse object and request data from OpenWeatherMap API
		// Unirest library streamlines the Http/API request process by abstracting much of the process.
		HttpResponse<JsonNode> response = Unirest.get("http://api.openweathermap.org/data/2.5/weather?zip=33612,us&APPID=6a811cf92464a6f9b8c0b1cc1a213842").
				header("accept",  "application/json").
				asJson();

		// convert the json response from the API into a String.
		String json = response.getBody().getObject().toString();

		// Use the StringUtils library, and the substringBetween() method is isolate the
		// temperature from the response string.
		String tempK = StringUtils.substringBetween(json, "\"temp\":",",\"temp_min\"");
		String desc = StringUtils.substringBetween(json, "\"description\":",",\"main\"");

		/*
		 * The temperature is provided in Kelvin, which needs to be converted to Fahrenheit
		 * using the formula F = (K * 1.8) - 459.67.
		 */
		double tempKelvin = Double.parseDouble(tempK);
		double tempF = (tempKelvin * 1.8) - 459.67;

		// Down casting the temperature from double to int, to trim the trivial decimal points off the temperature.
		temp = (int) tempF;

		// This is the string that will display on the bank landing screen.
		String weather = "Current weather in Tampa: " + desc + " with a temperature of " + temp + "\u2109.";
		return weather;
	}
}