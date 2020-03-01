package com.example.weathertest.mvp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weathertest.R;

public class Main2Activity extends AppCompatActivity {
    TextView citysCity;
    ImageView citysLogo;
    TextView citysTemp;
    TextView citysDesc;
    TextView citysTemp2;
    TextView citysPres2;
    TextView citysHumid2;
    TextView citysWind2;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String city = intent.getStringExtra("city");
        String logo = intent.getStringExtra("logo");
        String temp = intent.getStringExtra("temp");
        String desc = intent.getStringExtra("desc");
        String temp2 = intent.getStringExtra("temp2");
        String humid = intent.getStringExtra("humid");
        String presure = intent.getStringExtra("presure");
        String wind = intent.getStringExtra("wind");
        citysCity = findViewById(R.id.citys_city);
        citysLogo = findViewById(R.id.citys_logo);
        citysTemp = findViewById(R.id.citys_temp);
        citysDesc = findViewById(R.id.citys_desc);
        citysTemp2 = findViewById(R.id.citys_temp2);
        citysPres2 = findViewById(R.id.citys_pres2);
        citysHumid2 = findViewById(R.id.citys_humid2);
        citysWind2 = findViewById(R.id.citys_wind2);
        citysCity.setText(city);
        citysPres2.setText(ConvertPres(presure)+" мм рт.ст.");
        citysHumid2.setText(humid+" %");
        citysWind2.setText(wind+" м/с");
        citysLogo.setImageResource(Main2Activity.this.getResources().getIdentifier(logo, "drawable", Main2Activity.this.getPackageName()));
        citysTemp.setText(temp);
        citysDesc.setText(UpperCase(desc));
        Log.d(TAG, "desc"+desc);
        citysTemp2.setText("Ощущается как: "+temp2);

    }
    public String UpperCase(String word){
        if(word == null || word.isEmpty()) return ""; //или return word;
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }
    public String ConvertPres(String word){
        double result = Double.parseDouble(word);
        result=(result/133)*100;
        return String.valueOf(Math.round(result));
    }
}
