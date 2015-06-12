package com.holamundo.hoguer.localizacion;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private GoogleMap mMap;
    private Button Actualizar, Desactivar;
    private TextView Latitud, Longitud, Precision, EstadoProveedor;


    private LocationManager locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    private LocationListener locListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Actualizar = (Button) findViewById(R.id.BtnActualizar);
        Desactivar = (Button) findViewById(R.id.BtnDesactivar);
        Latitud = (TextView) findViewById(R.id.LblPosLatitud);
        Longitud = (TextView) findViewById(R.id.LblPosLongitud);
        Precision = (TextView) findViewById(R.id.LblPosPrecision);
        EstadoProveedor = (TextView) findViewById(R.id.LblEstado);

        Actualizar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                comenzarLocalizacion();
            }

        });

        Desactivar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                locManager.removeUpdates(locListener);
            }
        });
    }
    private void comenzarLocalizacion() {
        Location loc =
                locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        mostrarPosicion(loc);
        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                mostrarPosicion(location);
            }

            public void onProviderDisabled(String provider) {
                EstadoProveedor.setText("apagado");
            }


            public void onProviderEnabled(String provider) {
                EstadoProveedor.setText("Encendido ");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                EstadoProveedor.setText("Provider Status: " + status);
            }
        };

        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }
    public Object getSystemService(String locationService) {
        return null;
    }

    private void mostrarPosicion(Location loc) {
        if (loc != null) {
            Latitud.setText("Latitud: " + String.valueOf(loc.getLatitude()));
            Longitud.setText("Longitud: " + String.valueOf(loc.getLongitude()));
            Precision.setText("Precision: " + String.valueOf(loc.getAccuracy()));
            Log.i("", String.valueOf(loc.getLatitude() + " - " + String.valueOf(loc.getLongitude())));
        } else {
            Latitud.setText("Latitud: (sin_datos)");
            Longitud.setText("Longitud: (sin_datos)");
            Precision.setText("Precision: (sin_datos)");
        }
    }

}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}


