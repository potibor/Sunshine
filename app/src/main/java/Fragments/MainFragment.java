package Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.hasanozanal.sunshine.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import Data.DataProvider;
import Data.DataService;
import Data.JSONHandler;
import Helpers.DBHelper;
import Model.Weather;
import Model.WeatherAdapter;

public class MainFragment extends Fragment implements LocationListener {

    private static String DEGREE_ICON = "\u00b0";
    private int index = 0;

    private Location mLocation;
    private LocationManager mLocationManager;
    private FusedLocationProviderClient mLocationClient;
    private LocationCallback mLocationCallBack;
    private LocationRequest mLocationRequest;

    private TextView currentDateTxt;
    private TextView maxTempTxt;
    private TextView minTempTxt;
    private TextView cityNameTxt;
    private TextView descriptionTxt;
    private ImageView imageView;
    private ImageButton addLocationBtn;
    private ListView weatherList;

    private Weather weather;
    private DBHelper dbHelper;
    private JSONHandler jsonHandler;
    private DataService dataService;
    private JSONObject jsonObject;
    private MapViewFragment mapViewFragment;
    private ArrayList<Weather> locations;
    private WeatherAdapter weatherAdapter;

    public MainFragment() {
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        addLocationBtn = (ImageButton) view.findViewById(R.id.addLocationBtnId);
        currentDateTxt = (TextView) view.findViewById(R.id.currentDateId);
        maxTempTxt = (TextView) view.findViewById(R.id.max_tempTextId);
        minTempTxt = (TextView) view.findViewById(R.id.min_tempTxtId);
        cityNameTxt = (TextView) view.findViewById(R.id.city_nameTxtId);
        descriptionTxt = (TextView) view.findViewById(R.id.descriptionTxtId);
        imageView =(ImageView) view.findViewById(R.id.currentWeatherImgId);
        weatherList = (ListView) view.findViewById(R.id.listviewId);


        dbHelper = new DBHelper(getActivity());
        dataService = new DataService();

        setLocationChangeAction();
        getLastKnownLocation();

        locations = dbHelper.getWeather();
        weatherAdapter = new WeatherAdapter(getActivity(),R.layout.location_row,locations);
        weatherList.setAdapter(weatherAdapter);
        index = 0;
        request();

        getCurrentLocation();

        weatherList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                deleteListItem(position);
                weatherAdapter.notifyDataSetChanged();
                return true;
            }
        });
        weatherAdapter.notifyDataSetChanged();
        weatherList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // weather = weatherAdapter.getItem(position);
                DetailsFragment detailsFragment = new DetailsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_containerId,detailsFragment).addToBackStack(null);
                transaction.commit();
            }
        });

        addLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapViewFragment mapViewFragment = new MapViewFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_containerId, mapViewFragment).addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    @Override
    public void onLocationChanged(Location location) {
        mapViewFragment.setUserMarker(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                getCurrentLocation();
                break;
            default:
                break;
        }
    }

    public void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET}, 10);
        } else {
            mLocationRequest = LocationRequest.create();
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            mLocationRequest.setSmallestDisplacement(100);
            mLocationRequest.setInterval(1000);
            mLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallBack, null);
        }
    }

    public void getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET}, 10);

        } else mLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    public void setLocationChangeAction() {
        mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        mLocationClient = LocationServices.getFusedLocationProviderClient(getActivity());
        mLocationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                mLocation = locationResult.getLastLocation();
                String weatherUrl = dataService.getCurrentWeatherData(mLocation.getLatitude(),mLocation.getLongitude());
                try {
                    jsonObject = new JSONObject(weatherUrl);
                    jsonHandler = new JSONHandler();
                    weather = jsonHandler.JSONWeatherHandler(jsonObject);
                    updateUI(weather);
                }  catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void request(){
        if(locations.size() == 0)
            return;
        String weatherURl = dataService.getCurrentWeatherData(locations.get(index).getLat(),locations.get(index).getLon());
        try {
            jsonObject = new JSONObject(weatherURl);
            jsonHandler = new JSONHandler();
            weather = jsonHandler.JSONWeatherHandler(jsonObject);
            locations.set(index, weather);
            index ++;
            if(index < locations.size())
                request();
            else
                weatherAdapter.notifyDataSetChanged();

        }  catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateUI(Weather weather){
        cityNameTxt.setText(weather.getCity_nameTxt() + "," + weather.getCountry_nameTxt());
        maxTempTxt.setText(Integer.toString(weather.getMax_tempTxt()) + DEGREE_ICON);
        minTempTxt.setText(Integer.toString(weather.getMin_tempTxt())+ DEGREE_ICON);
        descriptionTxt.setText(weather.getDescriptionTxt());
    }
    public void deleteListItem(final int position){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do you want to delete?");
        builder.setCancelable(true);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setMessage("Are you sure?");
                builder1.setCancelable(true);
                builder1.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int mId = locations.get(position).getItemId();
                        Weather item = locations.get(position);
                        weatherAdapter.remove(item);
                        dbHelper.deleteItem(mId);
                        weatherAdapter.notifyDataSetChanged();
                        dbHelper.close();
                        dialog.dismiss();
                    }
                });
                builder1.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder1.create();
                alertDialog.show();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
