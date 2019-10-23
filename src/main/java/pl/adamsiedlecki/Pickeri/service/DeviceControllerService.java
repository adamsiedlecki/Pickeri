package pl.adamsiedlecki.Pickeri.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.adamsiedlecki.Pickeri.dao.DeviceControllerDAO;
import pl.adamsiedlecki.Pickeri.entity.DeviceController;

import java.util.List;

@Service
public class DeviceControllerService {

    private DeviceControllerDAO deviceControllerDAO;

    @Autowired
    public DeviceControllerService(DeviceControllerDAO deviceControllerDAO){
        this.deviceControllerDAO = deviceControllerDAO;
    }

    public void save(DeviceController deviceController){
        deviceControllerDAO.save(deviceController);
    }

    public List<DeviceController> findAll(){
        return deviceControllerDAO.findAll();
    }

}
