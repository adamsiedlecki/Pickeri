package pl.adamsiedlecki.Pickeri.entity;

import pl.adamsiedlecki.Pickeri.tools.apiInteraction.DeviceApiInteraction;

import javax.persistence.*;

@Entity
public class Device {

    @Id
    @GeneratedValue
    private Long id;

    private int pin;

    private String name;

    @ManyToOne
    @JoinColumn(name="controller_id", referencedColumnName="id")
    private DeviceController deviceController;

    public Device(int pin, String name, DeviceController deviceController) {
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

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void start(){
        DeviceApiInteraction.setDeviceState(this.getDeviceController().getAddress(), this.getPin(), true);
    }

    public void stop(){
        DeviceApiInteraction.setDeviceState(this.getDeviceController().getAddress(), this.getPin(), false);
    }

    public boolean isEnabled(){
        return DeviceApiInteraction.getDeviceState(this.getDeviceController().getAddress(), this.getPin());
    }

    @Override
    public String toString() {
        return "Device{" +
                "pin=" + pin +
                ", name='" + name + '\'' +
                '}';
    }
}
