package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

@Entity
public class Device {

    @Id
    @GeneratedValue
    private Long id;

    private Long pin;

    private String name;

    @JoinColumn(name="controller_id", referencedColumnName="id")
    private DeviceController deviceController;

    public Device(Long pin, String name) {
        this.pin = pin;
        this.name = name;
    }

    public Device() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPin() {
        return pin;
    }

    public void setPin(Long pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
