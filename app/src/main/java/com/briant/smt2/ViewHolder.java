package com.briant.smt2;

import android.content.Context;
import android.speech.RecognizerResultsIntent;
import android.speech.tts.TextToSpeech;
import android.telecom.Call;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mview;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        mview = itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClick.onItemClick(v, getAdapterPosition());
            }
        });
    }
    public void setKey(String key){

    }
    public void setDetails(Context context, String nama, String harga, String jenis, String image){
        TextView mNama = mview.findViewById(R.id.namaList);
        TextView mHarga = mview.findViewById(R.id.hargaList);
        TextView mJenis = mview.findViewById(R.id.jenisList);
        ImageView mImage = mview.findViewById(R.id.ImageList);
        mNama.setText(nama);
        mHarga.setText(harga);
        mJenis.setText(jenis);
        Picasso.get().load(image).into(mImage);
    }
    private ViewHolder.ItemClickListener mClick;
    public interface ItemClickListener{
        void onItemClick(View view, int position);
    }
    public void setOnClickListener(ViewHolder.ItemClickListener itemClickListener){
        mClick = itemClickListener;
    }
}
