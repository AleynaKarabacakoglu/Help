package com.example.help.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.help.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profili_Duzenle extends AppCompatActivity {

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
    FirebaseAuth fAuth;



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
        fAuth = FirebaseAuth.getInstance();
    }
    private void kayitlariGetir() {

        String uid = fAuth.getUid();

        DatabaseReference dbGelenler = db2.getReference().child("Kullanici_Bilgisi").child(uid);
        dbGelenler.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Kullanici kullanici = dataSnapshot.getValue(Kullanici.class);
                TC.setText(kullanici.getTc().trim());
                Kullanici.setText(kullanici.getIsim());
                Numara.setText(kullanici.getNumara());
                Ilac.setText(kullanici.getIlac());
                Kronik.setText(kullanici.getKronik());
                Gecirilen.setText(kullanici.getGecirilen() );
                YakinAdi.setText(kullanici.getYakinadi());
                YakinNumarasi.setText(kullanici.getYakinno().trim());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

   private void profili_duzenle(String isim,String tc,String ilac,String kronik, String gecirilen,String kan,String yakinadi,String yakinno,String cinsiyet,String numara)
    {
        DatabaseReference dbRef=db2.getReference().child("Kullanici_Bilgisi");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        dbRef.child(uid).setValue(new Kullanici(isim,tc,ilac,kronik,gecirilen,kan,yakinadi,yakinno,cinsiyet,numara));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profili__duzenle);
        init();
        kayitlariGetir();
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

                Toast.makeText(Profili_Duzenle.this, "Seçim yapıldı.",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }});
        spRh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                Toast.makeText(Profili_Duzenle.this, "Seçim yapıldı.",
                        Toast.LENGTH_SHORT).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


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



    public void clickprofilduzenle(View v)
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
            String iletisim_num=Numara.getText().toString().trim();
            String Isim=Kullanici.getText().toString().trim();


            profili_duzenle(Isim,tc,ilac,kronik,gecirilen,kan,yakinadi,yakinno,cinsiyet,iletisim_num);
            final Intent intent=new Intent(getApplicationContext(),Anasayfa.class);

            startActivity(intent);


        }
    }
}