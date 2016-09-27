package com.example.sanoop.autocomplete.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.sanoop.autocomplete.Common.CustomAutoCompleteView;
import com.example.sanoop.autocomplete.Database.DatabaseHandler;
import com.example.sanoop.autocomplete.Database.TextName;
import com.example.sanoop.autocomplete.Listeners.CustomTextChangedListener;
import com.example.sanoop.autocomplete.R;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    DatabaseHandler db;
    public CustomAutoCompleteView autoComplete;
    public ArrayAdapter<String> adapter;
    public String[] item = new String[] {"Searching..."};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try{

            db = new DatabaseHandler(MainActivity.this);
            autoComplete = (CustomAutoCompleteView) findViewById(R.id.autocomplete);
            autoComplete.addTextChangedListener(new CustomTextChangedListener(this));

            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, item);
            autoComplete.setAdapter(adapter);

        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void saveData(View view){
        try{

            String text = autoComplete.getText().toString();
            if (text.equals("")){
                Toast.makeText(this, "Enter a string ", Toast.LENGTH_LONG).show();
            }else{
                db.create(new TextName(text));
                autoComplete.setText("");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String[] getItemsFromDb(String searchTerm){
        List<TextName> texts = db.read(searchTerm);
        int rowCount = texts.size();

        String[] item = new String[rowCount];
        int x = 0;

        for (TextName text : texts) {

            item[x] = text.objectName;
            x++;
        }
        return item;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
