package pl.adamsiedlecki.Pickeri.entity;

import com.google.common.base.Preconditions;

import javax.persistence.*;
import java.util.ArrayList;
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
        Preconditions.checkArgument(name!=null, "Name cannot be null.");
        Preconditions.checkArgument(!name.isEmpty(), "Name cannot be empty.");
        Preconditions.checkArgument(!name.isBlank(), "Name cannot be blank.");
        Preconditions.checkArgument(password!=null, "Password cannot be null.");
        Preconditions.checkArgument(address!=null, "Address cannot be null.");
        Preconditions.checkArgument(!address.isEmpty(), "Address cannot be empty.");
        Preconditions.checkArgument(!address.isBlank(), "Address cannot be blank.");
        this.name = name;
        this.password = password;
        this.address = address;
        devices = new ArrayList<>();
    }

    public DeviceController() {
    }

    public void addDevice(Device device){
        Preconditions.checkArgument(device!=null, "Device cannot be null.");
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
        Preconditions.checkArgument(name!=null, "Name cannot be null.");
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        Preconditions.checkArgument(password!=null, "Password cannot be null.");
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        Preconditions.checkArgument(address!=null, "Address cannot be null.");
        this.address = address;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }
}
