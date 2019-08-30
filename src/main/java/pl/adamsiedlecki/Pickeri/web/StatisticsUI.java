package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.web.tabs.*;

import java.io.File;

@SpringUI(path="/statistics-and-info")
public class StatisticsUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private RankingTab rankingTab;
    private FindPickerTab findPickerTab;
    private AllDeliveriesTab allDeliveriesTab;
    private StatisticsTab statisticsTab;
    private OthersTab othersTab;

    @Autowired
    public StatisticsUI( RankingTab rankingTab, FindPickerTab findPickerTab,
                         AllDeliveriesTab allDeliveriesTab, StatisticsTab statisticsTab, OthersTab othersTab){
        this.rankingTab = rankingTab;
        this.findPickerTab = findPickerTab;
        this.allDeliveriesTab = allDeliveriesTab;
        this.statisticsTab = statisticsTab;
        this.othersTab = othersTab;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.addComponent(new Embedded("", new FileResource(new File("src\\main\\resources\\images\\pickeri.png"))));
        addTabs();
        this.setContent(root);
    }

    private void addTabs(){
        tabs = new TabSheet();
        tabs.addTab(rankingTab,"Ranking");
        tabs.addTab(findPickerTab,"Szukaj pracownika");
        tabs.addTab(allDeliveriesTab,"Wszystkie dostawy");
        tabs.addTab(statisticsTab,"Statystyki");
        tabs.addTab(othersTab,"Reszta");
        root.addComponent(tabs);
    }

}
