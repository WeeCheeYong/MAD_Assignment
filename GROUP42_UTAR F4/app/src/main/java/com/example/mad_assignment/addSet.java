package com.example.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class addSet extends AppCompatActivity {
    private EditText name, description;
    private Button addSet;
    private DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_set);

        name = findViewById(R.id.etTitle);
        description = findViewById(R.id.etDescription);
        addSet = findViewById(R.id.btnaddSet);
        dbHandler = new DBHandler(this);



        addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSet();
            }
        });
    }

    private void addSet() {
        String nameText = name.getText().toString();
        String descriptionText = description.getText().toString();
        if(descriptionText.isEmpty()) {
            descriptionText = null;
        }

        if(nameText.isEmpty()) {
            Toast.makeText(this, "Please fill in the name", Toast.LENGTH_SHORT).show();
        }
        else {
            ContentValues values = new ContentValues();
            values.put("title", nameText);
            values.put("description", descriptionText);
            values.put("last_result", (String) null);

            SQLiteDatabase db = dbHandler.getWritableDatabase();
            long result = db.insert("sets", null, values);
            db.close();

            if (result != -1) {
                Toast.makeText(this, "Set added successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Failed to add set", Toast.LENGTH_SHORT).show();
            }

            startActivity(new Intent(com.example.mad_assignment.addSet.this, flashCard.class));
        }
    }
}