package com.briant.smt2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RegisterActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private ImageView imageView;
    private EditText nama,nohp,alamat,username,password;
    private String Nama,Nohp,Alamat,Username,Password;
    private Button btnbrowse,btnupload;
    private DatabaseReference dbUsers = FirebaseDatabase.getInstance().getReference().child("Data Users");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.profilpict);
        nama = findViewById(R.id.nama_et);
        nohp = findViewById(R.id.nohp_et);
        alamat = findViewById(R.id.alamat_et);
        username = findViewById(R.id.username_in);
        password = findViewById(R.id.password_in);
        btnbrowse = findViewById(R.id.btn_gambar);
        btnupload = findViewById(R.id.btn_register);

        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Nama = nama.getText().toString().trim();
                Nohp = nohp.getText().toString().trim();
                Alamat = alamat.getText().toString().trim();
                Username = username.getText().toString().trim();
                Password = password.getText().toString().trim();
                if (Nama.isEmpty()){
                    nama.setError("Data belum dimasukkan");
                    nama.requestFocus();
                }else if (Nohp.isEmpty()) {
                    nohp.setError("Data belum dimasukkan");
                    nohp.requestFocus();
                }else if (Alamat.isEmpty()){
                    alamat.setError("Data belum dimasukkan");
                    alamat.requestFocus();
                }else if (Username.isEmpty()){
                    username.setError("Data belum dimasukkan");
                    username.requestFocus();
                }else if (Password.isEmpty()) {
                    password.setError("Data belum dimasukkan");
                    password.requestFocus();
                }else if (uri == null){
                    Toast.makeText(getApplicationContext(), "Gambar kosong", Toast.LENGTH_SHORT).show();
                }else{
                    uploaddata(uri);
                }
            }
        });

        btnbrowse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihgambar();
            }
        });
    }
    private void uploaddata(Uri uri) {
        Nama = nama.getText().toString().trim();
        Nohp = nohp.getText().toString().trim();
        Alamat = alamat.getText().toString().trim();
        Username = username.getText().toString().trim();
        Password = password.getText().toString().trim();
        StorageReference fileRef = storageReference.child(Username + "." + getFileExtension(uri));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String usrlv = "0";
                        HandlerRegister handlerUpload = new HandlerRegister(Nama, Nohp, Alamat, Username, Password, usrlv, uri.toString());
                        String modelId = dbUsers.push().getKey();
                        dbUsers.child(modelId).setValue(handlerUpload);
                        Toast.makeText(getApplicationContext(), "Berhasil!!", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                        hapus();
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                        intent.putExtra("USERNAME",Username);
                        startActivity(intent);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Gagal!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pilihgambar() {
        Intent gallerryIntent = new Intent();
        gallerryIntent.setAction(Intent.ACTION_GET_CONTENT);
        gallerryIntent.setType("image/*");
        startActivityForResult(gallerryIntent,2);
    }

    private void hapus(){
        nama.setText("");
        nohp.setText("");
        alamat.setText("");
        username.setText("");
        password.setText("");
        imageView.setImageURI(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK && data != null){
            uri = data.getData();
            imageView.setImageURI(uri);
        }
    }

    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    public void masukk(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
    }
}