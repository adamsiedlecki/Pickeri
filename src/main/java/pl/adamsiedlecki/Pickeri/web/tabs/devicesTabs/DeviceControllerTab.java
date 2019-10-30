package pl.adamsiedlecki.Pickeri.web.tabs.devicesTabs;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.Device;
import pl.adamsiedlecki.Pickeri.service.DeviceService;
import pl.adamsiedlecki.Pickeri.tools.net.PingTest;

import java.util.List;

@Component
@Scope("prototype")
public class DeviceControllerTab extends VerticalLayout {

    private DeviceService deviceService;
    private static final Logger log = LoggerFactory.getLogger(DeviceControllerTab.class);

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
                            setButtonIcons(device, startButton, stopButton);
                        });
                        stopButton.addClickListener(event->{
                            device.stop();
                            setButtonIcons(device, startButton, stopButton);
                        });
                        setButtonIcons(device, startButton, stopButton);
                        setPanelIcons(device, panel);
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

    private void setButtonIcons(Device device, Button startButton, Button stopButton){
        Thread isEnabledTest = new IsEnabledTestRunnable(startButton, stopButton, device);
        isEnabledTest.start();
    }

    private void setPanelIcons(Device device, Panel panel){
        Thread pingTestThread = new PingTestRunnable(panel, device);
        pingTestThread.start();
    }

    private static class PingTestRunnable extends Thread{

        private Panel panel;
        private Device device;

        public PingTestRunnable(Panel panel, Device device){
            this.panel = panel;
            this.device = device;
        }

        @Override
        public void run() {
            PingTest pingTest = new PingTest();
            if(pingTest.isAlive(device)){
                panel.setIcon(VaadinIcons.BULLSEYE);
            }else{
                panel.setIcon(VaadinIcons.EXCLAMATION_CIRCLE);
            }
        }
    }

    private class IsEnabledTestRunnable extends Thread{

        private Button startButton;
        private Button stopButton;
        private Device device;

        public IsEnabledTestRunnable(Button startButton, Button stopButton, Device device){
            this.stopButton = stopButton;
            this.startButton = startButton;
            this.device = device;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000);
                if(device.isEnabled()){
                    startButton.setIcon(VaadinIcons.CHECK_CIRCLE);
                    stopButton.setIcon(null);
                    //DeviceControllerTab.this.getUI().getPage().reload();
                }else {
                    stopButton.setIcon(VaadinIcons.CLOSE_SMALL);
                    startButton.setIcon(null);
                    //DeviceControllerTab.this.getUI().getPage().reload();
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }


}
