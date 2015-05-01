package group.cooking;

import java.util.*;

public class Recipe {
    //stuff
	
	public String cookingMethod, cuisine, name, imageURL, URL;
	public ArrayList<String> ingredients;
	
	public Recipe(String name, String imageURL, String URL, ArrayList<String> ingredients, String cookingMethod, String cuisine) {
		this.name = name;
		this.imageURL = imageURL;
		this.URL = URL;
		this.ingredients = ingredients;
		this.cookingMethod = cookingMethod;
		this.cuisine = cuisine;
	}
	
	public String toString() {
		return this.name;
	}
}
