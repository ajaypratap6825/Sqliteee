package com.example.projects.sqliteee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    DatabaseHelper helper;
    DataAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolbar);
        toolbar.showOverflowMenu();
        toolbar.setTitle( "Notes App");
        toolbar.setBackgroundColor(Color.parseColor("#ffffff"));
        setSupportActionBar(toolbar);


        helper = new DatabaseHelper(this);
        List<Data> allNotes = helper.getAllNotes();
        recyclerView = findViewById(R.id.rv);

        displayList(allNotes);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Add New Note", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this,AddActivity.class);
                startActivity(i);
            }
        });

    }

    private void displayList(List<Data> allNotes) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DataAdapter(this,allNotes);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<Data> getAllNotes = helper.getAllNotes();
            displayList(getAllNotes);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}