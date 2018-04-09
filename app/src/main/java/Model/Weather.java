package Model;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by ozanal on 27/03/2018.
 */

public class Weather {

    private int max_tempTxt;
    private int min_tempTxt;
    private int current_temp;
    private String city_nameTxt;
    private String country_nameTxt;
    private String descriptionTxt;
    private String date;
    private Double lat;
    private Double lon;
    private String windSpeedTxt;
    private String windDegTxt;
    private String humidity;
    private int ItemId;
    private String weatherIcon;

    public Weather() {
    }

    public Weather(int max_tempTxt, int min_tempTxt, String city_nameTxt, String country_nameTxt, String descriptionTxt, Double lat, Double lon, String windSpeedTxt, String windDegTxt, String humidity,String date,int current_temp,int itemId,String weatherIcon) {
        this.max_tempTxt = max_tempTxt;
        this.min_tempTxt = min_tempTxt;
        this.city_nameTxt = city_nameTxt;
        this.country_nameTxt = country_nameTxt;
        this.descriptionTxt = descriptionTxt;
        this.lat = lat;
        this.lon = lon;
        this.windSpeedTxt = windSpeedTxt;
        this.windDegTxt = windDegTxt;
        this.humidity = humidity;
        this.date = date;
        this.current_temp = current_temp;
        this.ItemId = itemId;
        this.weatherIcon = weatherIcon;
    }

    public int getItemId() {
        return ItemId;
    }

    public void setItemId(int itemId) {
        ItemId = itemId;
    }

    public int getMax_tempTxt() {
        return max_tempTxt;
    }

    public void setMax_tempTxt(int max_tempTxt) {
        this.max_tempTxt = max_tempTxt;
    }

    public int getMin_tempTxt() {
        return min_tempTxt;
    }

    public void setMin_tempTxt(int min_tempTxt) {
        this.min_tempTxt = min_tempTxt;
    }

    public String getCity_nameTxt() {
        return city_nameTxt;
    }

    public void setCity_nameTxt(String city_nameTxt) {
        this.city_nameTxt = city_nameTxt;
    }

    public String getCountry_nameTxt() {
        return country_nameTxt;
    }

    public void setCountry_nameTxt(String country_nameTxt) {
        this.country_nameTxt = country_nameTxt;
    }

    public String getDescriptionTxt() {
        return descriptionTxt;
    }

    public void setDescriptionTxt(String descriptionTxt) {
        this.descriptionTxt = descriptionTxt;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getWindSpeedTxt() {
        return windSpeedTxt;
    }

    public void setWindSpeedTxt(String windSpeedTxt) {
        this.windSpeedTxt = windSpeedTxt;
    }

    public String getWindDegTxt() {
        return windDegTxt;
    }

    public void setWindDegTxt(String windDegTxt) {
        this.windDegTxt = windDegTxt;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDate() {
        SimpleDateFormat curFormater = new SimpleDateFormat("EEEE");
        GregorianCalendar date = new GregorianCalendar();
        String[] dateStringArray = new String[7];

        for (int day = 0; day < 7; day++) {
            dateStringArray[day] = curFormater.format(date.getTime());
            date.roll(Calendar.DATE, 1);

        }
        return "";
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCurrent_temp() {
        return current_temp;
    }

    public void setCurrent_temp(int current_temp) {
        this.current_temp = current_temp;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }
}
