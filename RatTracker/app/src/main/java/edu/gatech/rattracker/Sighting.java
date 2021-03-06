package edu.gatech.rattracker;

import com.google.firebase.database.Exclude;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Information Holder for the data of a single rat sighting.
 *
 * @author Jiseok Choi
 * @version 1.0
 */

public class Sighting implements Serializable {
    private String key;
    private long date;
    private String type;
    private long zip;
    private String address;
    private String city;
    private String borough;
    private double longitude;
    private double latitude;

    /**
     * Creates a sighting without initial information.
     */
    public Sighting() {
    }

    /**
     * Creates sighting with provided information.
     *
     * @param aKey key for Firebase database
     * @param aDate the date of the sighting as a Unix timestamp in milliseconds
     * @param aType the type of sighting
     * @param aZip the zip code of the sighting
     * @param aAddress the address of the sighting
     * @param aCity the city of the sighting
     * @param aBorough the borough of the sighting
     * @param aLongitude the longitude of the sighting
     * @param aLatitude the latitude of the sighting
     */
    public Sighting(String aKey, long aDate, String aType, long aZip, String aAddress,
                    String aCity, String aBorough, double aLongitude, double aLatitude) {
        key = aKey;
        date = aDate;
        type = aType;
        zip = aZip;
        address = aAddress;
        city = aCity;
        borough = aBorough;
        longitude = aLongitude;
        latitude = aLatitude;
    }

    public String getKey() {
        return key;
    }

    public long getDate() {
        return date;
    }

    @Exclude
    public String getReformedDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date(this.date));
    }

    /**
     * Reforms location type
     *
     * @return the decoded location type
     */
    @Exclude
    public String getReformedLocationType() {
        try {
            return URLDecoder.decode(type, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return type;
        }
    }

    public long getZip() {
        return zip;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getBorough() {
        return borough;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getType() {return type;}

    public void setKey(String aKey) {
        key = aKey;
    }

    @Override
    public String toString() {
        return "Report Details: \n"
                + "Key: " + getKey() + "\n"
                + "Creation Date: " + getReformedDate() + "\n"
                + "Location Type: " + getReformedLocationType() + "\n"
                + "Zip Code: " + getZip() + "\n"
                + "Address: " + getAddress() + "\n"
                + "City: " + getCity() + "\n"
                + "Borough: " + getBorough() + "\n"
                + "Latitude: " + getLatitude() + "\n"
                + "Longitude: " + getLongitude();
    }


}
