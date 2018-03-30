package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.hasanozanal.sunshine.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import Data.DataService;
import Data.JSONHandler;
import Helpers.DBHelper;
import Model.Weather;
import Model.WeatherAdapter;

public class DetailsFragment extends Fragment {

    private MainFragment mainFragment;
    private WeatherAdapter weatherAdapter;
    private ArrayList<Weather> locations;
    private DataService dataService;
    private JSONObject jsonObject;
    private JSONHandler jsonHandler;
    private Weather weather;
    private DBHelper dbHelper;
    private int index = 0 ;

    private TextView maxTempDetailTxt;
    private TextView minTempDetailTxt;
    private TextView humidityValueTxt;
    private TextView rainValueTxt;
    private TextView windValueTxt;
    private ImageView todayDetailImg;
    private ImageView humdityImg;
    private ImageView rainImg;
    private ImageView windImg;

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

        maxTempDetailTxt = (TextView) view.findViewById(R.id.detailsMaxTempId);
        minTempDetailTxt = (TextView) view.findViewById(R.id.detailsMinTempId);
        humidityValueTxt = (TextView) view.findViewById(R.id.humidityValueId);
        windValueTxt = (TextView) view.findViewById(R.id.windValueId);
        rainValueTxt = (TextView) view.findViewById(R.id.rainValueId);

        dbHelper = new DBHelper(getActivity());
        dataService = new DataService();
        weather = new Weather();

        maxTempDetailTxt.setText(Integer.toString(weather.getMax_tempTxt()));


        return view;
    }

    public void details(){
        locations = dbHelper.getWeather();
        weatherAdapter = new WeatherAdapter(getActivity(),R.layout.location_row,locations);
        int position = weatherAdapter.getPosition(weather);

        String weatherUrl = dataService.getForecastWeatherData(locations.get(position).getLat(),locations.get(position).getLon());
        try {
            jsonObject = new JSONObject(weatherUrl);
            jsonHandler = new JSONHandler();
            weather = jsonHandler.JSONForecastHandler(jsonObject);
            locations.set(position,weather);

            updateUI(weather);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public void updateUI(Weather weather){
        maxTempDetailTxt.setText(Integer.toString(weather.getMax_tempTxt()));
        minTempDetailTxt.setText(Integer.toString(weather.getMin_tempTxt()));
    }
}
