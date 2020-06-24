package com.example.help.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.telephony.SmsManager;
import android.widget.TextView;
import android.widget.Toast;
import com.example.help.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

public class Anasayfa extends AppCompatActivity implements LocationListener  {

    private static final String TAG = "Anasayfa";
    public TextView txtalinanKullanici,txtkonum;
    String konum,enlem,boylam,sehir,hastane,hastaneNumarasi,polis,polisNumarasi,itfaiyeNumarasi;
    FirebaseDatabase db2;
    FirebaseAuth fAuth;
    LocationManager konum_yoneticisi;
    String provider;
    String isim,tc,numara,cinsiyet,kan,ilac,kronik,gecirilen,yakinadi,yakinnum;
    private RequestQueue mqueue;
    //************ SET  GET METHODLARI*********
    public String getIsim() {
        return isim;
    }
    public void setIsim(String İsim) {
        this.isim = İsim;
    }
    public String getTc() {
        return tc;
    }
    public void setTc(String TC) {
        this.tc = TC;
    }
    public String getKan() {
        return kan;
    }
    public void setKan(String Kan) {
        this.kan = Kan;
    }
    public String getIlac() {
        return ilac;
    }
    public void setIlac(String Ilac) {
        this.ilac = Ilac;
    }
    public String getNumara() {
        return numara;
    }
    public void setNumara(String Numara) {
        this.numara = Numara;
    }
    public String getKronik() {
        return kronik;
    }
    public void setKronik(String Kronik) {
        this.kronik = Kronik;
    }
    public String getYakinadi() {
        return yakinadi;
    }
    public void setYakinadi(String Yakinadi) {
        this.yakinadi = Yakinadi;
    }
    public String getYakinnum() {
        return yakinnum;
    }
    public void setYakinnum(String Yakinnum) {
        this.yakinnum = Yakinnum;
    }
    public String getGecirilen() {
        return gecirilen;
    }
    public void setGecirilen(String Gecirilen) {
        this.gecirilen = Gecirilen;
    }
    public String getCinsiyet() {
        return cinsiyet;
    }
    public void setCinsiyet(String Cinsiyet) {
        this.cinsiyet = Cinsiyet;
    }
    public String getEnlem() {
        return enlem;
    }
    public void setEnlem(String Enlem) {
        this.enlem = Enlem;
    }
    public String getBoylam() {
        return boylam;
    }
    public void setBoylam(String Boylam) {
        this.boylam = Boylam;
    }
    public String getKonum() {
        return konum;
    }
    public void setKonum(String Konum) {
        this.konum = Konum;
    }
    public String getHastane() {
        return hastane;
    }
    public void setHastane(String Hastane) {
        this.hastane = Hastane;
    }
    public String getHastaneNumarasi() {
        return hastaneNumarasi;
    }
    public void setHastaneNumarasi(String HastaneNumarasi) {
        this.hastaneNumarasi = HastaneNumarasi;
    }
    public String getPolis() {
        return polis;
    }
    public void setPolis(String Polis) {
        this.polis = Polis;
    }
    public String getPolisNumarasi() {
        return polisNumarasi;
    }
    public void setPolisNumarasi(String PolisNumarasi) {
        this.polisNumarasi = PolisNumarasi;
    }
    public String getSehir() {
        return sehir;
    }
    public void setSehir(String Sehir) {
        this.sehir = Sehir;
    }
    public String getItfaiyeNumarasi() {
        return itfaiyeNumarasi;
    }
    public void setItfaiyeNumarasi(String ItfaiyeNumarasi) {
        this.itfaiyeNumarasi = ItfaiyeNumarasi;
    }

