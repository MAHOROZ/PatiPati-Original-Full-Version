package com.example.patipati;

import android.content.Intent;
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

public class SifremiGuncelle extends AppCompatActivity implements View.OnClickListener {

    EditText sifre1,sifre2;
    Button buton_sifre_guncelle;

    DatabaseReference vt_referans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sifremi_guncelle);

        sifre1 = findViewById(R.id.sifremi_guncelle_1);
        sifre2 = findViewById(R.id.sifremi_guncelle_2);

        buton_sifre_guncelle = findViewById(R.id.button_sifre_guncelle);

        vt_referans = FirebaseDatabase.getInstance().getReference().child("kullanicilar");
        buton_sifre_guncelle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        final String[] ID = new String[1];

        Bundle veri = getIntent().getExtras();
        String e_mail = veri.getString("e_mail");

        final String yeni_sifre = sifre1.getText().toString();
        String yeni_sifre_tekrar = sifre2.getText().toString();

        vt_referans.orderByChild("email").equalTo(e_mail).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    ID[0] = child.getKey();
                    vt_referans.child(ID[0]).child("sifre").setValue(yeni_sifre);
                    Toast.makeText(SifremiGuncelle.this,"Şifre Başarıyla Değiştirildi.",Toast.LENGTH_LONG).show();
                    Intent s = new Intent(SifremiGuncelle.this, Giris_Yap.class);
                    startActivity(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
