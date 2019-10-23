package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;

@Entity
public class Device {

    @Id
    @GeneratedValue
    private Long id;

    private Long pin;

    private String name;

    @ManyToOne
    @JoinColumn(name="controller_id", referencedColumnName="id")
    private DeviceController deviceController;

    public Device(Long pin, String name, DeviceController deviceController) {
        this.pin = pin;
        this.name = name;
        this.deviceController = deviceController;
    }

    public Device() {
    }

    public DeviceController getDeviceController() {
        return deviceController;
    }

    public void setDeviceController(DeviceController deviceController) {
        this.deviceController = deviceController;
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

    public void start(){
        //TODO
    }

    public void stop(){
        //TODO
    }

    @Override
    public String toString() {
        return "Device{" +
                "pin=" + pin +
                ", name='" + name + '\'' +
                '}';
    }
}
