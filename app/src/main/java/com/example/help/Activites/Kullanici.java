package com.example.help.Activites;

public class Kullanici {
    private String username, password, email, tc, ilac, kronik, gecirilen, kan, yakinadi, yakinno, cinsiyet;

    private Kullanici() {
    }

    public Kullanici(String tc, String ilac, String kronik, String gecirilen, String kan, String yakin_adi, String yakin_no, String cinsiyet) {
        this.tc = tc;
        this.ilac = ilac;
        this.kronik = kronik;
        this.gecirilen = gecirilen;
        this.kan = kan;
        this.yakinadi = yakin_adi;
        this.yakinno = yakin_no;
    }

    public Kullanici(String username, String email, String password) {
        this.username = username;
        this.password = password;
        this.email = email;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getTc() {
        return tc;
    }

    public void setIlac(String ilac) {
        this.ilac = ilac;
    }

    public String getIlac() {
        return ilac;
    }

    public void setGecirilen(String gecirilen) {
        this.gecirilen = gecirilen;
    }

    public String getGecirilen() {
        return gecirilen;
    }

    public void setKronik(String kronik) {
        this.kronik = kronik;
    }

    public String getKronik() {
        return kronik;
    }

    public void setYakinadi(String yakinadi) {
        this.yakinadi = yakinadi;
    }

    public String getYakinno() {
        return yakinno;
    }

    public void setYakin_no(String yakin_no) {
        this.yakinno = yakinno;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }
}


