package pl.adamsiedlecki.Pickeri.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.adamsiedlecki.Pickeri.tools.apiInteraction.DeviceApiInteraction;

import javax.persistence.*;

@Entity
public class Device {

    @Transient
    private final Logger log = LoggerFactory.getLogger(Device.class);

    @Id
    @GeneratedValue
    private Long id;
    private int pin;
    private String name;

    @ManyToOne
    @JoinColumn(name="controller_id", referencedColumnName="id")
    private DeviceController deviceController;

    public Device(int pin, String name, DeviceController deviceController) {
        if(pin<0){
            throw new IllegalArgumentException();
        }
        if(name==null || name.isBlank() || name.isEmpty()){
            throw new IllegalArgumentException();
        }
        if(deviceController==null){
            throw new IllegalArgumentException();
        }
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
        DeviceApiInteraction.setDeviceState(this.getDeviceController().getAddress(), this.getPin(), true,
                getDeviceController().getPassword());
    }

    public void stop(){
        DeviceApiInteraction.setDeviceState(this.getDeviceController().getAddress(), this.getPin(), false,
                getDeviceController().getPassword());
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
