package com.vavoryn.booklibraryapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton add_button, refresh_button;
    MyDatabaseHelper myDB;
    ArrayList<String> book_id, book_title, book_author, book_pages;
    CustomAdapter customAdapter;
    ImageView empty_image;
    TextView no_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        refresh_button = findViewById(R.id.refresh_button);
        empty_image = findViewById(R.id.empty_image);
        no_data = findViewById(R.id.no_data);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });


        myDB = new MyDatabaseHelper(MainActivity.this);
        book_id = new ArrayList<>();
        book_title = new ArrayList<>();
        book_author = new ArrayList<>();
        book_pages = new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter( this, book_id, book_title, book_author,
                book_pages);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        refresh_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshData();
            }
        });
    }

    void storeDataInArrays(){
        // Clear existing data in the arrays before adding new data
        book_id.clear();
        book_title.clear();
        book_author.clear();
        book_pages.clear();

        Cursor cursor = myDB.readAllData(); // Fetch data from the database

        // Check if the cursor contains any data
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                // Add data from cursor to the respective arrays
                book_id.add(cursor.getString(0));     // Assuming column 0 is ID
                book_title.add(cursor.getString(1));  // Assuming column 1 is Title
                book_author.add(cursor.getString(2)); // Assuming column 2 is Author
                book_pages.add(cursor.getString(3));  // Assuming column 3 is Pages
            }
            empty_image.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
            cursor.close(); // Close the cursor to free up resources
        } else {
            // Show a Image if data not found
            empty_image.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
        }
    }

    void refreshData(){
        storeDataInArrays();
        customAdapter.notifyDataSetChanged();
        Toast.makeText(MainActivity.this, "Data refreshed!", Toast.LENGTH_SHORT).show();
    }
}