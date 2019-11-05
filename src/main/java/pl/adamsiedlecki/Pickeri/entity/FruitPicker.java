package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

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

    @Column(columnDefinition = "Decimal(10,2) default '0.00'")
    private BigDecimal fundsPaid;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "fruitPicker", cascade = CascadeType.ALL)
    private List<WorkTime> workTimeList;

    @Transient
    private long packageDeliveryAmount;

    @Transient
    private long packageDeliveryWithTypeOne;

    @Transient
    private long packageDeliveryWithTypeTwo;

    @Transient
    private long packageDeliveryWithTypeThree;

    @Transient
    private long packageDeliveryWithTypeFour;

    @Transient
    private BigDecimal weightSum;

    @Transient
    private BigDecimal weightWithTypeOne;

    @Transient
    private BigDecimal weightWithTypeTwo;

    @Transient
    private BigDecimal weightWithTypeThree;

    @Transient
    private BigDecimal weightWithTypeFour;

    public BigDecimal getWorkTimeHours(){
        BigDecimal hours = BigDecimal.ZERO;
        for(WorkTime workTime : workTimeList){
            BigDecimal timeAmount = new BigDecimal(workTime.getDuration().getSeconds());
            timeAmount = timeAmount.divide(new BigDecimal(3600), 2, RoundingMode.FLOOR);
            hours = hours.add(timeAmount);
        }
        return hours;
    }

    public String getIdNameLastName(){
        return id+" "+name+" "+lastName;
    }

    public List<WorkTime> getWorkTimeList() {
        return workTimeList;
    }

    public void setWorkTimeList(List<WorkTime> workTimeList) {
        this.workTimeList = workTimeList;
    }

    public BigDecimal getFundsPaid() {
        return fundsPaid;
    }

    public void setFundsPaid(BigDecimal fundsPaid) {
        this.fundsPaid = fundsPaid;
    }

    public BigDecimal getWeightWithTypeOne() {
        return weightWithTypeOne;
    }

    public String getWeightKgWithTypeOnePlainText() {
        BigDecimal result = weightWithTypeOne.divide(new BigDecimal(1000), 4, RoundingMode.FLOOR);
        return result.toPlainString();
    }

    public String getWeightKgWithTypeTwoPlainText() {
        BigDecimal result = weightWithTypeTwo.divide(new BigDecimal(1000), 4, RoundingMode.FLOOR);
        return result.toPlainString();
    }

    public String getWeightKgWithTypeThreePlainText() {
        BigDecimal result = weightWithTypeThree.divide(new BigDecimal(1000), 4, RoundingMode.FLOOR);
        return result.toPlainString();
    }

    public String getWeightKgWithTypeFourPlainText() {
        BigDecimal result = weightWithTypeFour.divide(new BigDecimal(1000), 4, RoundingMode.FLOOR);
        return result.toPlainString();
    }

    public void setWeightWithTypeOne(BigDecimal weightWithTypeOne) {
        this.weightWithTypeOne = weightWithTypeOne;
    }

    public BigDecimal getWeightWithTypeTwo() {
        return weightWithTypeTwo;
    }

    public void setWeightWithTypeTwo(BigDecimal weightWithTypeTwo) {
        this.weightWithTypeTwo = weightWithTypeTwo;
    }

    public BigDecimal getWeightWithTypeThree() {
        return weightWithTypeThree;
    }

    public void setWeightWithTypeThree(BigDecimal weightWithTypeThree) {
        this.weightWithTypeThree = weightWithTypeThree;
    }

    public BigDecimal getWeightWithTypeFour() {
        return weightWithTypeFour;
    }

    public void setWeightWithTypeFour(BigDecimal weightWithTypeFour) {
        this.weightWithTypeFour = weightWithTypeFour;
    }

    // [gram]
    public BigDecimal getWeightSum() {
        return weightSum;
    }

    public String getWeightSumKgPlainText() {
        BigDecimal result = weightSum.divide(new BigDecimal(1000), 4, RoundingMode.FLOOR);
        return result.toPlainString();
    }

    public void setWeightSum(BigDecimal weightSum) {
        this.weightSum = weightSum;
    }

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
