package pl.adamsiedlecki.Pickeri.web.ui;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.service.SettingsService;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.AlignmentSetter;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.HeaderAdder;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.ThemeSetter;
import pl.adamsiedlecki.Pickeri.web.tab.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tab.othersUITabs.*;

@SpringUI(path = "/other")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${other.title}")
public class PickersUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private MenuTab othersTab;
    private AddPickerTab addPickerTab;
    private Environment env;
    private SettingsService settingsService;

    @Autowired
    public PickersUI(MenuTab othersTab, AddPickerTab addPickerTab, Environment environment, SettingsService settingsService) {
        this.addPickerTab = addPickerTab;
        this.othersTab = othersTab;
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
        tabs.addTab(addPickerTab, env.getProperty("add.picker.tab"));
        tabs.addTab(othersTab, env.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }

}
