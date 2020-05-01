package com.example.help.Activites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
//import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.help.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Kayit extends AppCompatActivity {

    private EditText etPassWord, etUserName, etEmail;
    private Button btnNewUser;
    FirebaseDatabase db;
    FirebaseAuth fAuth;
    String user, password,email;

    private void init()
    {
        db= FirebaseDatabase.getInstance();
        btnNewUser=findViewById(R.id.btnNewUser);
        etEmail=findViewById(R.id.email);
        etPassWord=findViewById(R.id.password);
        etUserName=findViewById(R.id.userName);
        fAuth=FirebaseAuth.getInstance();


    }

    private void kullanici_kaydet(String username,String email, String password)
    {
        DatabaseReference dbRef=db.getReference("Kullanicilar");
        String uid = fAuth.getUid();
//        String key= dbRef.push().getKey();
        dbRef.child(uid).setValue(new Kullanici(username,email,password));
//        DatabaseReference dbRefYeni=db.getReference("Kullanicilar/"+key);
//        dbRefYeni.setValue(new Kullanici(username,email,password));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {//bundle aktiviteler arası geçişi sağlar
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kayit);
        init();
        if(fAuth.getCurrentUser()!=null)
        {
            Intent i = new Intent(Kayit.this, Bilgilendirme.class);
            startActivity(i);
            finish();
        }
        btnNewUser.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                user= etUserName.getText().toString().trim();
                email= etEmail.getText().toString().trim();
                password= etPassWord.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    etEmail.setError("Bu alan boş bırakılamaz.");
                    return;
                }
                if(TextUtils.isEmpty(user)){
                    etUserName.setError("Bu alan boş bırakılamaz.");
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    etPassWord.setError("Bu alan boş bırakılamaz.");
                    return;
                }
                if(password.length() < 6) {
                    etPassWord.setError("6 karakterden küçük şifre oluşturulamaz!");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Kayit.this, "kullanıcı oluşturuldu",Toast.LENGTH_SHORT).show();
                            kullanici_kaydet(user,email,password);
                            Intent i = new Intent(Kayit.this, Bilgilendirme.class);
                            startActivity(i);

                        }
                        else
                            {
                            Toast.makeText(Kayit.this, "kullanıcı oluşturulamadı"+ task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                    }
                });
            }
        });
    }}