    private void init()
    {
        db2 = FirebaseDatabase.getInstance();
        txtalinanKullanici = findViewById(R.id.alinanKullanici);
        fAuth = FirebaseAuth.getInstance();
        txtkonum=findViewById(R.id.konum);
        mqueue = Volley.newRequestQueue(this);
    }
    //******** FIREBASEDEN  KULLANICI BİLGİLERİ CEKİLDİ*******************
    private void kayitlariGetir()
    {

        String uid = fAuth.getUid();
        DatabaseReference dbGelenler = db2.getReference().child("Kullanici_Bilgisi").child(uid);
        dbGelenler.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Kullanici kullanici = dataSnapshot.getValue(Kullanici.class);
                //txtalinanKullanici.append(kullanici.getTc() + "\n");
                setIsim(kullanici.getIsim());
                setTc(kullanici.getTc());
                setCinsiyet(kullanici.getCinsiyet());
                setYakinnum(kullanici.getYakinno());
                setNumara(kullanici.getNumara());
                setKan(kullanici.getKan());
                setIlac(kullanici.getIlac());
                setGecirilen(kullanici.getGecirilen());
                setKronik(kullanici.getKronik());
                setYakinadi(kullanici.getYakinadi());


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {//bundle aktiviteler arası geçişi sağlar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.anasayfa);
        init();
        kayitlariGetir();
        setKonum("null");
        setHastaneNumarasi("null");
        setPolisNumarasi("null");
        setSehir("null");
        setItfaiyeNumarasi("null");

        konum_yoneticisi = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = konum_yoneticisi.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        Location lokasyon = konum_yoneticisi.getLastKnownLocation(provider);

        if (lokasyon != null)
        {

        }
        else {
            txtkonum.setText("NotAvaliable");
            enlem="not";
            boylam="not";
        }
        JsonParse();
        EnyakinItfaiye();
        EnYakinHastaneNumarasi();
        EnYakinPolis();


    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission
                (this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            return;
        }
        konum_yoneticisi.requestLocationUpdates(provider, 10, 1, (LocationListener) this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        konum_yoneticisi.removeUpdates((LocationListener) this);
    }
    @Override
    public void onLocationChanged(Location location)
    {
        double lat=location.getLatitude();//enlem bilgisi çekildi
        double log=location.getLongitude();//boylam bilgisi çekildi
        setEnlem(String.valueOf(lat));
        setBoylam(String.valueOf(log));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {

    }

    @Override
    public void onProviderEnabled(String provider)
    {
        Toast.makeText(this,"aktif ",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        Toast.makeText(this,"pasif ",Toast.LENGTH_SHORT).show();
    }

    public void onClickPolice(View v) {

        EnYakinPolis();
        if(getPolisNumarasi()!="null")
        {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                //smsManager.sendTextMessage(getPolisNumarasi(), null, getIsim()+" zor durumda!", null, null);
                smsManager.sendTextMessage(getPolisNumarasi(), null, "Anlik Konum: (enlem:"+enlem+") (boylam:"+getBoylam()+")", null, null);

                Toast.makeText(getApplicationContext(), "Mesajınız İletildi!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Mesajınız gönderilemedi. Lütfen tekrar deneyiniz.",
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, "onClick: ",e );

                e.printStackTrace();
            }


        }

        else
        {
            Toast.makeText(Anasayfa.this,"Lütfen tekrar deneyiniz",Toast.LENGTH_SHORT).show();
        }


    }
    public void onClickDoctor(View v) {

        EnYakinHastaneNumarasi();
        if(getHastaneNumarasi()!="null")
        {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(getHastaneNumarasi(), null, "isim:"+getIsim()+" TC:"+getTc()+" Kan:"+getKan(), null, null);
                smsManager.sendTextMessage(getHastaneNumarasi(), null, " Cinsiyet:"+getCinsiyet() +" Kullanilan Ilac:"+getIlac(), null, null);
                smsManager.sendTextMessage(getHastaneNumarasi(), null, "Enlem:"+getEnlem()+" Boylam:"+getBoylam(), null, null);

                Toast.makeText(getApplicationContext(), "Mesajınız İletildi!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Mesajınız gönderilemedi. Lütfen tekrar deneyiniz.",
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, "onClick: ",e );

                e.printStackTrace();
            }

        }
        else
            {
                Toast.makeText(Anasayfa.this,"Lütfen tekrar deneyiniz",Toast.LENGTH_SHORT).show();
            }





    }
    public void onClickFireman(View v) {


        EnyakinItfaiye();
        if(getItfaiyeNumarasi()!="null")
        {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(getItfaiyeNumarasi(), null, "Enlem:"+getEnlem()+" Boylam:"+getBoylam(), null, null);

                Toast.makeText(getApplicationContext(), "Mesajınız İletildi!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Mesajınız gönderilemedi. Lütfen tekrar deneyiniz.",
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, "onClick: ",e );

                e.printStackTrace();
            }
        }
        //txtkonum.setText(enlem+"   "+boylam);
        else
        {
            Toast.makeText(Anasayfa.this,"Lütfen tekrar deneyiniz",Toast.LENGTH_SHORT).show();
        }





    }
    public void onClickFriend(View v)
    {
        kayitlariGetir();
        String telNo = getYakinnum();

        JsonParse();
        if(getKonum()!="null")
        {
            try {
                SmsManager smsManager = SmsManager.getDefault();
                //smsManager.sendTextMessage(telNo, null, "Acil Durum!! Arkadaşın "+getİsim()+" zor durumda.", null, null);
                smsManager.sendTextMessage(telNo, null, "Yardım! Anlık Konumum:"+getKonum(), null, null);

                Toast.makeText(getApplicationContext(), "Mesajınız İletildi!",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(),
                        "Mesajınız gönderilemedi. Lütfen tekrar deneyiniz.",
                        Toast.LENGTH_LONG).show();
                Log.e(TAG, "onClick: ",e );

                e.printStackTrace();
            }
        }
        else
            {
            Toast.makeText(Anasayfa.this,"Lütfen tekrar deneyiniz",Toast.LENGTH_SHORT).show();
            }
    }

        public void onClickExit(View v) {
            Intent i = new Intent(Anasayfa.this, Giris.class);
            FirebaseAuth.getInstance().signOut();
            startActivity(i);
        }



        public void onClickProfilEdit(View v) {
            Intent i = new Intent(Anasayfa.this, Profili_Duzenle.class);
            startActivity(i);
        }

    private void JsonParse() {
        Log.d(TAG, "JsonParse: BU METOD ÇALIŞTI");
        String appid="wVxKmYyJpPqAGATc5I4Y"; //hereapi id
        String url = "https://places.ls.hereapi.com/places/v1/discover/here?at="+getEnlem()+"%2C"+getBoylam()+"&Accept-Language=tr-tr&app_id="+appid+"&app_code=dZ3Wqao7oizHlwYefK4nkQ";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONObject jsonArray = null;
                JSONObject context;
                JSONObject location;
                JSONObject adres;
                String text;
                String stateCode;
                try {
                    jsonArray = response.getJSONObject("search");
                    context=jsonArray.getJSONObject("context");
                    location=context.getJSONObject("location");
                    adres=location.getJSONObject("address");
                    text=adres.getString("text");
                    stateCode=adres.getString("stateCode");
                    setKonum(text);
                    setSehir(stateCode);
                    //Toast.makeText(Anasayfa.this,getKonum(),Toast.LENGTH_SHORT).show();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();

            }
        });
        mqueue.add(request);
    }
    private void EnYakinHastaneNumarasi() {
        Log.d(TAG, "JsonParse: BU METOD ÇALIŞTI");
        String appid="wVxKmYyJpPqAGATc5I4Y"; //hereapi id
        String url = "https://places.ls.hereapi.com/places/v1/discover/search?at="+getEnlem()+"%2C"+getBoylam()+"&q=hospital&Accept-Language=tr-tr&app_id="+appid+"&app_code=dZ3Wqao7oizHlwYefK4nkQ";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject results;
                JSONArray jsonArray = null;
                JSONObject title;

                try {
                    results=response.getJSONObject("results");
                    jsonArray = results.getJSONArray("items");
                    title=jsonArray.getJSONObject(0);
                    setHastane(title.getString("title"));
                    //Toast.makeText(Anasayfa.this,getHastane(),Toast.LENGTH_SHORT).show();
                    //*****************FIREBASE REELTIME DATABASE'DEN EN YAKIN HASTANE NUMARASI CEKKILDI***********
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Kurulus").child("Hastaneler").child(getHastane()).child("Numara");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            //txtalinanKullanici.append(dataSnapshot.getValue().toString());
                            setHastaneNumarasi(dataSnapshot.getValue().toString());


                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //*******************************************************************************************

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mqueue.add(request);
    }
    private void EnYakinPolis() {
        Log.d(TAG, "JsonParse: BU METOD ÇALIŞTI");
        String appid="wVxKmYyJpPqAGATc5I4Y"; //hereapi id
        String url = "https://places.ls.hereapi.com/places/v1/discover/search?at="+getEnlem()+"%2C"+getBoylam()+"&q=police&Accept-Language=tr-tr&app_id="+appid+"&app_code=dZ3Wqao7oizHlwYefK4nkQ";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                JSONObject results;
                JSONArray jsonArray = null;
                JSONObject title;

                try {
                    results=response.getJSONObject("results");
                    jsonArray = results.getJSONArray("items");
                    title=jsonArray.getJSONObject(0);
                    setPolis(title.getString("title"));
                    //Toast.makeText(Anasayfa.this,getHastane(),Toast.LENGTH_SHORT).show();
                    //*****************FIREBASE REELTIME DATABASE'DEN EN YAKIN POLIS NUMARASI CEKKILDI***********
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Kurulus").child("Polis").child(getPolis()).child("Numara");
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {


                            //txtalinanKullanici.append(dataSnapshot.getValue().toString());
                            setPolisNumarasi((dataSnapshot.getValue().toString()));


                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //*******************************************************************************************

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mqueue.add(request);
    }
    private void EnyakinItfaiye()
    {

        JsonParse();
        if(getHastaneNumarasi()!="null")
        {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Kurulus").child("İtfaiye").child(getSehir()+" İtfaiyesi").child("Numara");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {


                    //txtalinanKullanici.append(dataSnapshot.getValue().toString());
                    setItfaiyeNumarasi((dataSnapshot.getValue().toString()));


                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else
        {
        }

    }
}