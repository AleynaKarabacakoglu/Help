package com.example.help.Models;

public class Aleyna  {
    String isim;

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getSoyisim() {
        return soyisim;
    }

    public void setSoyisim(String soyisim) {
        this.soyisim = soyisim;
    }

    public Aleyna(String isim, String soyisim) {
        this.isim = isim;
        this.soyisim = soyisim;
    }

    String soyisim;

    public Aleyna() {
    }
}
