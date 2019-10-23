package pl.adamsiedlecki.Pickeri.web.tabs.devicesTabs;

import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.DeviceController;

@Component
@Scope("prototype")
public class DevicesAdvancedSettingsTab extends VerticalLayout {

    @Autowired
    public DevicesAdvancedSettingsTab(Environment env){
        HorizontalLayout root = new HorizontalLayout();
        root.addComponents(getAddDevicePanel(env), getAddControllerPanel(env));
        this.addComponent(root);
    }

    private Panel getControllerListPanel(Environment env){
        Panel panel = new Panel(env.getProperty("add.device.panel"));
        panel.setWidth(240, Unit.PIXELS);
        VerticalLayout root = new VerticalLayout();
        Button refreshButton = new Button(env.getProperty("refresh.button"));
        Grid<DeviceController> controllersGrid = new Grid<>();
        controllersGrid.addColumn(DeviceController::getId).setCaption(env.getProperty("id.column","ID"));
        root.addComponents(refreshButton, controllersGrid);
        panel.setContent(root);
        root.forEach(component -> root.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
        return panel;
    }

    private Panel getAddDevicePanel(Environment env){
        Panel panel = new Panel(env.getProperty("add.device.panel"));
        panel.setWidth(190, Unit.PIXELS);
        VerticalLayout root = new VerticalLayout();
        TextField deviceNameField = new TextField(env.getProperty("name.field"));
        TextField devicePinField = new TextField(env.getProperty("device.pin.field"));
        TextField controllerIdField = new TextField(env.getProperty("controller.id.field"));
        Button saveButton = new Button(env.getProperty("save.button"));
        root.addComponents(deviceNameField, devicePinField,controllerIdField, saveButton);
        panel.setContent(root);
        root.forEach(component -> root.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
        return panel;
    }

    private Panel getAddControllerPanel(Environment env){
        Panel panel = new Panel(env.getProperty("add.controller.panel"));
        panel.setWidth(190, Unit.PIXELS);
        VerticalLayout root = new VerticalLayout();
        TextField controllerNameField = new TextField(env.getProperty("name.field"));
        PasswordField controllerPasswordField = new PasswordField(env.getProperty("controller.password.field"));
        TextField controllerAddressField = new TextField(env.getProperty("controller.address.field"));
        Button saveButton = new Button(env.getProperty("save.button"));
        root.addComponents(controllerNameField, controllerPasswordField,controllerAddressField, saveButton);
        panel.setContent(root);
        root.forEach(component -> root.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
        return panel;
    }

}
