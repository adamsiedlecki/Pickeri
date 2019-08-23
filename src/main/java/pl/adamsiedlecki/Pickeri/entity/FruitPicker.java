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

    private long packageDeliveryWithCalyx;

    private long packageDeliveryWithoutCalyx;

    public long getPackageDeliveryWithCalyx() {
        return packageDeliveryWithCalyx;
    }

    public void setPackageDeliveryWithCalyx(long packageDeliveryWithCalyx) {
        this.packageDeliveryWithCalyx = packageDeliveryWithCalyx;
    }

    public long getPackageDeliveryWithoutCalyx() {
        return packageDeliveryWithoutCalyx;
    }

    public void setPackageDeliveryWithoutCalyx(long packageDeliveryWithoutCalyx) {
        this.packageDeliveryWithoutCalyx = packageDeliveryWithoutCalyx;
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
