package pl.adamsiedlecki.Pickeri.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adamsiedlecki.Pickeri.service.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private FruitPickerService fruitPickerService;
    private FruitDeliveryService fruitDeliveryService;
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    private Environment env;

    @Autowired
    public ApiController(FruitPickerService pickerService, FruitDeliveryService fruitDeliveryService, Environment environment,
                         DeviceService deviceService, DeviceControllerService deviceControllerService, ExpenseService expenseService,
                         FruitTypeService fruitTypeService) {
        this.env = environment;
        this.fruitPickerService = pickerService;
        this.fruitDeliveryService = fruitDeliveryService;

    }

    @GetMapping(value = "/get-all/{key}")
    public String getInfoString(@PathVariable String key) {
        return getDataStrings(key, fruitPickerService.findAll());
    }

    @GetMapping(value = "/get-pickers-amount/{key}")
    public String getPickersAmountInfo(@PathVariable String key) {
        return getDataStrings(key, fruitPickerService.getTotalAmountOfPickers());
    }

    @GetMapping(value = "/get-weight/{key}")
    public String getWeightInfo(@PathVariable String key) {
        return getDataStrings(key, fruitDeliveryService.getWeightSum());
    }

    @GetMapping(value = "/get-packages-amount/{key}")
    public String getPackagesInfo(@PathVariable String key) {
        return getDataStrings(key, fruitDeliveryService.getTotalAmountOfPackages());
    }

    @GetMapping(value = "/get-today-deliveries/{key}")
    public String getTodayDeliveries(@PathVariable String key) {
        return getDataStrings(key, fruitDeliveryService.getTodayDeliveries());
    }

    private String getDataStrings(String key, Number getNumberFunction) {
        String pass = env.getProperty("api.pass");
        if (pass.equals(key)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(getNumberFunction);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return "ACCESS DENIED";
    }

    private String getDataStrings(String key, List<?> list) {
        String pass = env.getProperty("api.pass");
        if (pass.equals(key)) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(list);
            } catch (JsonProcessingException e) {
                log.error(e.getMessage());
                return "ERROR OCCURRED";
            }
        }
        return "ACCESS DENIED";
    }

}
