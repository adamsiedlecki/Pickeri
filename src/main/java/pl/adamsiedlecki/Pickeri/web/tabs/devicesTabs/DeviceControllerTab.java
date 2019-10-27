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
                    if(device.getDeviceController()!=null){
                        Panel panel = new Panel(env.getProperty("device.id")+": "+device.getId());
                        VerticalLayout panelRoot = new VerticalLayout();
                        panelRoot.addComponent(new Label(device.getName()));
                        Label controllerName = new Label(env.getProperty("controller")+": "
                                +device.getDeviceController().getName());
                        panelRoot.addComponent(controllerName);
                        Button startButton = new Button(env.getProperty("on"));
                        Button stopButton = new Button(env.getProperty("off"));
                        startButton.addClickListener(ev->{
                            device.start();
                            setIcons(device, startButton, stopButton, panel);
                        });
                        stopButton.addClickListener(event->{
                            device.stop();
                            setIcons(device, startButton, stopButton, panel);
                        });
                        setIcons(device, startButton, stopButton, panel);
                        panelRoot.addComponents(startButton, stopButton);
                        panelRoot.forEach(component -> panelRoot.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
                        panel.setContent(panelRoot);
                        panel.setWidth(220, Unit.PIXELS);
                        panel.setHeight(320, Unit.PIXELS);
                        panels[i] = panel;
                        i++;
                    }
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

    private void setIcons(Device device, Button startButton, Button stopButton, Panel panel){
        if(device.isEnabled()){
            startButton.setIcon(VaadinIcons.CHECK_CIRCLE);
            stopButton.setIcon(null);
        }else {
            stopButton.setIcon(VaadinIcons.CLOSE_SMALL);
            startButton.setIcon(null);
        }
        if(device.isAlive()){
            panel.setIcon(VaadinIcons.BULLSEYE);
        }else{
            panel.setIcon(VaadinIcons.EXCLAMATION_CIRCLE);
        }
    }


}
