package com.phong.hocgooglemapphan2;

import androidx.appcompat.app.AppCompatActivity;

import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity {

    private GoogleMap mMap;
    Spinner spinnerMapType;
    ArrayAdapter<String> adapterMapType;
    String []arrMapType = {"Normal","Satelline","Terrain","Hybrid","None"};

    //Khởi tạo biến lắng nghe vị trí hiện tại thay đổi:
    GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            //Lấy vĩ tuyến, kinh tuyến:
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            Marker marker = mMap.addMarker(new MarkerOptions().position(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {//Khởi tạo GoogleMap:
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                //Gán myLocationChangeListener cho GoogleMap:
                mMap.setMyLocationEnabled(true);
                mMap.setOnMyLocationChangeListener(myLocationChangeListener);
            }
        });
        addControls();
        addEvents();
    }

    private void addEvents() {
        spinnerMapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                xuLyThayDoiMap(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void xuLyThayDoiMap(int i) {
        switch (i){
            case 0:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 2:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case 3:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case 4:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
        }
    }

    private void addControls() {
        spinnerMapType = findViewById(R.id.spinnerMapType);
        adapterMapType = new ArrayAdapter<String>(
                MainActivity.this,
                android.R.layout.simple_spinner_item,
                arrMapType);
        adapterMapType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMapType.setAdapter(adapterMapType);
    }
}
