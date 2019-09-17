package pl.adamsiedlecki.Pickeri.controller;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

import java.util.List;

@RestController
@RequestMapping("/api")
@Scope("prototype")
public class ApiController {

    private List<FruitPicker> pickers;

    @Autowired
    public ApiController(FruitPickerService pickerService){
        this.pickers = pickerService.findAll();
    }

    @RequestMapping(value = "/v1/{key}", method = RequestMethod.GET)
    public String getInfo(@PathVariable String key){

        if(key.equals("pass")){
            ObjectMapper mapper = new ObjectMapper();
            try {
                return  mapper.writeValueAsString(pickers);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        return "";
    }

}
