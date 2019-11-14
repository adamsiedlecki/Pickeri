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
import pl.adamsiedlecki.Pickeri.web.tab.independentTabs.AddDeliveryTab;
import pl.adamsiedlecki.Pickeri.web.tab.independentTabs.MenuTab;

@SpringUI
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${add.delivery.title}")
public class MainUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AddDeliveryTab addDeliveryTab;
    private MenuTab othersTab;
    private Environment environment;
    private SettingsService settingsService;

    @Autowired
    public MainUI(AddDeliveryTab addDeliveryTab, MenuTab othersTab, Environment environment, SettingsService settingsService) {
        this.addDeliveryTab = addDeliveryTab;
        this.othersTab = othersTab;
        this.environment = environment;
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
        tabs.addTab(addDeliveryTab, environment.getProperty("add.fruits.tab"));
        tabs.addTab(othersTab, environment.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }

}
