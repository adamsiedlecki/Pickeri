package pl.adamsiedlecki.Pickeri.web.ui;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.service.SettingsService;
import pl.adamsiedlecki.Pickeri.tools.UserInterfaceTools.AlignmentSetter;
import pl.adamsiedlecki.Pickeri.tools.UserInterfaceTools.HeaderAdder;
import pl.adamsiedlecki.Pickeri.tools.UserInterfaceTools.ThemeSetter;
import pl.adamsiedlecki.Pickeri.web.tab.devicesTabs.DeviceControllerTab;
import pl.adamsiedlecki.Pickeri.web.tab.devicesTabs.DevicesAdvancedSettingsTab;
import pl.adamsiedlecki.Pickeri.web.tab.independentTabs.MenuTab;

@SpringUI(path = "/devices-controller")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${devices.controller.title}")
public class DevicesControllerUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private Environment env;
    private DeviceControllerTab deviceControllerTab;
    private DevicesAdvancedSettingsTab devicesAdvancedSettingsTab;
    private MenuTab menuTab;
    private SettingsService settingsService;

    @Autowired
    public DevicesControllerUI(Environment environment, DeviceControllerTab deviceControllerTab,
                               DevicesAdvancedSettingsTab devicesAdvancedSettingsTab, MenuTab menuTab, SettingsService settingsService) {
        this.menuTab = menuTab;
        this.deviceControllerTab = deviceControllerTab;
        this.devicesAdvancedSettingsTab = devicesAdvancedSettingsTab;
        this.env = environment;
        this.settingsService = settingsService;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        HeaderAdder.add(root, settingsService);
        addTabs();
        AlignmentSetter.apply(root, tabs);
        this.setContent(root);
        ThemeSetter.set(this);
    }

    private void addTabs() {
        tabs = new TabSheet();
        tabs.addTab(deviceControllerTab, env.getProperty("devices.controller.title"));
        tabs.addTab(devicesAdvancedSettingsTab, env.getProperty("advanced.device.settings"));
        tabs.addTab(menuTab, env.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }

}
