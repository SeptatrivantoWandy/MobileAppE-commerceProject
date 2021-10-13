package com.example.mcsprojectakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    public static final String SEND_ID = "com.example.mcsprojectakhir.SEND_ID";
    public static final String SEND_PRODUCT_ID = "com.example.mcsprojectakhir.SEND_PRODUCT_ID";

    int userId;
    int productId;

    private GoogleMap mapForm;

    private TextView longitudeMap;
    private TextView latitudeMap;

    String strLongitude;
    String strLatitude;
    String productName;

    Double dLongitude;
    Double dLatitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_maps);

        longitudeMap = findViewById(R.id.textViewLongitudeMapDisplay);
        latitudeMap = findViewById(R.id.textViewLatitudeMapDisplay);

        Intent intent = getIntent();
        userId = intent.getIntExtra(SEND_ID, -1);
        productId = intent.getIntExtra(SEND_PRODUCT_ID, -1);

        strLongitude = StoreFormRecyclerView.PDV.get(productId-1).getLongitude();
        strLatitude = StoreFormRecyclerView.PDV.get(productId-1).getLatitude();
        productName = StoreFormRecyclerView.PDV.get(productId-1).getProductName();

        longitudeMap.setText(strLongitude);
        latitudeMap.setText(strLatitude);

        dLongitude = Double.parseDouble(strLongitude);
        dLatitude = Double.parseDouble(strLatitude);

        SupportMapFragment fragment = SupportMapFragment.newInstance();
        fragment.getMapAsync(this);
        getSupportFragmentManager().beginTransaction().add(R.id.frame_mapform, fragment).commit();



    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapForm = googleMap;

        LatLng GeoPos = new LatLng(dLatitude, dLongitude);
        mapForm.addMarker(new MarkerOptions().position(GeoPos).title(productName)).showInfoWindow();
        mapForm.moveCamera(CameraUpdateFactory.newLatLngZoom(GeoPos, 10));

    }
}