package pl.adamsiedlecki.Pickeri.entity;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
public class DeviceTest {

    @Test
    public void setPin(){
        Device device = new Device(2,"name", mock(DeviceController.class));
        Assert.assertEquals(2, device.getPin());
    }

    @Test
    public void setName(){
        Device device = new Device(2,"name123", mock(DeviceController.class));
        Assert.assertEquals("name123", device.getName());
    }

    @Test
    public void setDeviceController(){
        Device device = new Device(2,"name123", mock(DeviceController.class));
        Assert.assertNotNull(device.getDeviceController());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setPinLessThanZero(){
        Device device = new Device(-1,"name", mock(DeviceController.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setValuesNull(){
        Device device = new Device(2,null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNameBlank(){
        Device device = new Device(2,"    ", mock(DeviceController.class));
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNameEmpty(){
        Device device = new Device(2,"", mock(DeviceController.class));
    }

}
