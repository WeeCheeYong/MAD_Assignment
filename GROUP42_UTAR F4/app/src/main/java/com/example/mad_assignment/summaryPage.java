package com.example.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class summaryPage extends AppCompatActivity {
    private Button done;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_page);

        done = findViewById(R.id.done);
        textView = findViewById(R.id.textView4);

        int total = getIntent().getIntExtra("total", 0);
        int good = getIntent().getIntExtra("good", 0);

        textView.setText(good + " / " + total);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(summaryPage.this, flashCard.class));
            }
        });
    }
}