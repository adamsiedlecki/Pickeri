package pl.adamsiedlecki.Pickeri.web;

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
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tabs.statisticsTabs.FindPickerTab;
import pl.adamsiedlecki.Pickeri.web.tabs.statisticsTabs.StatisticsTab;

@SpringUI(path = "/statistics-and-info")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${statistics.title}")
public class StatisticsUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private FindPickerTab findPickerTab;
    private StatisticsTab statisticsTab;
    private MenuTab othersTab;
    private Environment env;

    @Autowired
    public StatisticsUI(FindPickerTab findPickerTab, StatisticsTab statisticsTab,
                        MenuTab othersTab, Environment environment) {
        this.findPickerTab = findPickerTab;
        this.statisticsTab = statisticsTab;
        this.othersTab = othersTab;
        this.env = environment;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.addComponent(ResourceGetter.getPickeriLogoAsEmbeddedComponent());
        addTabs();
        this.setContent(root);
    }

    private void addTabs() {
        tabs = new TabSheet();
        tabs.addTab(findPickerTab, env.getProperty("search.for.employee.tab"));
        tabs.addTab(statisticsTab, env.getProperty("statistics.tab"));
        tabs.addTab(othersTab, env.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }

}
