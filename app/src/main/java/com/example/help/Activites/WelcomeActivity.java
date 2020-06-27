package com.example.help.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.example.help.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class WelcomeActivity extends AppCompatActivity {
    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
        Log.d(TAG, "onStop: ");
    }

    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Log.d(TAG, "onCreate: Created");
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d(TAG, "onAuthStateChanged: ");
                if (firebaseAuth.getCurrentUser() != null) {
                    Log.d(TAG, "onAuthStateChanged: currentuser!=null");
                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference().child("Kullanici_Bilgisi");
                    myRef.child(firebaseAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Log.d(TAG, "onDataChange: ");
                            Kullanici kullanici = dataSnapshot.getValue(Kullanici.class);
                            if (kullanici != null) {
                                // Kullancıı bilgileri doldurmuş ise Anasayfaya geçis yapilir.
                                Intent i = new Intent(WelcomeActivity.this, Anasayfa.class);
                                finish();
                                startActivity(i);
                            } else {
                                //kullanici profil bilgilerini doldurmamıs ise bilgilendirme sayfasina gecilir.
                                Intent i = new Intent(WelcomeActivity.this, Bilgilendirme.class);
                                finish();
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d(TAG, "onCancelled: ");
                        }
                    });
                }
                else {
                    //kullanıcı oturumunu kapatmıs veya daha önce hic hesap olusturmamıs ise giris sayfasına gecilir.
                    Log.d(TAG, "onAuthStateChanged: currentuser null");
                    Intent i = new Intent(WelcomeActivity.this, Giris.class);
                    finish();
                    startActivity(i);
                }
            }
        };
    }
}
