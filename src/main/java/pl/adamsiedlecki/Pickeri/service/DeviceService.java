package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.DeviceDAO;
import pl.adamsiedlecki.Pickeri.entity.Device;

import java.util.List;

@Service
public class DeviceService {

    private DeviceDAO deviceDAO;

    @Autowired
    public DeviceService(DeviceDAO deviceDAO){
        this.deviceDAO = deviceDAO;
    }

    public void save(Device device){
        deviceDAO.save(device);
    }

    public List<Device> findAll(){
        return deviceDAO.findAll();
    }

}
