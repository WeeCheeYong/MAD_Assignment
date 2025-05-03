package com.example.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class flashCard extends AppCompatActivity {
    private Button addSet;
    private DBHandler dbHandler;
    private LinearLayout setContainer;
    private LayoutInflater inflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card);

        addSet = findViewById(R.id.addSet);
        dbHandler = new DBHandler(this);

        setContainer = findViewById(R.id.setContainer);
        inflater = LayoutInflater.from(this);


        addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(flashCard.this, addSet.class));
            }
        });


        loadData();
    }

    private void loadData() {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM sets", null);

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);

                // Inflate the set item layout
                View setView = inflater.inflate(R.layout.item_set, setContainer, false);

                TextView tvTitle = setView.findViewById(R.id.tvSetTitle);
                TextView tvDesc = setView.findViewById(R.id.tvSetDescription);
                ImageView btnEdit = setView.findViewById(R.id.btnEdit);
                ImageView btnAddCard = setView.findViewById(R.id.btnAddCard);
                ImageView btnStart = setView.findViewById(R.id.btnStart);

                tvTitle.setText(title);
                tvDesc.setText(description != null ? description : "");

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(flashCard.this, editSet.class);
                        intent.putExtra("setId", id);
                        intent.putExtra("setName", title);
                        intent.putExtra("setDesc", description);
                        startActivity(intent);
                    }
                });

                btnAddCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(flashCard.this, addCard.class);
                        intent.putExtra("setId", id);
                        intent.putExtra("setName", title);
                        intent.putExtra("setDesc", description);
                        startActivity(intent);
                    }
                });

                btnStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DBHandler dbHandler = new DBHandler(flashCard.this);
                        SQLiteDatabase db = dbHandler.getReadableDatabase();
                        Cursor cursor = db.rawQuery("SELECT * FROM cards WHERE setId = ?", new String[]{String.valueOf(id)});

                        if (cursor.getCount() > 0) {

                            Intent intent = new Intent(flashCard.this, gamePage.class);
                            intent.putExtra("setId", id);
                            startActivity(intent);
                        } else {
                            // No cards found, show Toast
                            Toast.makeText(flashCard.this, "No cards found. Please create cards first.", Toast.LENGTH_SHORT).show();
                        }

                        cursor.close();
                        db.close();
                    }
                });

                // Add the setView to the container
                setContainer.addView(setView);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }
    public void logout(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}