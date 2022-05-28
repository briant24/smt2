package com.briant.smt2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private EditText etuser,etpass;
    private String username,password;
    private Button btnlogin;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progressBar2);
        etuser = findViewById(R.id.username_et);
        etpass = findViewById(R.id.password_et);
        btnlogin = findViewById(R.id.btn_login);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etuser.getText().toString().trim();
                password = etpass.getText().toString().trim();
                if (username.isEmpty()){
                    etuser.setError("Data belum dimasukkan!");
                    etuser.requestFocus();
                }else if(password.isEmpty()){
                    etpass.setError("Data belum dimasukkan!");
                    etpass.requestFocus();
                }else{
                    ceklogin(username, password);
                }
            }
        });
    }

    private void ceklogin(String username, String password){
        progressBar.setVisibility(View.VISIBLE);
        databaseReference.child("Data Users")
                .orderByChild("username")
                .equalTo(username)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot data : snapshot.getChildren()) {
                            String pass = data.child("password").getValue().toString();
                            if(pass.equals(password)){
                                String level = data.child("usrLvl").getValue().toString();
                                if(level.equals("0")) {
                                    String nama = data.child("nama").getValue().toString();
                                    Intent adminMenu = new Intent(getApplicationContext(), MainActivity.class);
                                    adminMenu.putExtra("nama", nama);
                                    startActivity(adminMenu);
                                }else {
                                    Intent userMenu = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(userMenu);
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Username dan Atau Password Yang Anda Masukan Salah", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

    public void daftarr(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }
}