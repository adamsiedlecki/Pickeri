package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

@Entity
@Table
public class FruitDelivery {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private Long fruitPickerId;

    @Column
    private String type;

    @Column
    private Long packageAmount;

    @Column
    private LocalDateTime deliveryTime;

    @Column
    private String comment;

    @Column
    private String fruitVarietyName;

    @Column
    private BigDecimal fruitWeight;

    @Embedded
    private GeoLocalization geoLocalization;

    public FruitDelivery() {
    }

    public FruitDelivery(Long fruitPickerId, String fruitType, Long packageAmount, String comment, String fruitVarietyName, LocalDateTime deliveryTime) {
        this.fruitPickerId = fruitPickerId;
        this.packageAmount = packageAmount;
        this.deliveryTime = deliveryTime;
        this.comment = comment;
        this.fruitVarietyName = fruitVarietyName;
        this.type = fruitType;

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFruitPickerId(Long fruitPickerId) {
        this.fruitPickerId = fruitPickerId;
    }

    public BigDecimal getFruitWeight() {
        return fruitWeight;
    }

    public String getFruitWeightKgPlainString() {
        BigDecimal result = fruitWeight.divide(BigDecimal.valueOf(1000), 2, RoundingMode.FLOOR);
        return result.toPlainString();
    }

    public void setFruitWeight(BigDecimal fruitWeight) {
        this.fruitWeight = fruitWeight;
    }

    public GeoLocalization getGeoLocalization() {
        return geoLocalization;
    }

    public void setGeoLocalization(GeoLocalization geoLocalization) {
        this.geoLocalization = geoLocalization;
    }

    public String getFruitVarietyName() {
        return fruitVarietyName;
    }

    public void setFruitVarietyName(String fruitVarietyName) {
        this.fruitVarietyName = fruitVarietyName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(Long packageAmount) {
        this.packageAmount = packageAmount;
    }

    public String getDeliveryTimeFormatted() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return deliveryTime.format(formatter);
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public long getFruitPickerId() {
        return fruitPickerId;
    }

    public void setFruitPickerId(long fruitPickerId) {
        this.fruitPickerId = fruitPickerId;
    }

    public String getWeightSumKgPlainText() {
        BigDecimal result = fruitWeight.divide(new BigDecimal(1000), 4, RoundingMode.FLOOR);
        return result.toPlainString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FruitDelivery)) return false;
        FruitDelivery delivery = (FruitDelivery) o;
        return getId() == delivery.getId() &&
                Objects.equals(getFruitPickerId(), delivery.getFruitPickerId()) &&
                Objects.equals(getType(), delivery.getType()) &&
                Objects.equals(getPackageAmount(), delivery.getPackageAmount()) &&
                Objects.equals(getDeliveryTime(), delivery.getDeliveryTime()) &&
                Objects.equals(getComment(), delivery.getComment()) &&
                Objects.equals(getFruitVarietyName(), delivery.getFruitVarietyName()) &&
                Objects.equals(getFruitWeight(), delivery.getFruitWeight()) &&
                Objects.equals(getGeoLocalization(), delivery.getGeoLocalization());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFruitPickerId(), getType(), getPackageAmount(), getDeliveryTime(), getComment(), getFruitVarietyName(), getFruitWeight(), getGeoLocalization());
    }
}
