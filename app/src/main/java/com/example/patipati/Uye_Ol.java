package com.example.patipati;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Uye_Ol extends AppCompatActivity {

    private kullanicilar kullanicilar;
    EditText kullanici_ad, kullanici_soyad, kullanici_Email, kullanici_sifre;
    Button uye_ol_buton;

    FirebaseDatabase veri_tabani;
    DatabaseReference vt_referans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_uye_ol);

        kullanici_ad = (EditText)findViewById(R.id.uye_ol_ad);
        kullanici_soyad = (EditText)findViewById(R.id.uye_ol_soyad);
        kullanici_Email = (EditText)findViewById(R.id.uye_ol_e_mail);
        kullanici_sifre = (EditText)findViewById(R.id.uye_ol_sifre);

        uye_ol_buton = findViewById(R.id.button_uye_ol);

        kullanicilar = new kullanicilar();
        veri_tabani = FirebaseDatabase.getInstance();
        vt_referans = veri_tabani.getReference().child("kullanicilar");

        uye_ol_buton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                kullanicilar.setAd(kullanici_ad.getText().toString());
                kullanicilar.setSoyad(kullanici_soyad.getText().toString());
                kullanicilar.setEmail(kullanici_Email.getText().toString());
                kullanicilar.setSifre(kullanici_sifre.getText().toString());

                vt_referans.push().setValue(kullanicilar);

                Intent i = new Intent(Uye_Ol.this, Giris_Ekrani.class);
                startActivity(i);

                finish();
            }
        });
    }


}