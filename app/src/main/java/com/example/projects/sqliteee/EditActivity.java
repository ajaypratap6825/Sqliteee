package com.example.projects.sqliteee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class EditActivity extends AppCompatActivity {
    Toolbar toolbar;
    EditText noteTitle,noteDetails;
    long nId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        toolbar = findViewById(R.id.toolbar);
        toolbar.showOverflowMenu();
        toolbar.setTitle("Edit Note");
        toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        });

        Intent i = getIntent();
        nId = i.getLongExtra("ID",0);
        DatabaseHelper helper = new DatabaseHelper(this);
        Data d = helper.getData(nId);

        noteDetails = findViewById(R.id.noteDetails);
        noteTitle = findViewById(R.id.noteTitle);

        noteTitle.setText(d.getTitle());
        noteDetails.setText(d.getDescription());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.save){
            Data note = new Data(nId,noteTitle.getText().toString(),noteDetails.getText().toString());
            Log.d("EDITED", "edited: before saving id -> " + note.getId());
            DatabaseHelper sDB = new DatabaseHelper(getApplicationContext());
            long id = sDB.editNote(note);
            Log.d("EDITED", "EDIT: id " + id);
            goToMain();
            Toast.makeText(this, "Note Edited.", Toast.LENGTH_SHORT).show();
        }else if(item.getItemId() == R.id.cancel){
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void goToMain() {
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
        finish();
    }
}