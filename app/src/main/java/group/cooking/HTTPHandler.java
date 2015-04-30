package group.cooking;

import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

import java.net.*;

import javax.net.ssl.HttpsURLConnection;

import java.util.ArrayList;

public class HTTPHandler {

	// the method you wanna call
	public static ArrayList<Recipe> getRecipes(ArrayList<String> ingredients) throws Exception {
		
		ArrayList<Recipe> recipes = new ArrayList<Recipe>();
		
		String params = "";
		for (int i = 0; i < ingredients.size(); i++) {
			if (i != 0)
				params += "%2C%20";
			params += ingredients.get(i);
		}
		
		URL url = new URL("http://api.pearson.com:80/kitchen-manager/v1/recipes?ingredients-all=" + params);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		conn.setRequestMethod("GET");
		
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

		StringBuffer responseJSON = new StringBuffer(in.readLine());
		
		JSONParser parser = new JSONParser();
		JSONObject JSONobj = (JSONObject) parser.parse(responseJSON.toString());
		JSONArray data = (JSONArray) JSONobj.get("results");
		
		
		for (int i = 0; i < data.size(); i++) {
			JSONObject recipe = (JSONObject) data.get(i);
			JSONArray JSONingrs = (JSONArray) recipe.get("ingredients");
			ArrayList<String> ingrs = new ArrayList<String>();
			
			for (int j = 0; j < JSONingrs.size(); j++)
				ingrs.add((String) JSONingrs.get(j));
			
			recipes.add(new Recipe(
				(String) recipe.get("name"),
				(String) recipe.get("image"),
				(String) recipe.get("url"),
				ingrs,
				(String) recipe.get("cooking_method"),
				(String) recipe.get("cuisine")
			));
		}
		
		in.close();
		return recipes;
	}
	
	public static void main(String[] args) throws Exception {
		
		/*
		
		ArrayList<String> input = new ArrayList<String>();
		input.add("cinnamon");
		input.add("apple");
		
		System.out.println(getRecipes(input));

		*/

	}
	
}
