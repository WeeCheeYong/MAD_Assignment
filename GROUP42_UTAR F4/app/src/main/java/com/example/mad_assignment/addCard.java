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

public class addCard extends AppCompatActivity {
    private DBHandler dbHandler;
    private LinearLayout cardContainer;
    private LayoutInflater inflater;
    private Button addCard;
    private ImageView goback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        dbHandler = new DBHandler(this);
        cardContainer = findViewById(R.id.cardContainer);
        inflater = LayoutInflater.from(this);
        addCard = findViewById(R.id.btnaddCard);
        goback = findViewById(R.id.goback);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(addCard.this, flashCard.class));
            }
        });


        loadData();

        addCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getIntent().getStringExtra("setId");
                Intent intent = new Intent(addCard.this, AddCardET.class);
                intent.putExtra("setId", id);
                startActivity(intent);
            }
        });
    }

    private void loadData() {
        String setId = getIntent().getStringExtra("setId");
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cards WHERE setId = ?", new String[]{setId});

        if (cursor.moveToFirst()) {
            do {
                String id = cursor.getString(0);
                String set_Id = cursor.getString(1);
                String question = cursor.getString(2);
                String answer = cursor.getString(3);

                // Inflate the set item layout
                View setView = inflater.inflate(R.layout.card_layout, cardContainer, false);

                TextView tvQuestion = setView.findViewById(R.id.tvQuestion);
                TextView tvAnswer = setView.findViewById(R.id.tvAnswer);
                ImageView btnEdit = setView.findViewById(R.id.btnEdit);


                tvQuestion.setText(question);
                tvAnswer.setText(answer);

                btnEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(addCard.this, editCard.class);
                        intent.putExtra("cardId", id);
                        intent.putExtra("setId", set_Id);
                        intent.putExtra("question", question);
                        intent.putExtra("answer", answer);
                        startActivity(intent);
                    }
                });


                cardContainer.addView(setView);

            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
    }
}