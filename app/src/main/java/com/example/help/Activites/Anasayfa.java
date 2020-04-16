package com.example.help.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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


public class Anasayfa extends AppCompatActivity {

    private static final String TAG = "Anasayfa";
    private ImageButton btnHelp;
    private Button btnCikis, btnProfilDuzenle;
    public TextView txtalinanKullanici,txtcinsiyet;
    String alinanMesaj, alinanMesaj2;
    String alinanEnlem;
    String alinanBoylam;
    String konum;
    FirebaseDatabase db2;
    FirebaseAuth fAuth;

    private void init() {
        db2 = FirebaseDatabase.getInstance();
        btnHelp = findViewById(R.id.btnHelp);
        btnCikis = findViewById(R.id.btnCikis);
        btnProfilDuzenle = findViewById(R.id.btnProfilDuzenle);
        txtalinanKullanici = findViewById(R.id.alinanKullanici);
        fAuth = FirebaseAuth.getInstance();
        txtcinsiyet=findViewById(R.id.alinanCinsiyet);

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
        Intent al = getIntent();
//         alinanMesaj=al.getStringExtra("veri");//burada diğer sayfadan veri almak
//         alinanMesaj2=al.getStringExtra("veri2");//için parametre olarak
        alinanBoylam = al.getStringExtra("boylam");//tanımlanan keyler girildi.
        alinanEnlem = al.getStringExtra("enlem");
        konum = alinanEnlem ;//+ "  " + alinanBoylam;
        txtcinsiyet.setText(konum);
        kayitlariGetir();
//
//        txtalinanKullanici.setText(konum);

        //Anasayfadan çıkış yapmak için çıkış butonu eklendi.
        btnCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Anasayfa.this, Giris.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(i);
            }

        });

        //ProfiliDüzenle butonuyla Kayıt_tamamla sayfasına geçiş
        btnProfilDuzenle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Anasayfa.this, Profili_Duzenle.class);
                startActivity(i);
            }

        });

        //Help butonuna basıldığında kullanıcıya giden3 mesaj
        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String telNo = "05445038639";//mesaj gönderilecek numara

                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(telNo, null, konum, null, null);
                    // smsManager.sendTextMessage(telNo, null, alinanMesaj, null, null);
                    //smsManager.sendTextMessage(telNo, null, alinanMesaj2, null, null);
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
        });
    }
}