package pl.adamsiedlecki.Pickeri.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adamsiedlecki.Pickeri.entity.*;
import pl.adamsiedlecki.Pickeri.service.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2")
public class ObjectiveApiController {

    private FruitPickerService fruitPickerService;
    private FruitDeliveryService fruitDeliveryService;
    private DeviceService deviceService;
    private DeviceControllerService deviceControllerService;
    private ExpenseService expenseService;
    private FruitTypeService fruitTypeService;
    private FruitVarietyService fruitVarietyService;
    private NoteService noteService;
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    private Environment env;

    @Autowired
    public ObjectiveApiController(FruitPickerService pickerService, FruitDeliveryService fruitDeliveryService, Environment environment,
                         DeviceService deviceService, DeviceControllerService deviceControllerService, ExpenseService expenseService,
                         FruitTypeService fruitTypeService, FruitVarietyService fruitVarietyService, NoteService noteService) {
        this.noteService = noteService;
        this.fruitVarietyService = fruitVarietyService;
        this.expenseService = expenseService;
        this.env = environment;
        this.deviceControllerService = deviceControllerService;
        this.deviceService = deviceService;
        this.fruitPickerService = pickerService;
        this.fruitDeliveryService = fruitDeliveryService;
        this.fruitTypeService = fruitTypeService;

    }

    @GetMapping(value = "/get-notes/{key}")
    public List<Note> getNotes(@PathVariable String key) {
        return getDataObjects(key, noteService.findAll());
    }

    @GetMapping(value = "/get-fruit-varieties/{key}")
    public List<FruitVariety> getFruitVarieties(@PathVariable String key) {
        return getDataObjects(key, fruitVarietyService.findAll());
    }

    @GetMapping(value = "/get-fruit-types/{key}")
    public List<FruitType> getFruitTypes(@PathVariable String key) {
        return getDataObjects(key, fruitTypeService.findAll());
    }

    @GetMapping(value = "/get-pickers/{key}")
    public List<FruitPicker> getPickers(@PathVariable String key) {
        return getDataObjects(key, fruitPickerService.findAll());
    }

    @GetMapping(value = "/get-devices/{key}")
    public List<Device> getDevices(@PathVariable String key) {
        return getDataObjects(key, deviceService.findAll());
    }

    @GetMapping(value = "/get-controllers/{key}")
    public List<DeviceController> getDevicesControllers(@PathVariable String key) {
        return getDataObjects(key, deviceControllerService.findAll());
    }

    @GetMapping(value = "/get-expenses/{key}")
    public List<Expense> getExpenses(@PathVariable String key) {
        return getDataObjects(key, expenseService.findAll());
    }

    @GetMapping(value = "/get-deliveries/{key}")
    public List<FruitDelivery> getDeliveries(@PathVariable String key) {
        return getDataObjects(key, fruitDeliveryService.findAll());
    }

    private <T> List<T> getDataObjects(String key, List<T> objects) {
        String pass = env.getProperty("api.pass");
        if (pass.equals(key)) {
            return objects;
        }
        return List.of();
    }

}
