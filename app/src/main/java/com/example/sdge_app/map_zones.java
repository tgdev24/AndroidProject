package com.example.sdge_app;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class map_zones extends AppCompatActivity implements OnMapReadyCallback{

    GoogleMap m_map;
    boolean mapReady = false;
    private MarkerOptions options = new MarkerOptions();
    private ArrayList<LatLng> latLngs = new ArrayList<>();
    private ArrayList<String []> lines = new ArrayList<>();
    private Geocoder geocoder;
    private static final String[] permissions={
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.INTERNET,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_NETWORK_STATE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    private static final int INITIAL_REQUEST=1337;

    //added this for animated camera one
    static final CameraPosition SD = CameraPosition.builder()
            .target(new LatLng(32.715736, -117.161087))
            .zoom(10)
            .bearing(0)
            .tilt(45)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_zones);


        Button btnMap = (Button) findViewById(R.id.btnMap);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady) {
                    m_map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }
            }
        });

        Button btnSatellite = (Button) findViewById(R.id.btnSat);
        btnSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
            }
        });

        Button btnHybrid = (Button) findViewById(R.id.btnHybrid);
        btnHybrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady)
                    m_map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
            }
        });
//
        if(geocoder.isPresent()){
            geocoder = new Geocoder(this, Locale.getDefault());
        }
        else{
            Log.i("map_zones", "");
        }
        //changed this 2 lines to following
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
//        SupportMapFragment mapFragment = (SupportMapFragment) this.get.findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mapReady = true;
        m_map = map;
        flyTo(SD);
//        LatLng LA = new LatLng(34.0522, -118.2437);
//        m_map.addMarker(new MarkerOptions()
//                .position(LA)
//                .title("LA")
//                .snippet("Turn down for WHAT???????")
//                .icon(BitmapDescriptorFactory.defaultMarker()));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, 1337);
        }
        m_map.setMyLocationEnabled(true);
//        Log.v("map_zones", "Markers????????????????");
        //add all markers here
        addMarkers(latLngs);
//        CameraPosition target = CameraPosition.builder().target(LA).zoom(14).build();
//        m_map.moveCamera(CameraUpdateFactory.newCameraPosition(target));
//        m_map.addMarker(one);
//        m_map.addMarker(two);
//        m_map.addMarker(three);
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
    }

    public void onSearch(View view){
        AutoCompleteTextView locationtf = (AutoCompleteTextView) findViewById(R.id.addressEntered);
        String location = locationtf.getText().toString();
        List<Address> addressList = null;
        if(location != null || !location.equals("")){
//            Log.v("map_zones", "UUUUUUUUUUUUUUUUUUUU");
            Address address = null;
            try {
                addressList = geocoder.getFromLocationName(location, 1);
//                Log.v("map_zones", "ZZZZZZZZZZZZZZZZ");
                if (addressList.size()==0) {
                    return;
                }
                if (addressList.size()>0) {
//                    Log.v("map_zones", "QQQQQQQQQQQQ");
                    address = addressList.get(0);
//                    Log.v("map_zones", "WWWWWWWWWWWW");
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }
            if(address != null) {
//                Log.v("map_zones", "EEEEEEEEEEEE");
                String address1 = address.getAddressLine(0) + "," + "\u00A0";
//                Log.v("map_zones", "RRRRRRRRRRRR");
                address1 += address.getLocality() + "," + "\u00A0";
                address1 += address.getAdminArea() + "," + "\u00A0";
                address1 += address.getCountryCode() + "," + "\u00A0";
                address1 += address.getPostalCode();
                LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
//                Log.v("map_zones", "TTTTTTTTTTT");
                m_map.addMarker(new MarkerOptions().position(latLng).title(address1).snippet(""));
//                Log.v("map_zones", "YYYYYYYYYYYYYYY");
                m_map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15), 3000, null);
            }
        }
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        int cool = 0;
    }
    private void flyTo(CameraPosition target){
        m_map.animateCamera(CameraUpdateFactory.newCameraPosition(target), 10000, null);
    }

    //added to fix delay in too much work on main thread, skipping frames problem
//    @Override
//    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
//        super.onViewCreated(view, savedInstanceState);
//        m_map = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        m_map.getMapAsync(this);
//    }

    private void addMarkers(ArrayList<LatLng> latLngs){
//        Log.i("MainActivity", "HERE");
        BufferedReader reader = null;
        BufferedReader reader1 = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("convertcsv.txt")));
            reader1 = new BufferedReader(new InputStreamReader(getAssets().open("latslongs.txt")));
            String line;
            //skip headers in files
            reader.readLine();
            reader1.readLine();

            //get all contents
            while((line = reader.readLine()) != null){
//                Log.v("map_zones", line);
                String [] cols = line.split(",");
                lines.add(cols);
            }

            //gettting locations
            while((line = reader1.readLine()) != null){
                String [] cols1 = line.split("\t");
                latLngs.add(new LatLng(Double.parseDouble(cols1[0]), Double.parseDouble(cols1[1])));
            }

            // resizing the icon
            Resources r = getResources();
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, r.getDisplayMetrics());
            int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 35, r.getDisplayMetrics());
//            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.snowflake);
            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.snowflake);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

            // adding marker locations to the map
            for (int i = 0; i < lines.size(); i++) {
                options.position(latLngs.get(i));
                options.title(lines.get(i)[0]);
                options.snippet(lines.get(i)[1] + "<br>" + "<b>Contact Phone Number: " + "</b>" + lines.get(i)[2] + "<br>" + "<b>Hours: " + "</b>" + lines.get(i)[4]);
                options.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
                m_map.addMarker(options);
//                m_map.addMarker(new MarkerOptions().position(latLngs.get(i)).title(lines.get(i)[0]).snippet(lines.get(i)[1] + "<br>" + "<b>Contact Phone Number: " + "</b>" + lines.get(i)[2] + "<br>" + "<b>Hours: " + "</b>" + lines.get(i)[4]).icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
            }
            m_map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter(){
                @Override
                public View getInfoWindow(Marker arg0){
                    return null;
                }

                @Override
                public View getInfoContents(Marker marker){
                    Context context = getApplicationContext();
                    LinearLayout info = new LinearLayout(context);
                    info.setOrientation(LinearLayout.VERTICAL);

                    TextView title = new TextView(context);
                    title.setTextColor(Color.parseColor("#0085B2"));
                    title.setGravity(Gravity.CENTER);
                    title.setTypeface(null, Typeface.BOLD);
                    title.setText(marker.getTitle());

                    TextView snippet = new TextView(context);
                    snippet.setTextColor(Color.BLACK);
                    snippet.setText(Html.fromHtml(marker.getSnippet()));

                    info.addView(title);
                    info.addView(snippet);
                    return info;
                }
            });
        } catch (IOException e) {

        } finally{
            if(reader != null){
                try{
                    reader.close();
                } catch(IOException e){

                }
            }
            if(reader1 != null) {
                try {
                    reader1.close();
                } catch (IOException e) {

                }
            }
        }
    }
}
