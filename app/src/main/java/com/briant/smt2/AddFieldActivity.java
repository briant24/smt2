package com.briant.smt2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class AddFieldActivity extends AppCompatActivity {

    private RadioGroup radioJenisGroup;
    private RadioButton radioJenisButton;
    private EditText etNama, etHarga;
    private String namalap,hargalap,jenislap;
    private ImageView imageView;
    private Uri uri;
    private Button btnCari, btnUpload, btnKembali;
    private ProgressBar progressBar;
    private DatabaseReference dbLapangan = FirebaseDatabase.getInstance().getReference().child("Data Lapangan");
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_field);

        radioJenisGroup = findViewById(R.id.radioJenis);
        etNama = findViewById(R.id.namaLap);
        etHarga = findViewById(R.id.hargaJam);
        imageView = findViewById(R.id.imageView);
        progressBar = findViewById(R.id.progressBar3);
        btnCari = findViewById(R.id.button_browse);
        btnUpload = findViewById(R.id.button_upload);
        btnKembali = findViewById(R.id.button_kembali);
        btnKembali.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploaddata(uri);
            }
            private void uploaddata(Uri uri) {
                namalap = etNama.getText().toString().trim();
                hargalap = etHarga.getText().toString().trim();
                int selectedId = radioJenisGroup.getCheckedRadioButtonId();
                radioJenisButton = findViewById(selectedId);
                jenislap = radioJenisButton.getText().toString().trim();
                StorageReference fileRef = storageReference.child(namalap + "." + getFileExtension(uri));
                fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HandlerLapangan handlerUpload = new HandlerLapangan(namalap, hargalap, jenislap, uri.toString());
                                dbLapangan.push().setValue(handlerUpload);
                                Toast.makeText(getApplicationContext(), "Berhasil!!", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
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
        });
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pilihgambar();
            }
        });
    }

    private void pilihgambar() {
        Intent gallerryIntent = new Intent();
        gallerryIntent.setAction(Intent.ACTION_GET_CONTENT);
        gallerryIntent.setType("image/*");
        startActivityForResult(gallerryIntent,2);
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
}