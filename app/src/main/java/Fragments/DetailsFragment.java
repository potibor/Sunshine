package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import Model.WeatherRecyclerAdapter;

public class DetailsFragment extends Fragment {

    private MainFragment mainFragment;
    private WeatherAdapter weatherAdapter;
    private WeatherRecyclerAdapter adapter;
    private ArrayList<Weather> locations;
    private DataService dataService;
    private JSONObject jsonObject;
    private JSONHandler jsonHandler;
    private Weather weather;
    private DBHelper dbHelper;
    private static String DEGREE_ICON = "\u00b0";

    private TextView maxTempDetailTxt;
    private TextView minTempDetailTxt;
    private TextView humidityValueTxt;
    private TextView city_nameTxt;
    private TextView current_temp;
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

        city_nameTxt = (TextView) view.findViewById(R.id.cityNameDetailId);
        maxTempDetailTxt = (TextView) view.findViewById(R.id.detailsMaxTempId);
        minTempDetailTxt = (TextView) view.findViewById(R.id.detailsMinTempId);
        humidityValueTxt = (TextView) view.findViewById(R.id.humidityValueId);
        windValueTxt = (TextView) view.findViewById(R.id.windValueId);
        rainValueTxt = (TextView) view.findViewById(R.id.rainValueId);
        current_temp = (TextView) view.findViewById(R.id.currentTempDetailId);

        dbHelper = new DBHelper(getActivity());
        dataService = new DataService();

        Bundle bundle = this.getArguments();
        if (bundle != null){
            current_temp.setText(bundle.getString("temp","5"));
            maxTempDetailTxt.setText(bundle.getString("maxTemp","5"));
            minTempDetailTxt.setText(bundle.getString("minTemp","5"));
            humidityValueTxt.setText(bundle.getString("humidity","5"));
            windValueTxt.setText(bundle.getString("wind","5"));
            city_nameTxt.setText(bundle.getString("city","Current City"));
            //TODO: current temp json handle
            //TODO: img json handle

        }
        String forecastUrl = dataService.getForecastWeatherData(bundle.getDouble("lat"),bundle.getDouble("lon"));
        Log.v("weather",""+ forecastUrl);
        try {
            jsonObject = new JSONObject(forecastUrl);
            jsonHandler = new JSONHandler();
            locations = jsonHandler.JSONForecastHandler(jsonObject);

            updateListView(weather);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewId);
        adapter = new WeatherRecyclerAdapter(locations);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(manager);
        adapter.notifyDataSetChanged();
        return view;
    }


    public void updateListView(Weather weather){

    }
}
