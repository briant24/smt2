package com.briant.smt2;

import java.util.Locale;

public class HandlerRegister {
    public String nama, nohp, alamat, username, password, usrLvl, url;

    public HandlerRegister(String nama, String nohp, String alamat, String username, String password, String usrLvl, String url){
        this.nama = nama;
        this.nohp = nohp;
        this.alamat = alamat;
        this.username = username;
        this.password = password;
        this.usrLvl = usrLvl;
        this.url = url;
    }

    public String getNama(){return nama;}
    public String getNohp(){return nohp;}
    public String getAlamat(){return alamat;}
    public String getUsername(){return username;}
    public String getPassword(){return password;}
    public String getUsrLvl(){return usrLvl;}
    public String getUrl(){return url;}
}
