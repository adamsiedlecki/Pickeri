package pl.adamsiedlecki.Pickeri.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private FruitPickerService fruitPickerService;
    private FruitDeliveryService fruitDeliveryService;
    private String pass;
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    public ApiController(FruitPickerService pickerService, FruitDeliveryService fruitDeliveryService, Environment environment) {
        this.fruitPickerService = pickerService;
        this.fruitDeliveryService = fruitDeliveryService;
        pass = environment.getProperty("api.pass");
    }

    @GetMapping(value = "/get-all/{key}")
    public String getInfo(@PathVariable String key) {
        return getData(key, fruitPickerService.findAll());
    }

    @GetMapping(value = "/get-pickers-amount/{key}")
    public String getPickersAmountInfo(@PathVariable String key) {
        return getData(key, fruitPickerService.getTotalAmountOfPickers());
    }

    @GetMapping(value = "/get-weight/{key}")
    public String getWeightInfo(@PathVariable String key) {
        return getData(key, fruitDeliveryService.getWeightSum());
    }

    @GetMapping(value = "/get-packages-amount/{key}")
    public String getPackagesInfo(@PathVariable String key) {
        return getData(key, fruitDeliveryService.getTotalAmountOfPackages());
    }

    private String getData(String key, Number getNumberFunction) {
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

    private String getData(String key, List<?> list) {
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
