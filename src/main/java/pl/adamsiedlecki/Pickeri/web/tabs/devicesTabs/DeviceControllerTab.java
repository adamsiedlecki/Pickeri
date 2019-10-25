package pl.adamsiedlecki.Pickeri.web.tabs.devicesTabs;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.Device;
import pl.adamsiedlecki.Pickeri.service.DeviceService;

import java.util.List;

@Component
@Scope("prototype")
public class DeviceControllerTab extends VerticalLayout {

    private DeviceService deviceService;

    public DeviceControllerTab(Environment env, DeviceService deviceService){
        this.deviceService = deviceService;
        Button refreshButton = new Button(env.getProperty("refresh.button"));
        VerticalLayout root = new VerticalLayout();
        this.addComponents(refreshButton, root);
        refreshButton.addClickListener(e->{
            root.removeAllComponents();
            List<Device> devices = deviceService.findAll();

            int amountOfPanelsInRow = 5;
            Panel[] panels = new Panel[amountOfPanelsInRow];
            int i = 0;
            int j = amountOfPanelsInRow-1;
            for(Device device : devices){
                if(i<j){
                    Panel panel = new Panel(env.getProperty("device.id")+": "+device.getId());
                    VerticalLayout panelRoot = new VerticalLayout();
                    panelRoot.addComponent(new Label(device.getName()));
                    if(device.getDeviceController()!=null){
                        panelRoot.addComponent(new Label(env.getProperty("controller")+": "+device.getDeviceController().getName()));
                    }else{
                        panelRoot.addComponent(new Label("cannot recognise controller"));
                    }
                    Button startButton = new Button(env.getProperty("on"));
                    startButton.addClickListener(ev->{
                        device.start();
                    });
                    Button stopButton = new Button(env.getProperty("off"));
                    stopButton.addClickListener(event->{
                        device.stop();
                    });
                    if(device.isEnabled()){
                        startButton.setIcon(VaadinIcons.BULLSEYE);
                    }else {
                        stopButton.setIcon(VaadinIcons.CLOSE_SMALL);
                    }
                    panelRoot.addComponents(startButton, stopButton);
                    panelRoot.forEach(component -> panelRoot.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
                    panel.setContent(panelRoot);
                    panel.setWidth(220, Unit.PIXELS);
                    panel.setHeight(320, Unit.PIXELS);
                    panels[i] = panel;
                    i++;
                }else{
                    addPanels(panels, root);
                    panels = new Panel[amountOfPanelsInRow];
                    i=0;
                }
            }
            addPanels(panels, root);
        });
        this.setMargin(true);
        root.forEach(component -> root.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
        this.forEach(component -> this.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
        refreshButton.click();
    }

    private void addPanels(Panel[] panels, Layout root){
        HorizontalLayout row = new HorizontalLayout();
        for(Panel panel : panels){
            if(panel!=null){
                row.addComponent(panel);
            }
        }
        row.setWidth(94, Unit.PERCENTAGE);
        row.forEach(component -> row.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
        root.addComponent(row);
    }


}
