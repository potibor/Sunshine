package com.hasanozanal.sunshine.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.hasanozanal.sunshine.R;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import com.hasanozanal.sunshine.Data.DataService;
import com.hasanozanal.sunshine.Data.JSONHandler;
import com.hasanozanal.sunshine.Helpers.DBHelper;
import com.hasanozanal.sunshine.Model.Weather;
import com.hasanozanal.sunshine.Adapters.WeatherRecyclerAdapter;

public class DetailsFragment extends Fragment {

    private MainFragment mainFragment;
    private WeatherRecyclerAdapter adapter;
    private ArrayList<Weather> locations;
    private DataService dataService;
    private JSONObject jsonObject;
    private JSONHandler jsonHandler;
    private Weather weather;
    private DBHelper dbHelper;
    private Bundle bundle;
    private static String DEGREE_ICON = "\u00b0";

    private TextView maxTempDetailTxt;
    private TextView minTempDetailTxt;
    private TextView humidityValueTxt;
    private TextView city_nameTxt;
    private TextView current_temp;
    private TextView rainValueTxt;
    private TextView windValueTxt;
    private ImageView todayDetailImg;
    private ImageButton button;
    private RecyclerView recyclerView;
    private String unit;

    public DetailsFragment() {
    }
    public static DetailsFragment newInstance(String param1, String param2) {
        DetailsFragment fragment = new DetailsFragment();
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        setFields(view);
        Preferences();

        // region ButtonClick
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainFragment = new MainFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_containerId,mainFragment);
                transaction.commit();
            }
        });

        // endregion

        initializeClass();
        fetchWeatherData();
        fetchForecastData();
        setAdapter();

        return view;
    }

    public void initializeClass(){
        dbHelper = new DBHelper(getActivity());
        dataService = new DataService();
        bundle = this.getArguments();
    }
    public void updateView(Weather weather){
        current_temp.setText(Integer.toString(weather.getCurrent_temp())+DEGREE_ICON);
        maxTempDetailTxt.setText(Integer.toString(weather.getMax_tempTxt())+DEGREE_ICON);
        minTempDetailTxt.setText(Integer.toString(weather.getMin_tempTxt())+DEGREE_ICON);
        humidityValueTxt.setText(weather.getHumidity());
        windValueTxt.setText(weather.getWindSpeedTxt());
        city_nameTxt.setText(weather.getCity_nameTxt());

        Bitmap imageIcon = dataService.getImageData(weather.getWeatherIcon());
        if (imageIcon != null){
            todayDetailImg.setImageBitmap(imageIcon);
        }
    }
    public void fetchWeatherData(){
        SharedPreferences preferences = getActivity().getSharedPreferences("switch", Context.MODE_PRIVATE);
        String unit;
        if (preferences.getBoolean("Celcius",true)){
            unit = "&units=metric";
        }else {
            unit = "&units=imperial";
        }
        String weatherUrl = dataService.getCurrentWeatherData(bundle.getDouble("lat"),bundle.getDouble("lon"),unit);
        try {
            jsonObject = new JSONObject(weatherUrl);
            jsonHandler = new JSONHandler();
            weather = jsonHandler.JSONWeatherHandler(jsonObject);
            updateView(weather);
        }  catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void fetchForecastData(){
        String forecastUrl = dataService.getForecastWeatherData(bundle.getDouble("lat"),bundle.getDouble("lon"),unit);

        try {
            jsonObject = new JSONObject(forecastUrl);
            jsonHandler = new JSONHandler();
            locations = jsonHandler.JSONForecastHandler(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void setAdapter(){
        adapter = new WeatherRecyclerAdapter(locations);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        adapter.notifyDataSetChanged();

    }
    public void setFields(View view){
        city_nameTxt = (TextView) view.findViewById(R.id.cityNameDetailId);
        maxTempDetailTxt = (TextView) view.findViewById(R.id.detailsMaxTempId);
        minTempDetailTxt = (TextView) view.findViewById(R.id.detailsMinTempId);
        humidityValueTxt = (TextView) view.findViewById(R.id.humidityValueId);
        windValueTxt = (TextView) view.findViewById(R.id.windValueId);
        rainValueTxt = (TextView) view.findViewById(R.id.rainValueId);
        current_temp = (TextView) view.findViewById(R.id.currentTempDetailId);
        button = (ImageButton) view.findViewById(R.id.BackToMainBtnId);
        todayDetailImg = (ImageView) view.findViewById(R.id.detailsImgId);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewId);
    }
    public void Preferences(){
        SharedPreferences preferences = getActivity().getSharedPreferences("switch",0);
        if (preferences.getBoolean("Celcius",true)){
            unit = "&units=metric";
        }else{
            unit = "&units=imperial";
        }
    }
}
