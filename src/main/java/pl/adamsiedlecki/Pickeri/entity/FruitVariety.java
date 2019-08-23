package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;

@Entity
@Table
public class FruitVariety {

    @Id
    @Column
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    public FruitVariety() {
    }

    public FruitVariety(String name, String comment, float percentageParticipationInPackagesAmount) {
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

    public float getPercentageParticipationInPackagesAmount() {
        return percentageParticipationInPackagesAmount;
    }

    public void setPercentageParticipationInPackagesAmount(float percentageParticipationInPackagesAmount) {
        this.percentageParticipationInPackagesAmount = percentageParticipationInPackagesAmount;
    }

    @Column
    private String comment;

    private float percentageParticipationInPackagesAmount;

}
