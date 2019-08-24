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

@SpringUI
public class MainUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AddDeliveryTab addDeliveryTab;
    private RankingTab rankingTab;
    private FindPickerTab findPickerTab;
    private AddPickerTab addPickerTab;
    private AllDeliveriesTab allDeliveriesTab;
    private StatisticsTab statisticsTab;
    private AddFruitVarietyTab fruitVarietyTab;

    @Autowired
    public MainUI(AddDeliveryTab addDeliveryTab, RankingTab rankingTab, FindPickerTab findPickerTab,
                  AddPickerTab addPickerTab, AllDeliveriesTab allDeliveriesTab, StatisticsTab statisticsTab,
    AddFruitVarietyTab  addFruitVarietyTab){
        this.addDeliveryTab = addDeliveryTab;
        this.rankingTab = rankingTab;
        this.findPickerTab = findPickerTab;
        this.addPickerTab = addPickerTab;
        this.allDeliveriesTab = allDeliveriesTab;
        this.statisticsTab = statisticsTab;
        this.fruitVarietyTab = addFruitVarietyTab;
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

        tabs.addTab(addDeliveryTab,"Dodaj owoce");
        //addDeliveryTab.addLayoutClickListener(x->addDeliveryTab.refreshVarieties());

        tabs.addTab(rankingTab,"Ranking");

        tabs.addTab(findPickerTab,"Szukaj pracownika");

        tabs.addTab(addPickerTab,"Dodaj pracownika");

        tabs.addTab(allDeliveriesTab,"Wszystkie dostawy");

        tabs.addTab(statisticsTab,"Statystyki");

        tabs.addTab(fruitVarietyTab,"Dodaj odmianę owocu");

        root.addComponent(tabs);
    }

}
