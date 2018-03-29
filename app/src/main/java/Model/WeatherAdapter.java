package Model;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hasanozanal.sunshine.R;

import java.util.ArrayList;

/**
 * Created by ozanal on 29/03/2018.
 */

public class WeatherAdapter extends ArrayAdapter<Weather> {
    Activity activity;
    int layoutResource;
    Weather weather;
    ArrayList<Weather> wData = new ArrayList<>();

    public WeatherAdapter(@NonNull Activity act, int resource, ArrayList<Weather> weatherData) {
        super(act, resource, weatherData);
        activity = act;
        layoutResource = resource;
        wData = weatherData;
        notifyDataSetChanged();
    }



    @Override
    public int getCount() {
        return wData.size();
    }

    @Nullable
    @Override
    public Weather getItem(int position) {
        return wData.get(position);
    }

    @Override
    public int getPosition(@Nullable Weather item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        ViewHolder holder = null;
        if (row == null || (row.getTag()) == null) {

            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutResource, null);
            holder = new ViewHolder();

            holder.city_nameHolder = (TextView) row.findViewById(R.id.list_row_cityNameId);
            holder.max_tempHolder = (TextView) row.findViewById(R.id.list_row_tempId);

            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }
        holder.weather = getItem(position);
        holder.city_nameHolder.setText(holder.weather.getCity_nameTxt());
        holder.max_tempHolder.setText(holder.weather.getMax_tempTxt());
        return row;
    }


    class ViewHolder {
        Weather weather;
        TextView city_nameHolder;
        TextView max_tempHolder;
        TextView min_tempHolder;
    }

}
