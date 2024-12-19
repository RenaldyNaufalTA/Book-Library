package com.vavoryn.booklibraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input;
    Button add_button, back_button;
//    FloatingActionButton back_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);

        title_input = findViewById(R.id.title_input);
        author_input = findViewById(R.id.author_input);
        pages_input = findViewById(R.id.pages_input);
        add_button = findViewById(R.id.add_button);
        back_button = findViewById(R.id.back_button);


        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation())
                {
                    MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                    myDB.addBook(
                            title_input.getText().toString().trim(),
                            author_input.getText().toString().trim(),
                            Integer.valueOf(pages_input.getText().toString().trim()));
                    Toast.makeText(AddActivity.this, "Book Added Successfully", Toast.LENGTH_SHORT).show();
                }

            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean validation() {
        if (title_input.length() <= 0) {
            Toast.makeText(AddActivity.this, getString(R.string.error_title_required), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (title_input.length() >= 15) {
            Toast.makeText(AddActivity.this, getString(R.string.error_title_length), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (author_input.length() <= 0) {
            Toast.makeText(AddActivity.this, getString(R.string.error_author_required), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pages_input.length() <= 0) {
            Toast.makeText(AddActivity.this, getString(R.string.error_pages_required), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

}