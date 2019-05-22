package com.example.patipati;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OnayKoduGir extends AppCompatActivity implements View.OnClickListener {

    EditText onayKoduGir;
    Button onayKoduButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_onay_kodu_gir);

        onayKoduGir = findViewById(R.id.onay_kodu);
        onayKoduButon = findViewById(R.id.button_onay_kodu);

        onayKoduButon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        final String onay_Kodu = onayKoduGir.getText().toString();

        if (onay_Kodu.equals("SSCD250116")){

            Bundle veri = getIntent().getExtras();
            String gelen = veri.getString("e_mail");

            Toast.makeText(OnayKoduGir.this, "Yönlendiriliyorsunuz!", Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            Intent s = new Intent(getApplicationContext(), SifremiGuncelle.class);

            bundle.putString("e_mail",gelen);
            s.putExtras(bundle);

            startActivity(s);

        }else{
            Toast.makeText(OnayKoduGir.this, "Onay Kodu Doğru Değil!", Toast.LENGTH_SHORT).show();
        }
    }
}
