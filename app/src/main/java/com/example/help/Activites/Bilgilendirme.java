package com.example.help.Activites;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import com.example.help.R;
import com.google.firebase.auth.FirebaseAuth;

public class Bilgilendirme extends AppCompatActivity
{

    private Button btnProfil, btnBilgi,btnCikis;
    private LinearLayout bilgiLayout1;

    public void init()
    {
        btnProfil = findViewById(R.id.btnProfil);
        btnBilgi = findViewById(R.id.btnBilgi);
        btnCikis = findViewById(R.id.btnCikis);
        bilgiLayout1 = findViewById(R.id.bilgiLayout1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bilgilendirme);
        init();
        //Bilgilendirme butonuna tıklanıldığında içeriğin görünür olması sağlandı
        btnBilgi.setOnClickListener(new View.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(View v)
                                        {
                                            if (bilgiLayout1.getVisibility() == View.GONE) {
                                                bilgiLayout1.setVisibility(View.VISIBLE);
                                                btnBilgi.setAlpha((float) 0.5);


                                            } else {
                                                bilgiLayout1.setVisibility(View.GONE);
                                                btnBilgi.setAlpha(1);

                                            }

                                        }
                                    }
        );

        btnCikis.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent i = new Intent(Bilgilendirme.this, Giris.class);
                FirebaseAuth.getInstance().signOut();
                startActivity(i);
            }

        });


        btnProfil.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent i = new Intent(Bilgilendirme.this, Kayit_tamamla.class);
                startActivity(i);

            }

        });


    }

}
