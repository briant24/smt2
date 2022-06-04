package com.briant.smt2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView admin;
    private Button btnlogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        admin = findViewById(R.id.tvIntent);
        btnlogout = findViewById(R.id.button);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        admin = findViewById(R.id.tvIntent);
        Intent ambildata=getIntent();
        String strnama;
        strnama = ambildata.getStringExtra("nama");
        admin.setText("Selamat datang, " + strnama);
    }

    public void gopesanan(View view) {
        Intent intent = new Intent(getApplicationContext(), BookingActivity.class);
        startActivity(intent);
    }

    public void golapangan(View view) {
        Intent intent = new Intent(getApplicationContext(), AddFieldActivity.class);
        startActivity(intent);
    }

    public void goedit(View view) {
    }

    public void goeditlap(View view) {
    }
}