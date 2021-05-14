package pl.adamsiedlecki.Pickeri.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import pl.adamsiedlecki.Pickeri.tools.time.TimeConverter;

import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class WorkTime {

    @Id
    @GeneratedValue
    private Long id;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="picker_id", referencedColumnName="id")
    private FruitPicker fruitPicker;
    @JsonIgnore
    @Column(nullable = true)
    private LocalDateTime startTime;
    @JsonIgnore
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

    @JsonIgnore
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

    @JsonIgnore
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

    @JsonIgnore
    public String getEndTimePlainString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return endTime.format(formatter);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @JsonIgnore
    public Duration getDuration() {
        return duration;
    }

    public String getDurationPlainString() {
        return TimeConverter.getString(this);
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
