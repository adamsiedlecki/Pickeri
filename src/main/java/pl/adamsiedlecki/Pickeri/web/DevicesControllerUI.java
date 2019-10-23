package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.tools.AlignmentSetter;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.devicesTabs.DeviceControllerTab;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tabs.othersUITabs.AddPickerTab;

@SpringUI(path = "/devices-controller")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${devices.controller.title}")
public class DevicesControllerUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private Environment env;
    private DeviceControllerTab deviceControllerTab;

    @Autowired
    public DevicesControllerUI(Environment environment, DeviceControllerTab deviceControllerTab) {
        this.env = environment;
        this.deviceControllerTab = deviceControllerTab;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        Embedded pickeriLogo = ResourceGetter.getPickeriLogoAsEmbeddedComponent();
        root.addComponent(pickeriLogo);
        addTabs();
        AlignmentSetter.apply(root, pickeriLogo, tabs);
        this.setContent(root);
    }

    private void addTabs() {
        tabs = new TabSheet();
        tabs.addTab(deviceControllerTab, env.getProperty("devices.controller.title"));
        root.addComponent(tabs);
    }

}
