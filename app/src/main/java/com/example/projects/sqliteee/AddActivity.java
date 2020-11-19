package com.example.projects.sqliteee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

public class AddActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle,noteDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        toolbar = findViewById(R.id.toolbar);
        toolbar.showOverflowMenu();
        toolbar.setTitle("New Note");
        toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);

        noteDetails = findViewById(R.id.noteDetails);
        noteTitle = findViewById(R.id.noteTitle);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.save:
                if(noteTitle.getText().length() != 0){
                    Data data = new Data(noteTitle.getText().toString(),noteDetails.getText().toString());
                    DatabaseHelper helper = new DatabaseHelper(this);
                    long ide = helper.addNote(data);
                    Data d = helper.getData(ide);
                    Log.d("inserted", "Note: "+ id + " -> Title:" + d.getTitle()+" Date: ");
                    onBackPressed();
                }else{
                    noteTitle.setError("Title Can not be Blank.");
                }
                break;
            case R.id.delete:
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}