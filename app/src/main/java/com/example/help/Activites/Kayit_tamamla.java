package com.example.help.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.help.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Kayit_tamamla extends AppCompatActivity implements LocationListener {


    Bundle bnd3,bnd4;
    String enlem,boylam;
    private EditText etKullanici,etMail,
            etTC, etIlac, etKronik, etGecirilen,
            etKan, etYakinAdi, etYakinNumarasi,
            etCinsiyet;
    FirebaseDatabase db2;

    //private Button btnProfilTamamla;
    LocationManager konum_yoneticisi;
    String provider;



    private void init()
    {
        //btnProfilTamamla = findViewById(R.id.btnProfilTamamla);
        db2=FirebaseDatabase.getInstance();
        etKullanici = findViewById(R.id.etKullanici);
        etMail = findViewById(R.id.etMail);
        etTC = findViewById(R.id.etTC);
        etIlac = findViewById(R.id.etIlac);
        etKronik = findViewById(R.id.etKronik);
        etGecirilen = findViewById(R.id.etGecirilen);
        etKan = findViewById(R.id.etKan);
        etYakinAdi = findViewById(R.id.etYakinAdi);
        etYakinNumarasi = findViewById(R.id.etYakinNumarasi);
        etCinsiyet = findViewById(R.id.etCinsiyet);
    }
    private void profil_olustur(String tc,String ilac,String kronik, String gecirilen,String kan,String yakinadi,String yakinno,String cinsiyet)
    {
        DatabaseReference dbRef=db2.getReference().child("Kullanici_Bilgisi");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbRef.child(uid).setValue(new Kullanici(tc,ilac,kronik,gecirilen,kan,yakinadi,yakinno,cinsiyet));

//        String key= dbRef.push().getKey();
//        DatabaseReference dbRefYeni=db2.getReference().child("Kullanici_Bilgisi/"+key);
//        dbRefYeni.setValue(new Kullanici(tc,ilac,kronik,gecirilen,kan,yakinadi,yakinno,cinsiyet));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kayit_tamamla);

        bnd3= new Bundle();
        bnd4= new Bundle();
        init();

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
            //txtEnlem.setText("NotAvaliable");
            // txtBoylam.setText("Not Avaiable");
            enlem="not";
            boylam="not";
        }


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
        konum_yoneticisi.requestLocationUpdates(provider, 10, 1, this);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        konum_yoneticisi.removeUpdates(this );
    }
    @Override
    public void onLocationChanged(Location location)
    {
        double lat=location.getLatitude();//enlem bilgisi çekildi
        double log=location.getLongitude();//boylam bilgisi çekildi
        enlem=String.valueOf(lat);//enlemi doubledan stringe çevirdik.
        boylam=String.valueOf(log);

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

    public void clickp(View v)
    {
        if (etKullanici.getText().toString().trim().equals(""))
            etKullanici.setError("İsim soyisim boş bırakılamaz.");
        else if (etTC.getText().toString().trim().equals(""))
            etTC.setError("TC Kimlik Numarası boş bırakılamaz.");
        else if (etCinsiyet.getText().toString().trim().equals(""))
            etCinsiyet.setError("Cinsiyet boş bırakılamaz.");
        else if (etIlac.getText().toString().trim().equals(""))
            etIlac.setError("Kullanılan İlaç boş bırakılamaz.");
        else if (etKronik.getText().toString().trim().equals(""))
            etKronik.setError("Kronik Hastalıklar boş bırakılamaz.");
        else if (etGecirilen.getText().toString().trim().equals(""))
            etGecirilen.setError("Geçirilen Hastalıklar boş bırakılamaz.");
        else if (etKan.getText().toString().trim().equals(""))
            etKan.setError("Kan Grubu boş bırakılamaz.");
        else if (etYakinAdi.getText().toString().trim().equals(""))
            etYakinAdi.setError("Yakın Adı boş bırakılamaz.");
        else if (etYakinNumarasi.getText().toString().trim().equals(""))
            etYakinNumarasi.setError("Yakın Numarası boş bırakılamaz.");
        else {

            String tc=etTC.getText().toString().trim();
            String ilac=etIlac.getText().toString().trim();
            String kronik=etKronik.getText().toString().trim();
            String kan=etKan.getText().toString().trim();
            String yakinadi=etYakinAdi.getText().toString().trim();
            String yakinno=etYakinNumarasi.getText().toString().trim();
            String gecirilen=etGecirilen.getText().toString().trim();
            String cinsiyet=etCinsiyet.getText().toString().trim();

            profil_olustur(tc,ilac,kronik,gecirilen,kan,yakinadi,yakinno,cinsiyet);
            final Intent intent=new Intent(getApplicationContext(),Anasayfa.class);
            bnd3.putString("enlem",enlem);
            intent.putExtras(bnd3);
            bnd4.putString("boylam",boylam);
            intent.putExtras(bnd4);
            startActivity(intent);


        }
    }
}