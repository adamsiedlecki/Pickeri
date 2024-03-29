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
import pl.adamsiedlecki.Pickeri.web.tab.settingsTabs.GeneralSettingsTab;

@SpringUI(path = "/settings")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${settings.title}")
public class SettingsUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private final MenuTab othersTab;
    private final Environment environment;
    private final GeneralSettingsTab generalSettingsTab;
    private final SettingsService settingsService;

    @Autowired
    public SettingsUI(MenuTab othersTab, Environment environment, GeneralSettingsTab generalSettingsTab, SettingsService settingsService) {
        this.othersTab = othersTab;
        this.environment = environment;
        this.generalSettingsTab = generalSettingsTab;
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
        tabs.addTab(generalSettingsTab, environment.getProperty("general.settings.tab"));
        tabs.addTab(othersTab, environment.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }

}
