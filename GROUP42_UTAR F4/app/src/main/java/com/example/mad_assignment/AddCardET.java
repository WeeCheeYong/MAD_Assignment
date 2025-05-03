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

public class AddCardET extends AppCompatActivity {
    private ImageView goBack;
    private EditText question, answer;
    private DBHandler dbHandler;
    private Button btnAdd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card_et);

        goBack = findViewById(R.id.goback1);
        question = findViewById(R.id.etQuestion);
        answer = findViewById(R.id.etAnswer);

        dbHandler = new DBHandler(this);
        btnAdd = findViewById(R.id.btnAdd);

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String questionText = question.getText().toString();
                String answerText = answer.getText().toString();
                String id = getIntent().getStringExtra("setId");

                if(questionText.isEmpty() || answerText.isEmpty()) {
                    Toast.makeText(AddCardET.this, "Please fill in the name", Toast.LENGTH_SHORT).show();
                }
                else {
                    ContentValues values = new ContentValues();
                    values.put("setId", id);
                    values.put("question", questionText);
                    values.put("answer", answerText);


                    SQLiteDatabase db = dbHandler.getWritableDatabase();
                    long result = db.insert("cards", null, values);
                    db.close();

                    if (result != -1) {
                        Toast.makeText(AddCardET.this, "Set added successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AddCardET.this, "Failed to add card", Toast.LENGTH_SHORT).show();
                    }

                    Intent intent = new Intent(AddCardET.this, addCard.class);
                    intent.putExtra("setId", id);
                    startActivity(intent);
                }
            }
        });
    }
}