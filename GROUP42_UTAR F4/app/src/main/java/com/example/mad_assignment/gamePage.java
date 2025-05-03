package com.example.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class gamePage extends AppCompatActivity {
    private ImageView goback;
    private TextView tvQuestion;
    private ImageView good, bad, hint;
    private DBHandler dbHandler;
    private ArrayList<cards> cardList;
    private int currentIndex = 0;
    private int goodCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_page);

        goback = findViewById(R.id.imageView);
        tvQuestion = findViewById(R.id.tvQuestion);
        good = findViewById(R.id.good);
        bad = findViewById(R.id.bad);
        hint = findViewById(R.id.hint);

        dbHandler = new DBHandler(this);
        String setId = getIntent().getStringExtra("setId");
        cardList = new ArrayList<>();
        loadCards(setId);

        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(gamePage.this, flashCard.class));
            }
        });

        hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cardList.isEmpty() && currentIndex < cardList.size()) {
                    String answer = cardList.get(currentIndex).getAnswer();

                    new androidx.appcompat.app.AlertDialog.Builder(gamePage.this)
                            .setTitle("Hint")
                            .setMessage("Answer: " + answer)
                            .setPositiveButton("OK", null)
                            .show();
                }
            }
        });

        if (!cardList.isEmpty()) {
            tvQuestion.setText(cardList.get(0).getQuestion());
        }


        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion(true);
            }
        });


        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextQuestion(false);
            }
        });

    }
    private void loadCards(String setId) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM cards WHERE setId = ?", new String[]{setId});

        if(cursor.moveToFirst()) {
            do {
                int tempId = Integer.parseInt(setId);
                int id = Integer.parseInt(cursor.getString(0));
                String question = cursor.getString(2);
                String answer = cursor.getString(3);
                cardList.add(new cards(id, tempId, question, answer));
            }while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
    }

    private void nextQuestion(boolean isGood) {
        if(isGood) goodCount++;
        currentIndex++;

        if(currentIndex < cardList.size()) {
            tvQuestion.setText(cardList.get(currentIndex).getQuestion());
        }
        else {
            Intent intent = new Intent(gamePage.this, summaryPage.class);
            intent.putExtra("total", cardList.size());
            intent.putExtra("good", goodCount);
            startActivity(intent);
            finish();
        }
    }
}