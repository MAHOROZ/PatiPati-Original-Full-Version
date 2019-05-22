package com.example.patipati;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static android.widget.AdapterView.*;

public class IlanEkle extends AppCompatActivity{

    private Ilanlar ilanlar;
    Button buton_ilan_ekle;
    EditText ilan_basligi, ilan_aciklamasi;
    private Spinner spinnerkategoriler;
    private Spinner spinnerturler;
    private Spinner spinnercinsiyet;
    private Spinner spinneryas;

    private ArrayAdapter<String> dataAdapterForkategoriler;
    private ArrayAdapter<String> dataAdapterForturler;
    private ArrayAdapter<String> dataAdapterForcinsiyet;
    private ArrayAdapter<String> dataAdapterForyas;

    String kategori_adi,tur_adi,cinsiyeti,yasi;
    FirebaseDatabase veri_tabani;
    DatabaseReference vt_referans;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ilan_ekle);

        ilan_basligi = (EditText)findViewById(R.id.ilan_basligi);
        ilan_aciklamasi = (EditText)findViewById(R.id.ilan_aciklamasi);
        buton_ilan_ekle= (Button)findViewById(R.id.button_ilan_ekleme);
        spinnerkategoriler = (Spinner) findViewById(R.id.spinner_kategoriler);
        spinnerturler = (Spinner) findViewById(R.id.spinner_turler);
        spinnercinsiyet = (Spinner) findViewById(R.id.spinner_cinsiyet);
        spinneryas = (Spinner) findViewById(R.id.spinner_yas);



        final List <String> kategoriler = new ArrayList <String>();
        kategoriler.add("Kedi");
        kategoriler.add("Köpek");

        final List <String> turler1 = new ArrayList <String>();
        turler1.add("Van");
        turler1.add("İran");
        turler1.add("Ankara");

        final List <String> turler2 = new ArrayList <String>();
        turler2.add("Kangal");
        turler2.add("Pitbul");
        turler2.add("Ankara");

        List <String> cinsiyet = new ArrayList <String>();
        cinsiyet.add("Dişi");
        cinsiyet.add("Erkek");

        List <String> yas = new ArrayList <String>();
        yas.add("0-6 ay");
        yas.add("6-12 Ay");
        yas.add("12+ Ay");


        dataAdapterForkategoriler = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, kategoriler);
        dataAdapterForturler = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, turler1);
        dataAdapterForcinsiyet = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item,cinsiyet);
        dataAdapterForyas= new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, yas);

        dataAdapterForkategoriler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForturler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForcinsiyet.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapterForyas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerkategoriler.setAdapter(dataAdapterForkategoriler);
        spinnerturler.setAdapter(dataAdapterForturler);
        spinnercinsiyet.setAdapter(dataAdapterForcinsiyet);
        spinneryas.setAdapter(dataAdapterForyas);



        spinnerkategoriler.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {

                kategori_adi = parent.getItemAtPosition(position).toString();
                Toast.makeText(IlanEkle.this,kategori_adi,Toast.LENGTH_LONG).show();

                if (parent.getSelectedItem().toString().equals(kategoriler.get(0)))
                    dataAdapterForkategoriler = new ArrayAdapter <String>(IlanEkle.this, android.R.layout.simple_spinner_item, turler1);
                else if(parent.getSelectedItem().toString().equals(kategoriler.get(1)))
                    dataAdapterForturler = new ArrayAdapter<String>(IlanEkle.this, android.R.layout.simple_spinner_item,turler2);

                dataAdapterForturler.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerturler.setAdapter(dataAdapterForturler);
            }

            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });
        spinnerturler.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {

                tur_adi = parent.getItemAtPosition(position).toString();
                Toast.makeText(IlanEkle.this,tur_adi,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });
        spinnercinsiyet.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {

                cinsiyeti = parent.getItemAtPosition(position).toString();
                Toast.makeText(IlanEkle.this,cinsiyeti,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });
        spinneryas.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView <?> parent, View view, int position, long id) {

                yasi = parent.getItemAtPosition(position).toString();
                Toast.makeText(IlanEkle.this,yasi,Toast.LENGTH_LONG).show();
            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });
        ilanlar = new Ilanlar();
        veri_tabani = FirebaseDatabase.getInstance();
        vt_referans = veri_tabani.getReference().child("ilanlar");
        buton_ilan_ekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ilanlar.setIlan_basligi(ilan_basligi.getText().toString());
                ilanlar.setIlan_aciklamasi(ilan_aciklamasi.getText().toString());
                ilanlar.setKategori(kategori_adi);
                ilanlar.setTur(tur_adi);
                ilanlar.setYas(yasi);
                ilanlar.setCinsiyet(cinsiyeti);
                vt_referans.push().setValue(ilanlar);
            }
        });
    }
}