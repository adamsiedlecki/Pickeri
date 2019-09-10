package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Table
public class FruitVariety {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String comment;

    private BigDecimal percentageParticipationInPackagesAmount;

    private long totalPackages;

    private BigDecimal totalWeight;

    private BigDecimal percentageParticipationInWeight;

    public FruitVariety() {
    }

    public FruitVariety(String name, String comment) {
        this.name = name;
        this.comment = comment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public BigDecimal getPercentageParticipationInPackagesAmount() {
        return percentageParticipationInPackagesAmount;
    }

    public void setPercentageParticipationInPackagesAmount(BigDecimal percentageParticipationInPackagesAmount) {
        this.percentageParticipationInPackagesAmount = percentageParticipationInPackagesAmount;
    }

    public long getTotalPackages() {
        return totalPackages;
    }

    public void setTotalPackages(long totalPackages) {
        this.totalPackages = totalPackages;
    }

    public String getPercentageParticipationInPackagesAmountPlainText(){
        return percentageParticipationInPackagesAmount.toPlainString();
    }

    public BigDecimal getTotalWeight(){
        return totalWeight;
    }

    public void setTotalWeight(BigDecimal totalWeight){
        this.totalWeight = totalWeight;
    }

    public BigDecimal getTotalWeightKgPlainText(){
        return totalWeight.divide(new BigDecimal(1000),4, RoundingMode.FLOOR);
    }

    public BigDecimal getPercentageParticipationInWeight() {
        return percentageParticipationInWeight;
    }

    public String getPercentageParticipationInWeightPlainText() {
        return percentageParticipationInWeight.toPlainString();
    }

    public void setPercentageParticipationInWeight(BigDecimal percentageParticipationInWeight) {
        this.percentageParticipationInWeight = percentageParticipationInWeight;
    }
}
