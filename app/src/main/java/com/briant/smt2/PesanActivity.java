package com.briant.smt2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PesanActivity extends AppCompatActivity {

    private TextView nama,jenis,harga;
    private ImageView gambar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
    }

    @Override
    public void onStart(){
        super.onStart();
        nama = findViewById(R.id.tvNama);
        jenis = findViewById(R.id.jenis);
        harga = findViewById(R.id.harga);
        gambar = findViewById(R.id.imgfocus);

        Intent ambildata=getIntent();
        String strnama, strharga, strgjenis, strurl;
        strnama = ambildata.getStringExtra("namaLap");
        strharga = ambildata.getStringExtra("hargaLap");
        strgjenis = ambildata.getStringExtra("jenisLap");
        strurl = ambildata.getStringExtra("urlImage");
        nama.setText(strnama);
        harga.setText(strharga);
        jenis.setText(strgjenis);
        Picasso.get().load(strurl).into(gambar);
    }
}