package Model;

/**
 * Created by ozanal on 27/03/2018.
 */

public class Weather {

    private String max_tempTxt;
    private String min_tempTxt;
    private String city_nameTxt;
    private String country_nameTxt;
    private String descriptionTxt;
    private Double lat;
    private Double lon;
    private String windSpeedTxt;
    private String windDegTxt;
    private String humidity;

    public Weather() {
    }

    public Weather(String max_tempTxt, String min_tempTxt, String city_nameTxt, String country_nameTxt, String descriptionTxt, Double lat, Double lon, String windSpeedTxt, String windDegTxt, String humidity) {
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
    }

    public String getMax_tempTxt() {
        return max_tempTxt;
    }

    public void setMax_tempTxt(String max_tempTxt) {
        this.max_tempTxt = max_tempTxt;
    }

    public String getMin_tempTxt() {
        return min_tempTxt;
    }

    public void setMin_tempTxt(String min_tempTxt) {
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
}
