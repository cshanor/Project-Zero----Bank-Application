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

	public double getWeatherData() throws Exception {
		
		
		// Create a new HttpResponse object and request data from OpenWeatherMap API
		HttpResponse<JsonNode> response = Unirest.get("http://api.openweathermap.org/data/2.5/weather?zip=33612,us&APPID=6a811cf92464a6f9b8c0b1cc1a213842").
				header("accept",  "application/json").
				asJson();
		
		// convert the json response from the API into a String.
		String json = response.getBody().getObject().toString();
		
		// Use the StringUtils library, and the substringBetween method is isolate the
		// temperature from the response string.
		String tempK = StringUtils.substringBetween(json, "\"temp\":",",\"temp_min\"");
		
		/**
		 * The temperature is provided in Kelvin, which needs to be converted to Fahrenheit
		 * using the formula F = (K * 1.8) - 459.67. 
		 */
		double tempKelvin = Double.parseDouble(tempK);
		double tempF = (tempKelvin * 1.8) - 459.67;
		temp = (int) tempF;
		return temp;
	}
	
	public static void main(String args[]) throws Exception {
		WeatherService weather = new WeatherService();
		weather.getWeatherData();
	}
	
}
