package group.cooking;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class RecipesMenu extends ActionBarActivity {
    public String TAG = "RecipeMenu";
    public ArrayList<String> s_recipes;
    public ArrayList<Recipe> l_recipes;


    /*
    public void onItemClick(AdapterView<?> l, View v, int position, long id) {
        Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);
        // Then you start a new Activity via Intent
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
        intent.putExtra("position", position);
        intent.putExtra("id", id);
        startActivity(intent);
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes_menu);

        String s_ingred[] = getIntent().getExtras().getStringArray("ingredients");

        ArrayList<String> l_ingred = new ArrayList<String>(Arrays.asList(s_ingred));

        try {
            Log.d(TAG, "Entered Try");

            l_recipes = getRecipes(l_ingred);
            s_recipes = new ArrayList<String>();

            Log.d(TAG, "Got Recipes");

            ListView listView = (ListView) findViewById(R.id.listView);

            Log.d(TAG, "Found " + l_recipes.size() + " recipes");

            for(int i = 0; i < l_recipes.size(); i++)
            {
                Log.d(TAG, "Entered Loop");
                s_recipes.add(l_recipes.get(i).toString());
            }
            Log.d(TAG, "Passed Second Loop");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, s_recipes);
            Log.d(TAG, "Setting adapter");
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Log.d(TAG, "Reached item click");
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    Recipe rec = l_recipes.get(i);
                    intent.setData(Uri.parse(rec.URL));
                    startActivity(intent);

                }
            });


        } catch (Exception e) {
            Log.d(TAG, "Can't retrieve recipes");
        }




    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_recipes_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static ArrayList<Recipe> getRecipes(ArrayList<String> ingredients) throws Exception {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        String TAG = "RecipeMenu";
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        String params = "";
        for (int i = 0; i < ingredients.size(); i++) {
            if (i != 0)
                params += "%2C%20";
            params += ingredients.get(i);
        }

        Log.d(TAG, "Retrieving URL");
        URL url = new URL("http://api.pearson.com:80/kitchen-manager/v1/recipes?ingredients-all=" + params);
        Log.d(TAG, "Opening Connection");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        Log.d(TAG, "Opened Connection");
        conn.setRequestMethod("GET");
        Log.d(TAG, "Request Method Passed");

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        Log.d(TAG, "Buffered Reader Passed");

        StringBuffer responseJSON = new StringBuffer(in.readLine());

        JSONParser parser = new JSONParser();
        JSONObject JSONobj = (JSONObject) parser.parse(responseJSON.toString());
        JSONArray data = (JSONArray) JSONobj.get("results");
        Log.d(TAG, "JSON Passed");


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
        Log.d(TAG, "Passed for loop" +
                "");

        in.close();
        return recipes;
    }
}
