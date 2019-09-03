package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;
import java.math.BigDecimal;

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

    public FruitVariety() {
    }

    public FruitVariety(String name, String comment) {
        this.name = name;
        this.comment = comment;
        this.percentageParticipationInPackagesAmount = percentageParticipationInPackagesAmount;
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
}
