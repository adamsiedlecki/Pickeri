package pl.adamsiedlecki.Pickeri.web.tabs.devicesTabs;

import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class DeviceControllerTab extends VerticalLayout {

    public DeviceControllerTab(){
        this.addComponent(new Label("NOT SUPPORTED YET"));
    }

}
