package com.vavoryn.booklibraryapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UpdateActivity extends AppCompatActivity {

    EditText title_input, author_input, pages_input;
    TextView title_page2;
    Button update_button, back_button;
    FloatingActionButton delete_button;
    String id, title, author, pages;
    Intent backIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        title_page2 = findViewById(R.id.title_page2);
        title_input = findViewById(R.id.title_input2);
        author_input = findViewById(R.id.author_input2);
        pages_input = findViewById(R.id.pages_input2);
        update_button = findViewById(R.id.update_button);
        back_button = findViewById(R.id.back_button);
        delete_button = findViewById(R.id.delete_button);

        backIntent = new Intent(UpdateActivity.this, MainActivity.class);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(backIntent);
            }
        });

//        First call this
        getAndSetIntentData();
        title_page2.setText(title_input.getText().toString().trim());

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validation()){
                    MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                    myDB.updateData(
                            id,
                            title_input.getText().toString().trim(),
                            author_input.getText().toString().trim(),
                            pages_input.getText().toString().trim());
                    Toast.makeText(UpdateActivity.this, "Updated Successfully kon", Toast.LENGTH_SHORT).show();
                }

            }
        });

        delete_button.setOnClickListener(v ->
        {
            confirmDelete();
        });
    }
    void getAndSetIntentData(){
        if(getIntent().hasExtra("id") && getIntent().hasExtra("title") &&
        getIntent().hasExtra("author") && getIntent().hasExtra("pages")){
//            Getting data from intent
            id = getIntent().getStringExtra("id");
            title = getIntent().getStringExtra("title");
            author = getIntent().getStringExtra("author");
            pages = getIntent().getStringExtra("pages");

//            Getting data intent
            title_input.setText(title);
            author_input.setText(author);
            pages_input.setText(pages);

        } else {
            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }
    
    private boolean validation(){
        if (title_input.length() <= 0) {
            Toast.makeText(UpdateActivity.this, getString(R.string.error_title_required), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (title_input.length() >= 15) {
            Toast.makeText(UpdateActivity.this, getString(R.string.error_title_length), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (author_input.length() <= 0) {
            Toast.makeText(UpdateActivity.this, getString(R.string.error_author_required), Toast.LENGTH_SHORT).show();
            return false;
        }
        if (pages_input.length() <= 0) {
            Toast.makeText(UpdateActivity.this, getString(R.string.error_pages_required), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void confirmDelete(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Confirmation");
        builder.setMessage("Are you sure you want to delete "+ title + "?");

        // Set positive button
        builder.setPositiveButton("Yes", (dialog, which) -> {
            // Action when OK is clicked
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            myDB.deleteOneRow(id);
            Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show();
//            For back to MainActivity
//            finish();
            startActivity(backIntent);
        });

        // Set negative button
        builder.setNegativeButton("No", (dialog, which) -> {
            // Action when Cancel is clicked
            Toast.makeText(this, "Deletion canceled", Toast.LENGTH_SHORT).show();
        });

        // Display the AlertDialog
        builder.show();
    }
}