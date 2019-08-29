package pl.adamsiedlecki.Pickeri.web;


import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import org.apache.catalina.webresources.FileResource;
import org.springframework.beans.factory.annotation.Autowired;

import pl.adamsiedlecki.Pickeri.web.tabs.*;

import java.io.File;

@Route(value="")
public class MainUI extends VerticalLayout {

    private Tabs tabs;
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


    public MainUI() {
        root = new VerticalLayout();

        root.add(new Image( "src\\main\\resources\\images\\pickeri.png","logo picture"));
        addTabs();

        this.add(root);
    }

    private void addTabs(){
        tabs = new Tabs();

        tabs.add(addDeliveryTab); // ,"Dodaj owoce"
        //addDeliveryTab.addLayoutClickListener(x->addDeliveryTab.refreshVarieties());

        tabs.add(rankingTab); // ,"Ranking"

        tabs.add(findPickerTab); // ,"Szukaj pracownika"

        tabs.add(addPickerTab); // ,"Dodaj pracownika"

        tabs.add(allDeliveriesTab); // ,"Wszystkie dostawy"

        tabs.add(statisticsTab); // ,"Statystyki"

        tabs.add(fruitVarietyTab); // ,"Dodaj odmianę owocu"

        root.add(tabs);
    }

}
