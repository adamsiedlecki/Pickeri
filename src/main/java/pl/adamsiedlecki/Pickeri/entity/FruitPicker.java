package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;

@Entity
@Table
public class FruitPicker {

    public FruitPicker() {
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column
    private String name;

    @Column
    private String lastName;

    @Column
    private String gender;

    private long packageDeliveryAmount;

    private long packageDeliveryWithTypeOne;

    private long packageDeliveryWithTypeTwo;

    private long packageDeliveryWithTypeThree;

    private long packageDeliveryWithTypeFour;

    public long getPackageDeliveryWithTypeOne() {
        return packageDeliveryWithTypeOne;
    }

    public void setPackageDeliveryWithTypeOne(long packageDeliveryWithTypeOne) {
        this.packageDeliveryWithTypeOne = packageDeliveryWithTypeOne;
    }

    public long getPackageDeliveryWithTypeTwo() {
        return packageDeliveryWithTypeTwo;
    }

    public void setPackageDeliveryWithTypeTwo(long packageDeliveryWithTypeTwo) {
        this.packageDeliveryWithTypeTwo = packageDeliveryWithTypeTwo;
    }

    public long getPackageDeliveryWithTypeThree() {
        return packageDeliveryWithTypeThree;
    }

    public void setPackageDeliveryWithTypeThree(long packageDeliveryWithTypeThree) {
        this.packageDeliveryWithTypeThree = packageDeliveryWithTypeThree;
    }

    public long getPackageDeliveryWithTypeFour() {
        return packageDeliveryWithTypeFour;
    }

    public void setPackageDeliveryWithTypeFour(long packageDeliveryWithTypeFour) {
        this.packageDeliveryWithTypeFour = packageDeliveryWithTypeFour;
    }

    public long getPackageDeliveryAmount() {
        return packageDeliveryAmount;
    }

    public void setPackageDeliveryAmount(long packageDeliveryAmount) {
        this.packageDeliveryAmount = packageDeliveryAmount;
    }

    public FruitPicker(String name, String lastName, String gender) {
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
