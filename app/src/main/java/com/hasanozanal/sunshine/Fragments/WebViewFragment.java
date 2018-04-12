package com.hasanozanal.sunshine.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.hasanozanal.sunshine.R;

public class WebViewFragment extends Fragment {

    public WebViewFragment() {
    }
    public static WebViewFragment newInstance(String param1, String param2) {
        WebViewFragment fragment = new WebViewFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web_view, container, false);
        WebView wv = (WebView) view.findViewById(R.id.the_webview);
        wv.loadUrl("http://www.hasanozanal.com");
        return view;
    }
}
