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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Uye_Ol extends AppCompatActivity {
    FirebaseAuth auth;
    GoogleSignInClient mGoogleSignInClient;
    SignInButton signInButton;

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

        kullanici_ad = (EditText) findViewById(R.id.uye_ol_ad);
        kullanici_soyad = (EditText) findViewById(R.id.uye_ol_soyad);
        kullanici_Email = (EditText) findViewById(R.id.uye_ol_e_mail);
        kullanici_sifre = (EditText) findViewById(R.id.uye_ol_sifre);

        uye_ol_buton = findViewById(R.id.button_uye_ol);

        kullanicilar = new kullanicilar();
        veri_tabani = FirebaseDatabase.getInstance();
        vt_referans = veri_tabani.getReference().child("kullanicilar");

        uye_ol_buton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                kullanicilar.setAd(kullanici_ad.getText().toString());
                kullanicilar.setSoyad(kullanici_soyad.getText().toString());
                kullanicilar.setEmail(kullanici_Email.getText().toString());
                kullanicilar.setSifre(kullanici_sifre.getText().toString());

                vt_referans.push().setValue(kullanicilar);

                Intent i = new Intent(Uye_Ol.this, Giris_Yap.class);
                startActivity(i);

                finish();
            }
        });

        auth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton = (SignInButton) findViewById(R.id.button_google_giris_yap);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, 101);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Log.w("Giriş Başarısız.", e);
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = auth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "Giriş Başarılı", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), AnaSayfa.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Giriş Yapılamadı", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
