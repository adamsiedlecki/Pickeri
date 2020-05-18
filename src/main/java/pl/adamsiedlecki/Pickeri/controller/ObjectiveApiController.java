package pl.adamsiedlecki.Pickeri.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.adamsiedlecki.Pickeri.entity.*;
import pl.adamsiedlecki.Pickeri.service.*;

import java.util.List;

import static com.github.jaiimageio.impl.plugins.tiff.TIFFFaxCompressor.pass;

@RestController
@RequestMapping("/api/v2")
public class ObjectiveApiController {

    private final FruitPickerService fruitPickerService;
    private final FruitDeliveryService fruitDeliveryService;
    private final DeviceService deviceService;
    private final DeviceControllerService deviceControllerService;
    private final ExpenseService expenseService;
    private final FruitTypeService fruitTypeService;
    private final FruitVarietyService fruitVarietyService;
    private final NoteService noteService;
    private final SettingsService settingsService;
    private final PickeriUserDetailsService userDetailsService;
    private final WorkTimeService workTimeService;
    private static final Logger log = LoggerFactory.getLogger(ApiController.class);
    private final Environment env;

    @Autowired
    public ObjectiveApiController(FruitPickerService pickerService, FruitDeliveryService fruitDeliveryService, Environment environment,
                                  DeviceService deviceService, DeviceControllerService deviceControllerService, ExpenseService expenseService,
                                  FruitTypeService fruitTypeService, FruitVarietyService fruitVarietyService, NoteService noteService,
                                  SettingsService settingsService, PickeriUserDetailsService userDetailsService,
                                  WorkTimeService workTimeService) {
        this.workTimeService = workTimeService;
        this.userDetailsService = userDetailsService;
        this.settingsService = settingsService;
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

    @GetMapping(value = "/get-work-time/{key}")
    public List<WorkTime> getWorkTimeList(@PathVariable String key) {
        return getDataObjects(key, workTimeService.findAll());
    }

    @GetMapping(value = "/get-users/{key}")
    public List<User> getUsers(@PathVariable String key) {
        return getDataObjects(key, userDetailsService.findAll());
    }

    @GetMapping(value = "/get-settings/{key}")
    public List<SettingsEntity> getSettings(@PathVariable String key) {
        return getDataObjects(key, settingsService.findAll());
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

    @PostMapping(value = "/post-delivery/{key}")
    public void getDeliveries(@PathVariable FruitDelivery fruitDelivery, @PathVariable String key) {
        if (pass.equals(key)) {
            fruitDeliveryService.addDelivery(fruitDelivery);
        }
    }

    @GetMapping(value = "/get-user-by-username/{username}/{key}")
    public Object getUser(@PathVariable String username, @PathVariable String key) {
        UserDetails user = userDetailsService.loadUserByUsername(username);
        return getDataObjects(key, List.of(user));
    }

    private <T> List<T> getDataObjects(String key, List<T> objects) {
        String pass = env.getProperty("api.pass");
        if (pass.equals(key)) {
            return objects;
        }
        return List.of();
    }

}
