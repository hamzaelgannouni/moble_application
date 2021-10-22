package com.example.login;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.TextView;

public class locationListener implements LocationListener {
    Context context;
    String id;




    public locationListener(Context context,String id){
        this.context=context;
        this.id=id;
    }

    @Override
    public void onLocationChanged(Location lastlocat) {
       // Toast.makeText(context, "Log:"+Double.toString(lastlocat.getLongitude())+"larg:"+Double.toString(lastlocat.getLatitude()),Toast.LENGTH_LONG).show();
        new Profiel.inserttask().execute("https://findmykids.000webhostapp.com/models/insertpoint.php?y="+Double.toString(lastlocat.getLongitude())+"&x="+Double.toString(lastlocat.getLatitude())+"&id="+this.id);


    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
     //   Toast.makeText(context, "lllllog:",Toast.LENGTH_LONG).show();
        //new Profiel.inserttask().execute("http://10.0.2.2/Projet/v4/models/insertpoint.php?x="+Double.toString(lastlocat.getLongitude())+"&y="+Double.toString(lastlocat.getLatitude()));


    }

    @Override
    public void onProviderEnabled(String provider) {
    //  Toast.makeText(context, "lllllog:",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onProviderDisabled(String provider) {
     //   Toast.makeText(context, "lllllog:",Toast.LENGTH_LONG).show();

    }
}
