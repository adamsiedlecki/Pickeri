package pl.adamsiedlecki.Pickeri.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
public class DeviceControllerTest {

    @Test
    public void setProperValues() {
        DeviceController deviceController = new DeviceController("name", "password", "address");
        Assert.assertEquals("name", deviceController.getName());
        Assert.assertEquals("password", deviceController.getPassword());
        Assert.assertEquals("address", deviceController.getAddress());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNullValues() {
        DeviceController deviceController = new DeviceController(null, null, null);
    }

    @Test
    public void setName() {
        DeviceController deviceController = new DeviceController("name", "password", "address");
        deviceController.setName("name123");
        Assert.assertEquals("name123", deviceController.getName());
    }

    @Test
    public void setPassword() {
        DeviceController deviceController = new DeviceController("name", "password", "address");
        deviceController.setPassword("password123");
        Assert.assertEquals("password123", deviceController.getPassword());
    }

    @Test
    public void setAddress() {
        DeviceController deviceController = new DeviceController("name", "password", "address");
        deviceController.setAddress("address123");
        Assert.assertEquals("address123", deviceController.getAddress());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNullName() {
        DeviceController deviceController = new DeviceController("name", "password", "address");
        deviceController.setName(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNullPassword() {
        DeviceController deviceController = new DeviceController("name", "password", "address");
        deviceController.setPassword(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNullAddress() {
        DeviceController deviceController = new DeviceController("name", "password", "address");
        deviceController.setAddress(null);
    }

    @Test
    public void addDevice() {
        DeviceController deviceController = new DeviceController("name", "password", "address");
        deviceController.addDevice(mock(Device.class));
        Assert.assertEquals(1, deviceController.getDevices().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addNullDevice() {
        DeviceController deviceController = new DeviceController("name", "password", "address");
        deviceController.addDevice(null);
    }
}
