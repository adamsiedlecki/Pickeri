package pl.adamsiedlecki.Pickeri.web.tabs.devicesTabs;

import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DevicesAdvancedSettingsTab extends VerticalLayout {

    @Autowired
    public DevicesAdvancedSettingsTab(Environment env){
        this.addComponent(getAddDevicePanel(env));
    }

    private Panel getAddDevicePanel(Environment env){
        Panel panel = new Panel(env.getProperty("add.device.panel"));
        VerticalLayout root = new VerticalLayout();
        TextField deviceNameField = new TextField(env.getProperty("name.field"));
        TextField devicePinField = new TextField(env.getProperty("device.pin.field"));
        Button saveButton = new Button(env.getProperty("save.button"));

        root.addComponents(deviceNameField, devicePinField, saveButton);
        panel.setContent(root);
        return panel;
    }

}
