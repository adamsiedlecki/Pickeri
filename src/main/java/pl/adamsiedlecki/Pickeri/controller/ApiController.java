package pl.adamsiedlecki.Pickeri.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

@RestController
@RequestMapping("/api/v1")
@Scope("prototype")
public class ApiController {

    private FruitPickerService fruitPickerService;
    private FruitDeliveryService fruitDeliveryService;
    private String pass = "pass";

    @Autowired
    public ApiController(FruitPickerService pickerService, FruitDeliveryService fruitDeliveryService){
        this.fruitPickerService = pickerService;
        this.fruitDeliveryService = fruitDeliveryService;
    }

    @RequestMapping(value = "/get-all/{key}", method = RequestMethod.GET)
    public String getInfo(@PathVariable String key){

        if(pass.equals(key)){
            ObjectMapper mapper = new ObjectMapper();
            try {
                return  mapper.writeValueAsString(fruitPickerService.findAll());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return "ACCESS DENIED";
    }

    @RequestMapping(value = "/get-pickers-amount/{key}", method = RequestMethod.GET)
    public String getPickersAmountInfo(@PathVariable String key){

        if(pass.equals(key)){
            ObjectMapper mapper = new ObjectMapper();
            try {
                return  mapper.writeValueAsString(fruitPickerService.getTotalAmountOfPickers());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return "ACCESS DENIED";
    }

    @RequestMapping(value = "/get-weight/{key}", method = RequestMethod.GET)
    public String getWeightInfo(@PathVariable String key){

        if(pass.equals(key)){
            ObjectMapper mapper = new ObjectMapper();
            try {
                return  mapper.writeValueAsString(fruitDeliveryService.getWeightSum());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return "ACCESS DENIED";
    }

    @RequestMapping(value = "/get-packages-amount/{key}", method = RequestMethod.GET)
    public String getPackagesInfo(@PathVariable String key){

        if(pass.equals(key)){
            ObjectMapper mapper = new ObjectMapper();
            try {
                return  mapper.writeValueAsString(fruitDeliveryService.getTotalAmountOfPackages());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return "ACCESS DENIED";
    }

}
