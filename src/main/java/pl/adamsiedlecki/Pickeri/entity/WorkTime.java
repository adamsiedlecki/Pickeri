package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    private Duration duration;

    public WorkTime(FruitPicker fruitPicker, LocalDateTime startTime, LocalDateTime endTime, Duration duration) {
        this.fruitPicker = fruitPicker;
        this.startTime = startTime;
        this.endTime = endTime;
        this.duration = duration;
    }

    public WorkTime() {
    }

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

    public String getStartTimePlainString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return startTime.format(formatter);
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public String getEndTimePlainString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return endTime.format(formatter);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public String getDurationPlainString() {
        BigDecimal workHours = new BigDecimal(getDuration().getSeconds())
                .divide(new BigDecimal(3600),2, RoundingMode.FLOOR);
        return String.valueOf(workHours);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
