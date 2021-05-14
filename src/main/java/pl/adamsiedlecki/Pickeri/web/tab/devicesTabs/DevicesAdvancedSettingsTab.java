package pl.adamsiedlecki.Pickeri.web.tab.devicesTabs;

import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.Device;
import pl.adamsiedlecki.Pickeri.entity.DeviceController;
import pl.adamsiedlecki.Pickeri.service.DeviceControllerService;
import pl.adamsiedlecki.Pickeri.service.DeviceService;

import java.util.Optional;

@Component
@Scope("prototype")
public class DevicesAdvancedSettingsTab extends VerticalLayout {

    private final DeviceControllerService deviceControllerService;
    private final DeviceService deviceService;
    private final Environment env;

    @Autowired
    public DevicesAdvancedSettingsTab(Environment env, DeviceControllerService deviceControllerService,
                                      DeviceService deviceService) {
        this.deviceService = deviceService;
        this.deviceControllerService = deviceControllerService;
        this.env = env;
        HorizontalLayout root = new HorizontalLayout();
        root.addComponents(getControllerListPanel(), getAddDevicePanel(), getAddControllerPanel(), getDeleteControllerPanel());
        this.addComponent(root);
    }

    private Panel getControllerListPanel(){
        Panel panel = new Panel(env.getProperty("devices.controllers.list.panel"));
        panel.setWidth(720, Unit.PIXELS);
        panel.setHeight(600, Unit.PIXELS);
        VerticalLayout root = new VerticalLayout();
        Button refreshButton = new Button(env.getProperty("refresh.button"));
        Grid<DeviceController> controllersGrid = new Grid<>();
        controllersGrid.addColumn(DeviceController::getId).setCaption(env.getProperty("id.column","ID"));
        controllersGrid.addColumn(DeviceController::getName).setCaption(env.getProperty("name.column","name"));
        controllersGrid.addColumn(DeviceController::getAddress).setCaption(env.getProperty("controller.address.field","address"));
        controllersGrid.addColumn(DeviceController::getDevices).setCaption(env.getProperty("devices.list","devices"));
        controllersGrid.setWidth(95, Unit.PERCENTAGE);
        root.addComponents(refreshButton, controllersGrid);
        refreshButton.addClickListener(e->controllersGrid.setItems(deviceControllerService.findAll()));
        refreshButton.click();
        panel.setContent(root);
        root.forEach(component -> root.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
        return panel;
    }

    private Panel getAddDevicePanel(){
        Panel panel = new Panel(env.getProperty("add.device.panel"));
        panel.setWidth(190, Unit.PIXELS);
        panel.setHeight(320, Unit.PIXELS);
        VerticalLayout root = new VerticalLayout();
        TextField deviceNameField = new TextField(env.getProperty("name.field"));
        TextField devicePinField = new TextField(env.getProperty("device.pin.field"));
        TextField controllerIdField = new TextField(env.getProperty("controller.id.field"));
        Button saveButton = new Button(env.getProperty("save.button"));
        saveButton.addClickListener(event -> {
            if(NumberUtils.isDigits(devicePinField.getValue()) && NumberUtils.isDigits(controllerIdField.getValue())){
                Optional<DeviceController> deviceController = deviceControllerService.findById(Long.parseLong(controllerIdField.getValue()));
                if(deviceController.isPresent()){
                    deviceService.save(new Device(Integer.parseInt(devicePinField.getValue()), deviceNameField.getValue(),deviceController.get()));
                    deviceNameField.clear();
                    devicePinField.clear();
                    controllerIdField.clear();
                }else{
                    Notification.show(env.getProperty("cannot.find.controller.with.id"), Notification.Type.ERROR_MESSAGE);
                }

            }else{
                Notification.show(env.getProperty("not.numeric.values.notification"));
            }
        });
        root.addComponents(deviceNameField, devicePinField,controllerIdField, saveButton);
        panel.setContent(root);
        root.forEach(component -> root.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
        return panel;
    }

    private Panel getAddControllerPanel(){
        Panel panel = new Panel(env.getProperty("add.controller.panel"));
        panel.setWidth(190, Unit.PIXELS);
        panel.setHeight(320, Unit.PIXELS);
        VerticalLayout root = new VerticalLayout();
        TextField controllerNameField = new TextField(env.getProperty("name.field"));
        PasswordField controllerPasswordField = new PasswordField(env.getProperty("controller.password.field"));
        TextField controllerAddressField = new TextField(env.getProperty("controller.address.field"));
        Button saveButton = new Button(env.getProperty("save.button"));
        saveButton.addClickListener(e->{
            if(!controllerNameField.isEmpty()&&!controllerAddressField.isEmpty()){
                DeviceController deviceController = new DeviceController(controllerNameField.getValue(),
                        controllerPasswordField.getValue(), controllerAddressField.getValue());
                deviceControllerService.save(deviceController);
                controllerNameField.clear();
                controllerPasswordField.clear();
                controllerAddressField.clear();
            }
        });
        root.addComponents(controllerNameField, controllerPasswordField,controllerAddressField, saveButton);
        panel.setContent(root);
        root.forEach(component -> root.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
        return panel;
    }

    private Panel getDeleteControllerPanel(){
        Panel panel = new Panel(env.getProperty("delete.controller.panel"));
        panel.setWidth(190, Unit.PIXELS);
        panel.setHeight(320, Unit.PIXELS);
        VerticalLayout root = new VerticalLayout();
        TextField controllerIdField = new TextField(env.getProperty("controller.id.field"));
        Button saveButton = new Button(env.getProperty("save.button"));
        saveButton.addClickListener(e->{
            if(!controllerIdField.isEmpty()&&NumberUtils.isDigits(controllerIdField.getValue())){
                deviceControllerService.deleteById(Long.parseLong(controllerIdField.getValue()));
                controllerIdField.clear();
            }
        });
        saveButton.setStyleName(ValoTheme.BUTTON_DANGER);
        root.addComponents(controllerIdField, saveButton);
        panel.setContent(root);
        root.forEach(component -> root.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
        return panel;
    }

}
