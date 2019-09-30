package pl.adamsiedlecki.Pickeri.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private FruitPickerService fruitPickerService;
    private FruitDeliveryService fruitDeliveryService;
    private String pass;

    @Autowired
    public ApiController(FruitPickerService pickerService, FruitDeliveryService fruitDeliveryService, Environment environment) {
        this.fruitPickerService = pickerService;
        this.fruitDeliveryService = fruitDeliveryService;
        pass = environment.getProperty("api.pass");
    }

    @RequestMapping(value = "/get-all/{key}", method = RequestMethod.GET)
    public String getInfo(@PathVariable String key) {
        return getData(key, fruitPickerService.findAll());
    }

    @RequestMapping(value = "/get-pickers-amount/{key}", method = RequestMethod.GET)
    public String getPickersAmountInfo(@PathVariable String key) {
        return getData(key, fruitPickerService.getTotalAmountOfPickers());
    }

    @RequestMapping(value = "/get-weight/{key}", method = RequestMethod.GET)
    public String getWeightInfo(@PathVariable String key) {
        return getData(key, fruitDeliveryService.getWeightSum());
    }

    @RequestMapping(value = "/get-packages-amount/{key}", method = RequestMethod.GET)
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
                e.printStackTrace();
            }
        }
        return "ACCESS DENIED";
    }

}
