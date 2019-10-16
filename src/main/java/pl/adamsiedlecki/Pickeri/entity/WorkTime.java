package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
public class WorkTime {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="picker_id", referencedColumnName="id")
    private FruitPicker fruitPicker;

    @Column(nullable = true)
    private LocalDateTime startTime;

    @Column(nullable = true)
    private LocalDateTime endTime;

    private BigDecimal amountOfHours;

    public String getPickerInfo(){
        return fruitPicker.getId()+" "+fruitPicker.getName()+" "+fruitPicker.getLastName();
    }

    public FruitPicker getFruitPicker() {
        return fruitPicker;
    }

    public void setFruitPicker(FruitPicker fruitPicker) {
        this.fruitPicker = fruitPicker;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getAmountOfHours() {
        return amountOfHours;
    }

    public void setAmountOfHours(BigDecimal amountOfHours) {
        this.amountOfHours = amountOfHours;
    }
}
