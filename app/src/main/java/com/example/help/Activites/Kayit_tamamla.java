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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.help.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Kayit_tamamla extends AppCompatActivity implements LocationListener {


    Bundle bnd3,bnd4;
    String enlem,boylam;
    private EditText Kullanici,
            TC, Ilac,Kronik, Gecirilen,
            YakinAdi, YakinNumarasi,Numara;
    private TextView txtcinsiyet;
    private Spinner spKan,spRh;
    FirebaseDatabase db2;
    private RadioButton kadin,erkek;
    private RadioGroup GroupCinsiyet;
    String cinsiyet;
    private String[] kan={"A","B","AB","0"};
    private String [] rh={"+","-"};
    private ArrayAdapter<String> dataAdapterForKan;
    private ArrayAdapter<String> dataAdapterForRh;

    LocationManager konum_yoneticisi;
    String provider;



    private void init()
    {

        db2=FirebaseDatabase.getInstance();
        Kullanici = findViewById(R.id.etKullanici);
        TC = findViewById(R.id.etTC);
        Ilac = findViewById(R.id.etIlac);
        Kronik = findViewById(R.id.etKronik);
        Gecirilen = findViewById(R.id.etGecirilen);
        YakinAdi = findViewById(R.id.etYakinAdi);
        YakinNumarasi = findViewById(R.id.etYakinNum);
        txtcinsiyet = findViewById(R.id.txtCinsiyet);
        Numara=findViewById(R.id.etNumara);
        kadin=findViewById(R.id.radioBtnKadin);
        erkek=findViewById(R.id.radioBtnErkek);
        GroupCinsiyet=findViewById(R.id.radioGroupCinsiyet);
        spKan=findViewById(R.id.spinnerKan);
        spRh=findViewById(R.id.spinnerRh);
    }
    private void profil_olustur(String tc,String ilac,String kronik, String gecirilen,String kan,String yakinadi,String yakinno,String cinsiyet)
    {
        DatabaseReference dbRef=db2.getReference().child("Kullanici_Bilgisi");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbRef.child(uid).setValue(new Kullanici(tc,ilac,kronik,gecirilen,kan,yakinadi,yakinno,cinsiyet));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kayit_tamamla);

        bnd3= new Bundle();
        bnd4= new Bundle();
        init();
        //Spinner'lar için adapterleri hazırlıyoruz.
        dataAdapterForKan = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, kan);
        dataAdapterForRh = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,rh);

        //Listelenecek verilerin görünümünü belirliyoruz.
        dataAdapterForKan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForRh.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Hazırladğımız Adapter'leri Spinner'lara ekliyoruz.
        spKan.setAdapter(dataAdapterForKan);
        spRh.setAdapter(dataAdapterForRh);

        // Listelerden bir eleman seçildiğinde yapılacakları tanımlıyoruz.
        spKan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                Toast.makeText(Kayit_tamamla.this, "Seçim yapıldı.",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }});
        spRh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                Toast.makeText(Kayit_tamamla.this, "Seçim yapıldı.",
                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.radioBtnKadin:
                if (checked) cinsiyet = "Kadın";
                    break;
            case R.id.radioBtnErkek:
                if (checked) cinsiyet = "Erkek";
                    break;

        }}



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
        if (Kullanici.getText().toString().trim().equals(""))
            Kullanici.setError("İsim soyisim boş bırakılamaz.");
        else if (TC.getText().toString().trim().equals(""))
            TC.setError("TC Kimlik Numarası boş bırakılamaz.");
        else if(TC.length() != 11)
            TC.setError("Girilen TC 11 karakter olmalı!");
        else if (!kadin.isChecked()&&!erkek.isChecked())
                txtcinsiyet.setError("Lütfen seçim yapınız.");
        else if (kadin.isChecked()&&erkek.isChecked())
            txtcinsiyet.setError("Lütfen sadece birini seçiniz.");
        else if (Ilac.getText().toString().trim().equals(""))
            Ilac.setError("Kullanılan İlaç boş bırakılamaz.");
        else if (Kronik.getText().toString().trim().equals(""))
            Kronik.setError("Kronik Hastalıklar boş bırakılamaz.");
        else if (Gecirilen.getText().toString().trim().equals(""))
            Gecirilen.setError("Geçirilen Hastalıklar boş bırakılamaz.");

        else if (YakinAdi.getText().toString().trim().equals(""))
            YakinAdi.setError("Yakın Adı boş bırakılamaz.");
        else if (YakinNumarasi.getText().toString().trim().equals(""))
            YakinNumarasi.setError("Yakın Numarası boş bırakılamaz.");
        else {

            String tc=TC.getText().toString().trim();
            String ilac=Ilac.getText().toString().trim();
            String kronik=Kronik.getText().toString().trim();
            String kan=spKan.getSelectedItem().toString()+spRh.getSelectedItem().toString();
            String yakinadi=YakinAdi.getText().toString().trim();
            String yakinno=YakinNumarasi.getText().toString().trim();
            String gecirilen=Gecirilen.getText().toString().trim();


            profil_olustur(tc,ilac,kronik,gecirilen,kan,yakinadi,yakinno,cinsiyet);
            final Intent intent=new Intent(getApplicationContext(),Anasayfa.class);
            bnd3.putString("enlem",cinsiyet);
            intent.putExtras(bnd3);
            bnd4.putString("boylam",boylam);
            intent.putExtras(bnd4);
            startActivity(intent);


        }
    }
}