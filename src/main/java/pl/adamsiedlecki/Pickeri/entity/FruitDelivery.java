package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
    private String fruitType;

    @Column
    private Long packageAmount;

    @Column
    private LocalDateTime deliveryTime;

    @Column
    private String comment;

    @Column
    private String fruitVariety;

    public FruitDelivery() {
    }

    public String getFruitVariety() {
        return fruitVariety;
    }

    public void setFruitVariety(String fruitVariety) {
        this.fruitVariety = fruitVariety;
    }

    public FruitDelivery(Long fruitPickerId, String fruitType, Long packageAmount, String comment,String fruitVariety, LocalDateTime deliveryTime) {
        this.fruitPickerId = fruitPickerId;
        this.fruitType = fruitType;
        this.packageAmount = packageAmount;
        this.deliveryTime = deliveryTime;
        this.comment = comment;
        this.fruitVariety = fruitVariety;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFruitType() {
        return fruitType;
    }

    public void setFruitType(String fruitType) {
        this.fruitType = fruitType;
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

}
