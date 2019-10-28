package pl.adamsiedlecki.Pickeri.tools.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.adamsiedlecki.Pickeri.entity.Device;

import java.io.IOException;
import java.net.InetAddress;

public class PingTest {

    private static final Logger log = LoggerFactory.getLogger(PingTest.class);

    public boolean isAlive(Device device){
        try {
            String textAddress = device.getDeviceController().getAddress();
            textAddress = textAddress.replace("https://", "");
            textAddress = textAddress.replace("http://", "");
            String[] parts = textAddress.split(":");
            textAddress = parts[0];
            InetAddress address = InetAddress.getByName(textAddress);
            if(address.isReachable(5000)){
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return false;
    }

    public  boolean isAlive(String textAddress){
        try {
            textAddress = textAddress.replace("https://", "");
            textAddress = textAddress.replace("http://", "");
            String[] parts = textAddress.split(":");
            textAddress = parts[0];
            InetAddress address = InetAddress.getByName(textAddress);
            if(address.isReachable(5000)){
                return true;
            }else{
                return false;
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return false;
    }
}
