package be.kdg.processor.models.proxy;

import com.fasterxml.jackson.annotation.JsonAlias;

//"location":{"lat":51.203512,"long":4.437337}
public class Location {
    @JsonAlias("lat")
    private double latitude;
    @JsonAlias("long")
    private double longitude;

    public Location() {
    }

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
