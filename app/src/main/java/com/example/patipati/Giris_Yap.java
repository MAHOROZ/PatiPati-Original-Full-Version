package com.example.patipati;

import android.content.Intent;
import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Giris_Yap extends AppCompatActivity implements View.OnClickListener {

    EditText kullanici_Email, kullanici_sifre;
    Button giris_yap_buton;

    private kullanicilar kullanicilar;

    private DatabaseReference vt_referans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_giris_yap);

        kullanici_Email = (EditText)findViewById(R.id.giris_yap_e_mail);
        kullanici_sifre = (EditText)findViewById(R.id.giris_yap_sifre);
        giris_yap_buton = findViewById(R.id.button_giris_yap);



        vt_referans = FirebaseDatabase.getInstance().getReference().child("kullanicilar");

        giris_yap_buton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        final String k_Email = kullanici_Email.getText().toString();
        final String k_sifre = kullanici_sifre.getText().toString();

        vt_referans.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    kullanicilar k_bilgileri = snapshot.getValue(kullanicilar.class);
                    if (k_Email.equals(k_bilgileri.getEmail()) && k_sifre.equals(k_bilgileri.getSifre())){
                            Toast.makeText(Giris_Yap.this,"Giriş Başarılı!",Toast.LENGTH_SHORT).show();
                            Intent s = new Intent(getApplicationContext(),Giris_Ekrani.class);
                            startActivity(s);
                            break;
                    }else{
                        Toast.makeText(Giris_Yap.this,"Kullanıcı Bulunamadı!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
