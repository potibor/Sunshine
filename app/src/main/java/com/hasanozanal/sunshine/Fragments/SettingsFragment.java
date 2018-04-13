package com.hasanozanal.sunshine.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.hasanozanal.sunshine.R;

public class SettingsFragment extends Fragment {
    private Switch aSwitch;
    private TextView saveBtn;
    public SettingsFragment() {
    }
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        aSwitch = (Switch) view.findViewById(R.id.settings_fragment_switch_btn);
        saveBtn = (TextView) view.findViewById(R.id.fragment_settings_savebtn);

        SharedPreferences preferences = getActivity().getSharedPreferences("switch",0);
        if (preferences.getBoolean("Celcius",true)){
            aSwitch.setChecked(true);
        }else{
            aSwitch.setChecked(false);
        }


        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("switch",0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (aSwitch.isChecked()){
                    editor.putBoolean("Celcius",true);
                    editor.apply();
                }else{
                    editor.putBoolean("Celcius",false);
                    editor.apply();
                }
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainFragment mainFragment = new MainFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_containerId,mainFragment);
                transaction.commit();
            }
        });

        return view;
    }
}
