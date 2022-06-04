package com.briant.smt2;

import android.widget.TextView;

public class HandlerLapangan {
    public String namalap;
    public String harga;
    public String jenis;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String url;

    public void setNamalap(String namalap) {
        this.namalap = namalap;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public HandlerLapangan(String namalap,String harga,String jenis,String url){
        this.namalap = namalap;
        this.harga = harga;
        this.jenis = jenis;
        this.url = url;
    }
    public String getNamalap(){return this.namalap;}
    public String getHarga(){return this.harga;}
    public String getJenis(){return this.jenis;}
    public String getUrl(){return this.url;}

    public HandlerLapangan(){

    }
}
