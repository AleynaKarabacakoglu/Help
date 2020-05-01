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
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;


public class Anasayfa extends AppCompatActivity implements LocationListener  {

    private static final String TAG = "Anasayfa";
    private ImageButton btnHelp;
    private Button btnCikis, btnProfilDuzenle;
    public TextView txtalinanKullanici,txtkonum;
    String alinanEnlem;
    String alinanBoylam;
    String konum,enlem,boylam;

    FirebaseDatabase db2;
    FirebaseAuth fAuth;
    LocationManager konum_yoneticisi;
    String provider;

    private void init() {
        db2 = FirebaseDatabase.getInstance();
        btnHelp = findViewById(R.id.btnHelp);
        btnCikis = findViewById(R.id.btnCikis);
        btnProfilDuzenle = findViewById(R.id.btnProfilDuzenle);
        txtalinanKullanici = findViewById(R.id.alinanKullanici);
        fAuth = FirebaseAuth.getInstance();
        txtkonum=findViewById(R.id.konum);
        enlem="sila";

    }



    private void kayitlariGetir() {

        txtalinanKullanici.setText("");
        String uid = fAuth.getUid();

        DatabaseReference dbGelenler = db2.getReference().child("Kullanici_Bilgisi").child(uid);
        dbGelenler.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Kullanici kullanici = dataSnapshot.getValue(Kullanici.class);
                txtalinanKullanici.append(kullanici.getTc() + "\n");
                txtalinanKullanici.append(kullanici.getIsim()+"\n");
                txtalinanKullanici.append(kullanici.getCinsiyet() + "\n");
                txtalinanKullanici.append(kullanici.getNumara() + "\n");
                txtalinanKullanici.append(kullanici.getKan() + "\n");
                txtalinanKullanici.append(kullanici.getIlac() + "\n");
                txtalinanKullanici.append(kullanici.getKronik() + "\n");
                txtalinanKullanici.append(kullanici.getGecirilen() + "\n");
                txtalinanKullanici.append(kullanici.getYakinadi()+ "\n");
                txtalinanKullanici.append(kullanici.getYakinno()+ "\n");

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
        txtkonum.setText("djnfgkjdfkj");
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

        public void onClickHelp(View v) {
            String telNo = "05445038639";//mesaj gönderilecek numara
            txtkonum.setText(enlem);

            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(telNo, null, "aaa", null, null);

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

        public void onClickExit(View v) {
            Intent i = new Intent(Anasayfa.this, Giris.class);
            FirebaseAuth.getInstance().signOut();
            startActivity(i);
        }



        public void onClickProfilEdit(View v) {
            Intent i = new Intent(Anasayfa.this, Profili_Duzenle.class);
            startActivity(i);
        }


}