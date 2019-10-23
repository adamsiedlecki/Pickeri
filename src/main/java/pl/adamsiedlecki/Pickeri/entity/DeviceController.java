package pl.adamsiedlecki.Pickeri.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class DeviceController {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String password;

    private String address;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "deviceController", cascade = CascadeType.ALL)
    private List<Device> devices;

    public DeviceController(String name, String password, String address) {
        this.name = name;
        this.password = password;
        this.address = address;
    }

    public DeviceController() {
    }

    public void addDevice(Device device){
        this.devices.add(device);
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
