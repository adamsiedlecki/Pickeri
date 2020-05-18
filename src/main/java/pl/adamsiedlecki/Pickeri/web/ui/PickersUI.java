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
import pl.adamsiedlecki.Pickeri.web.tab.employeesTab.AddPickerTab;
import pl.adamsiedlecki.Pickeri.web.tab.employeesTab.DeletePickerTab;
import pl.adamsiedlecki.Pickeri.web.tab.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tab.statisticsTabs.FindPickerTab;

@SpringUI(path = "/other")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${other.title}")
public class PickersUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private final MenuTab othersTab;
    private final AddPickerTab addPickerTab;
    private final Environment env;
    private final SettingsService settingsService;
    private final DeletePickerTab deletePickerTab;
    private final FindPickerTab findPickerTab;

    @Autowired
    public PickersUI(FindPickerTab findPickerTab, MenuTab othersTab, AddPickerTab addPickerTab, DeletePickerTab deletePickerTab, Environment environment, SettingsService settingsService) {
        this.addPickerTab = addPickerTab;
        this.othersTab = othersTab;
        this.env = environment;
        this.settingsService = settingsService;
        this.deletePickerTab = deletePickerTab;
        this.findPickerTab = findPickerTab;
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
        tabs.addTab(findPickerTab, env.getProperty("search.for.employee.tab"));
        tabs.addTab(addPickerTab, env.getProperty("add.picker.tab"));
        tabs.addTab(deletePickerTab, env.getProperty("delete.employee"));
        tabs.addTab(othersTab, env.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }
}
