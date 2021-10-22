package com.example.login;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.util.Log;
import android.widget.Toast;
import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


public class Profiel extends AppCompatActivity {

    String IDenfant ;
    Button test1, sortie, arret;
    TextView nom, prenom, age, pere;
    private static final String TAG = "MainActivity";


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiel);
        nom = findViewById(R.id.idnom);
        prenom = findViewById(R.id.prenomid);
        age = findViewById(R.id.ageid);
        pere = findViewById(R.id.pereid);
        test1=findViewById(R.id.b);
        arret=findViewById(R.id.w);
        Intent intent = getIntent();
        String nomfils = intent.getStringExtra("nom");
        String prenomfils = intent.getStringExtra("prenom");
        String age1 = intent.getStringExtra("age");
        IDenfant = intent.getStringExtra("id");
        String nopere = intent.getStringExtra("nompere");
        String pre = intent.getStringExtra("prenompere");
        nom.setText(nomfils);
        prenom.setText(prenomfils);
        age.setText(age1);
        pere.setText(nopere + " " + pre);



    }
    ProgressDialog dialog;
    Context context;

    public void getlocation(View view) {
        context = this;
        String bol= view.getTag().toString();

        LocationManager locaMa = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationListener list1=new locationListener(this,IDenfant);
        if(bol.contentEquals("0")){
            Toast.makeText(Profiel.this,"Le processus d'envoi de points de localisation a commencé" ,Toast.LENGTH_SHORT).show();

            locaMa.requestLocationUpdates(locaMa.GPS_PROVIDER,50,50, list1);
            arret.setBackground(this.getResources().getDrawable(R.drawable.buttonstylewithgradient));
            test1.setBackgroundColor(Color.GREEN);


        }else{
            locaMa.removeUpdates(list1);
            list1 = null;
            Toast.makeText(Profiel.this, "La détermination de la localisation a été désactivée" ,Toast.LENGTH_SHORT).show();

            test1.setBackground(this.getResources().getDrawable(R.drawable.buttonstylewithgradient));
            arret.setBackgroundColor(Color.RED);

        }


    }

    static String result = "";

    public void finalee(View view) {
        finish();
    }

    static class inserttask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            InputStream isr = null;

            try{
                String URL=params[0];
                URL url = new URL( URL);
                URLConnection urlConnection = url.openConnection();
                isr  = new BufferedInputStream(urlConnection.getInputStream());



            }

            catch(Exception e){

                Log.e("log_tag", "Error in http connection " + e.toString());



            }

//convert response to string

            try{

                BufferedReader reader = new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);

                StringBuilder sb = new StringBuilder();

                String line = null;

                while ((line = reader.readLine()) != null) {

                    sb.append(line + "\n");

                }

                isr.close();

                result=sb.toString();

            }

            catch(Exception e){

                Log.e("log_tag", "Error  converting result " + e.toString());

            }

//parse json data


            return null;
        }







    }
}

