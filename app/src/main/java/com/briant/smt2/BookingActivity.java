package com.briant.smt2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class BookingActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    private ViewHolder.ItemClickListener mItemClickListener;
    private LinearLayoutManager mLinearLayoutManager;
    FirebaseRecyclerAdapter<HandlerLapangan, ViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<HandlerLapangan> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        mLinearLayoutManager = new LinearLayoutManager(this);
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);

        mRecyclerView = findViewById(R.id.RecyclerList);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Data Lapangan");
        
        showdata();
    }

    private void showdata() {
        options = new FirebaseRecyclerOptions.Builder<HandlerLapangan>().setQuery(databaseReference,HandlerLapangan.class).build();
        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<HandlerLapangan, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull HandlerLapangan model) {
                holder.setDetails(getApplicationContext(), model.getNamalap(), model.getHarga(), model.getJenis(), model.getUrl());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String nama = model.getNamalap().toString().trim();
                        String harga = model.getHarga().toString().trim();
                        String jenis = model.getJenis().toString().trim();
                        String url = model.getUrl().toString().trim();
                        Intent intent = new Intent(getApplicationContext(), PesanActivity.class);
                        intent.putExtra("namaLap", nama);
                        intent.putExtra("hargaLap", harga);
                        intent.putExtra("jenisLap", jenis);
                        intent.putExtra("urlImage", url);
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_row,parent,false);
                ViewHolder viewHolder = new ViewHolder(itemView);
                return viewHolder;
            }
        };
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    protected void onStart(){
        super.onStart();
        if(firebaseRecyclerAdapter != null){
            firebaseRecyclerAdapter.startListening();
        }
    }
}