package Fragments;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.hasanozanal.sunshine.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import Data.DataService;
import Data.JSONHandler;
import Helpers.DBHelper;
import Model.Weather;

public class MapViewFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private MarkerOptions userMarker;
    private Weather weather = new Weather();
    private DBHelper dbHelper = new DBHelper(getActivity());
    private DataService dataService;
    private JSONObject jsonObject;
    private  JSONHandler jsonHandler;

    public MapViewFragment() {
    }
    public static MapViewFragment newInstance(String param1, String param2) {
        MapViewFragment fragment = new MapViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map_view, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMapLongClickListener(this);
    }

    public void setUserMarker(Location location) {
        LatLng userLocation = new LatLng(location.getLatitude(),location.getLongitude());

        if (userMarker == null){
            userMarker = new MarkerOptions().position(userLocation).title("Current Location");
            mMap.addMarker(userMarker);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,45));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (mMap != null){
            getLatAndLon(latLng);
        }
    }
    private void getLatAndLon(final LatLng latLng) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to add this location?");
        builder.setCancelable(true);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Double lat = latLng.latitude;
                Double lon = latLng.longitude;
                dbHelper = new DBHelper(getActivity());
                dbHelper.addWeather(lat,lon);
                dbHelper.close();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
