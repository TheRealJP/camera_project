package be.kdg.processor.proxy.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import javax.persistence.*;

@Data
public class Location {
    private int id;
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

    @Override
    public String toString() {
        return "Location{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
