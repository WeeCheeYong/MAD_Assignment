package com.example.mad_assignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class editSet extends AppCompatActivity {
    private EditText name, description;
    private Button addSet, btnDelete;
    private DBHandler dbHandler;
    private ImageView goBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_set);

        name = findViewById(R.id.etTitle);
        description = findViewById(R.id.etDescription);
        addSet = findViewById(R.id.btnaddSet);
        btnDelete = findViewById(R.id.btDelete);
        goBack = findViewById(R.id.goBack);
        dbHandler = new DBHandler(this);

        loadIntent();

        addSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String updateName = name.getText().toString();
                String updateDesc = description.getText().toString();
                String id = getIntent().getStringExtra("setId");;
                if(updateDesc.isEmpty()) {
                    updateDesc = null;
                }
                if(!updateName.isEmpty()) {
                    dbHandler.updateSet(id, updateName, updateDesc);
                    finish();
                    startActivity(new Intent(editSet.this, flashCard.class));
                }
                else {
                    Toast.makeText(editSet.this, "Please fill in the name", Toast.LENGTH_SHORT).show();
                }

            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = getIntent().getStringExtra("setId");
                dbHandler.deleteSet(id);
                finish();
                startActivity(new Intent(editSet.this, flashCard.class));
                Toast.makeText(editSet.this, "Set deleted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadIntent() {
        String idt = getIntent().getStringExtra("setId");
        String namet = getIntent().getStringExtra("setName");
        String descriptiont = getIntent().getStringExtra("setDesc");

        name.setText(namet);
        if(descriptiont == null) {
            description.setText("");
        }
        else {
            description.setText(descriptiont);
        }
    }
}