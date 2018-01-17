package cg.com.orionwambert.labgps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {
    private LocationManager manager;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.text_or);
        manager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            connected();
        }
    }

    //connection au gps
    private void connected() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, this);
    }
    //deconnexion au gps
    private void logout() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        manager.removeUpdates(this);
    }
    //Affichage des coodoonée dans un Toast Lorsque les coodonnée gps change
    @Override
    public void onLocationChanged(Location location) {
        StringBuilder stringBuilder=new StringBuilder();
        stringBuilder.append("Latitute :"+location.getLatitude());
        stringBuilder.append("Longitude :"+location.getLongitude());
        textView.setText(stringBuilder);
        Toast.makeText(this,stringBuilder.toString(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
    //appel de la methode de connexion au lorsque le gps est activiée

    @Override
    public void onProviderEnabled(String provider) {
        if ("gps".equals(provider)){
            connected();
        }
    }
    //appel de la methode de deconnexion au lorsque le gps est desactivé
    @Override
    public void onProviderDisabled(String provider) {
        if ("gps".equals(provider)){
            logout();
        }
    }
}
