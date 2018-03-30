package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hasanozanal.sunshine.R;

public class DetailsFragment extends Fragment {

    private MainFragment mainFragment;

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
        mainFragment = new MainFragment();
        maxTempDetailTxt = (TextView) view.findViewById(R.id.detailsMaxTempId);

        return view;
    }
}
