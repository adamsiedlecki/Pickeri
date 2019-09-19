package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.OthersTab;
import pl.adamsiedlecki.Pickeri.web.tabs.statisticsTabs.FindPickerTab;
import pl.adamsiedlecki.Pickeri.web.tabs.statisticsTabs.StatisticsTab;

@SpringUI(path = "/statistics-and-info")
public class StatisticsUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private FindPickerTab findPickerTab;
    private StatisticsTab statisticsTab;
    private OthersTab othersTab;

    @Autowired
    public StatisticsUI(FindPickerTab findPickerTab, StatisticsTab statisticsTab,
                        OthersTab othersTab) {
        this.findPickerTab = findPickerTab;
        this.statisticsTab = statisticsTab;
        this.othersTab = othersTab;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.addComponent(new Embedded("", new FileResource(ResourceGetter.getPickeriLogo())));
        addTabs();
        this.setContent(root);
    }

    private void addTabs() {
        tabs = new TabSheet();
        tabs.addTab(findPickerTab, "Szukaj pracownika");
        tabs.addTab(statisticsTab, "Statystyki");
        tabs.addTab(othersTab, "Reszta");
        root.addComponent(tabs);
    }

}
