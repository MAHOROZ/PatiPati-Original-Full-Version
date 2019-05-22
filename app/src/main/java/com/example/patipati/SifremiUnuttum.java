package com.example.patipati;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;

public class SifremiUnuttum extends AppCompatActivity implements View.OnClickListener {

    EditText sifremi_unuttum_e_mail;
    Button kod_gonder_buton;

    String e_mail;

    Session session = null;
    ProgressDialog pdialog = null;
    Context context = null;
    String rec, subject, textMessage;

    private DatabaseReference vt_referans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_sifremi_unuttum);

        sifremi_unuttum_e_mail = findViewById(R.id.sifremi_unuttum_e_mail);
        kod_gonder_buton = findViewById(R.id.kod_gonder_button);

        vt_referans = FirebaseDatabase.getInstance().getReference().child("kullanicilar");

        kod_gonder_buton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        e_mail = sifremi_unuttum_e_mail.getText().toString();

        vt_referans.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    kullanicilar k_bilgileri = snapshot.getValue(kullanicilar.class);
                    if (e_mail.equals(k_bilgileri.getEmail())){
                        e_mail_gonder(e_mail);
                        break;
                    }
                    else{
                        Log.v("Kullanıcı Bulunamadı.","Mail yok.");
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void e_mail_gonder(String e_mail){

        context = this;

        rec = e_mail;
        subject = "PatiPati Onay Kodu";
        textMessage = "SSCD250116";


        Properties props = new Properties();
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port","465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.port","465");

        session = Session.getDefaultInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("malperenhoroz@gmail.com","deneme123");
            }
        });

        pdialog = ProgressDialog.show(context,"","Onay kodu gönderiliyor..",true);

        RetreiveFeedTask task = new RetreiveFeedTask();
        task.execute();
    }
    class RetreiveFeedTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params){
            try{
                Message message = new javax.mail.internet.MimeMessage(session);
                message.setFrom(new javax.mail.internet.InternetAddress("burakhanalagoz@gmail.com"));
                message.setRecipients(Message.RecipientType.TO, javax.mail.internet.InternetAddress.parse(rec));
                message.setSubject(subject);
                message.setContent(textMessage,"text/html; charset=utf-8");
                Transport.send(message);
            }
            catch (MessagingException e){
                e.printStackTrace();
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String result){

            pdialog.dismiss();
            Toast.makeText(getApplicationContext(),"Mail Gönderildi",Toast.LENGTH_LONG).show();

            Bundle bundle = new Bundle();

            Intent s = new Intent(getApplicationContext(), OnayKoduGir.class);

            bundle.putString("e_mail",e_mail);
            s.putExtras(bundle);

            startActivity(s);
        }
    }
}