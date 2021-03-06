package Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Model.Weather;

/**
 * Created by ozanal on 27/03/2018.
 */

public class JSONHandler {
    public Weather JSONWeatherHandler(JSONObject jsonObject) {

        Weather weather = new Weather(0,0,"","","",0.0,0.0,"","","");
        try {
            // Coordinats
            JSONObject coordObj = DataProvider.getObject("coord",jsonObject);
            weather.setLat(DataProvider.getDouble("lat",coordObj));
            weather.setLon(DataProvider.getDouble("lon",coordObj));
            // Weather
            JSONArray weatherArray = jsonObject.getJSONArray("weather");
            JSONObject weatherObj = weatherArray.getJSONObject(0);
            weather.setDescriptionTxt(DataProvider.getString("main",weatherObj));
            // Main
            JSONObject mainObj = DataProvider.getObject("main",jsonObject);
            weather.setMax_tempTxt(DataProvider.getInt("temp_max",mainObj));
            weather.setMin_tempTxt(DataProvider.getInt("temp_min",mainObj));
            weather.setHumidity(DataProvider.getString("humidity",mainObj));
            // Wind
            JSONObject windObj = DataProvider.getObject("wind",jsonObject);
            weather.setWindSpeedTxt(DataProvider.getString("speed",windObj));
//            weather.setWindDegTxt(DataProvider.getString("deg",windObj));
            // City Name
            weather.setCity_nameTxt(DataProvider.getString("name",jsonObject));
            // Sys
            JSONObject sysObj = DataProvider.getObject("sys",jsonObject);
            weather.setCountry_nameTxt(DataProvider.getString("country",sysObj));


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }
    public Weather JSONForecastHandler(JSONObject jsonObject){
        Weather weather = new Weather(1,1,"","","",0.0,0.0,"","","");

        try {
            JSONArray listArray = jsonObject.getJSONArray("list");
            for (int i=0;i<listArray.length()-1;i++){
                if (i % 8 == 7){
                    // Main
                    JSONObject listArrayJSONObject = listArray.getJSONObject(i);
                    JSONObject mainObj = DataProvider.getObject("main",listArrayJSONObject);
                    weather.setMax_tempTxt(DataProvider.getInt("temp_max",mainObj));
                    weather.setMin_tempTxt(DataProvider.getInt("temp_min",mainObj));

                    JSONArray weatherArrayObj = listArrayJSONObject.getJSONArray("weather");
                    JSONObject weatherObj = weatherArrayObj.getJSONObject(i);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return weather;
    }
}
