package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
public class GeoLocalization {

    private Double latitude;

    private Double longitude;

    public GeoLocalization() {
    }

    public GeoLocalization(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
}
