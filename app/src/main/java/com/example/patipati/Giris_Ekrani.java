package com.example.patipati;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Giris_Ekrani extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//will hide the title
        getSupportActionBar().hide();
        setContentView(R.layout.activity_giris_ekrani);

        Button giris_yap = (Button) findViewById(R.id.giris_ekrani_giris_yap);
        giris_yap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Giris_Ekrani.this, Giris_Yap.class);
                startActivity(i);
            }
        });
        Button uye_ol = (Button) findViewById(R.id.giris_ekrani_uye_ol);
        uye_ol.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Giris_Ekrani.this, Uye_Ol.class);
                startActivity(i);
            }
        });
    }
}
