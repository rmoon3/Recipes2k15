package group.cooking;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Arrays;


public class IngredientsMenu extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients_menu);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ingredients_menu, menu);
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

    private boolean isEmpty(EditText etText) {
        return etText.getText().toString().trim().length() == 0;
    }

    private EditText input;
    private int i =0;
    private TextView displayInput;
    private int count = 0;

    public void buttonOnClick(View v)
    {
        //Button button = (Button) v;
        input = (EditText) findViewById(R.id.editText);
        if(isEmpty(input)){}
        else
            count++;
        if(count > 0)
            display();
    }

    String[] rd = new String[100];
    String[] ingredients;
    public void display(){
        ingredients = new String[count];
        rd[i++] = input.getText().toString();
        for(int j = 0; j < ingredients.length; j++)
        {
            ingredients[j] = rd[j];
        }
        displayInput = (TextView) findViewById(R.id.savedIngred);
        displayInput.setText(Arrays.toString(ingredients));
        input.setText(null);
    }

    public void  clearButton(View v)
    {
        //Button button = (Button) v;
        new AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete all of your saved ingredients?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteArray(rd);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void deleteArray(String[] v){
        displayInput.setText("");
        String[] rd = new String[100];
        count = 0;
    }

}
