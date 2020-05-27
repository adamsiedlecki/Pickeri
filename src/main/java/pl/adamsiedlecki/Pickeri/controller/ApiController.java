package pl.adamsiedlecki.Pickeri.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.*;
import pl.adamsiedlecki.Pickeri.tools.pdf.PickersToPdfWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ApiController {

    private final FruitPickerService fruitPickerService;
    private final FruitDeliveryService fruitDeliveryService;
    private final FruitTypeService fruitTypeService;
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    private final Environment env;

    @Autowired
    public ApiController(FruitPickerService pickerService, FruitDeliveryService fruitDeliveryService, Environment environment,
                         DeviceService deviceService, DeviceControllerService deviceControllerService, ExpenseService expenseService,
                         FruitTypeService fruitTypeService) {
        this.env = environment;
        this.fruitPickerService = pickerService;
        this.fruitDeliveryService = fruitDeliveryService;
        this.fruitTypeService = fruitTypeService;
    }

    @GetMapping(value = "/get-all/{key}")
    public List<?> getInfoString(@PathVariable String key) {
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
    public List<?> getTodayDeliveries(@PathVariable String key) {
        List<FruitDelivery> todayDeliveries = fruitDeliveryService.getTodayDeliveries();
        todayDeliveries.sort(Comparator.comparing(FruitDelivery::getDeliveryTime));
        return getDataStrings(key, todayDeliveries);
    }

    @GetMapping(value = "/download-pickers-pdf/{key}", produces = "application/pdf")
    public ResponseEntity<InputStreamResource> downloadPickersPdf(@PathVariable String key) throws IOException {
        String pass = env.getProperty("api.pass");
        if (pass.equals(key)) {
            String pdfPath = "src\\main\\resources\\downloads\\pickersRaport.pdf";
            String fileName = "pickersRaport.pdf";
            File check = new File(pdfPath);
            if (check.exists()) {
                check.delete();
            }
            List<FruitPicker> fruitPickers = fruitPickerService.findAll();
            PickersToPdfWriter.writeRaport(fruitPickers, pdfPath, fruitTypeService, env);

            log.info("Calling Download:- " + fileName);
            File file = new File("src\\main\\resources\\downloads\\" + fileName);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment;filename=" + file.getName())
                    .contentType(MediaType.APPLICATION_PDF).contentLength(file.length())
                    .body(resource);
        } else {
            return null;
        }
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

    private List<?> getDataStrings(String key, List<?> list) {
        String pass = env.getProperty("api.pass");
        if (pass.equals(key)) {
            return list;
        }
        return List.of();
    }

}
