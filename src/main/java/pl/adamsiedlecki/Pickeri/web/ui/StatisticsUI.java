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
import pl.adamsiedlecki.Pickeri.web.tab.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tab.statisticsTabs.FindPickerTab;
import pl.adamsiedlecki.Pickeri.web.tab.statisticsTabs.StatisticsTab;

@SpringUI(path = "/statistics-and-info")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${statistics.title}")
public class StatisticsUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private FindPickerTab findPickerTab;
    private StatisticsTab statisticsTab;
    private MenuTab othersTab;
    private Environment env;
    private SettingsService settingsService;

    @Autowired
    public StatisticsUI(FindPickerTab findPickerTab, StatisticsTab statisticsTab,
                        MenuTab othersTab, Environment environment, SettingsService settingsService) {
        this.findPickerTab = findPickerTab;
        this.statisticsTab = statisticsTab;
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
        tabs.addTab(findPickerTab, env.getProperty("search.for.employee.tab"));
        tabs.addTab(statisticsTab, env.getProperty("statistics.tab"));
        tabs.addTab(othersTab, env.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }

}
