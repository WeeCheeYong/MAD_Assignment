package com.example.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class editCard extends AppCompatActivity {
    private DBHandler dbHandler;
    private ImageView goBack;
    private EditText question, answer;
    private Button btnAdd, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);

        dbHandler = new DBHandler(this);
        goBack = findViewById(R.id.goback);
        question = findViewById(R.id.etQuestion);
        answer = findViewById(R.id.etAnswer);
        btnAdd = findViewById(R.id.btnAdd);
        btnDelete = findViewById(R.id.btDelete);
        String id = getIntent().getStringExtra("cardId");
        String setId = getIntent().getStringExtra("setId");
        String cquestion = getIntent().getStringExtra("question");
        String canswer = getIntent().getStringExtra("answer");

        question.setText(cquestion);
        answer.setText(canswer);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(editCard.this, addCard.class);
                intent.putExtra("setId", setId);
                startActivity(intent);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String question1 = question.getText().toString();
                String answer1 = answer.getText().toString();
                if(question1.isEmpty() || answer1.isEmpty()) {
                    Toast.makeText(editCard.this, "Both fields are required", Toast.LENGTH_SHORT).show();
                }
                else {
                    SQLiteDatabase db = dbHandler.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("setId", setId);
                    values.put("question", question1);
                    values.put("answer", answer1);
                    db.update("cards", values, "id = ?", new String[]{id});
                    db.close();
                    Intent intent = new Intent(editCard.this, addCard.class);
                    intent.putExtra("setId", setId);
                    startActivity(intent);
                }

            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = dbHandler.getWritableDatabase();
                db.delete("cards", "id = ?", new String[]{id});
                db.close();
                Intent intent = new Intent(editCard.this, addCard.class);
                intent.putExtra("setId", setId);
                startActivity(intent);
            }
        });

    }



}