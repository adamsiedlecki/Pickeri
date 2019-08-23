package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    public MainUI(AddDeliveryTab addDeliveryTab, RankingTab rankingTab, FindPickerTab findPickerTab,
                  AddPickerTab addPickerTab, AllDeliveriesTab allDeliveriesTab){
        this.addDeliveryTab = addDeliveryTab;
        this.rankingTab = rankingTab;
        this.findPickerTab = findPickerTab;
        this.addPickerTab = addPickerTab;
        this.allDeliveriesTab = allDeliveriesTab;
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

        tabs.addTab(rankingTab,"Ranking");
        rankingTab.addLayoutClickListener(e->{rankingTab.refreshData();});

        tabs.addTab(findPickerTab,"Szukaj pracownika");

        tabs.addTab(addPickerTab,"Dodaj pracownika");

        tabs.addTab(allDeliveriesTab,"Wszystkie dostawy");
        allDeliveriesTab.addLayoutClickListener(e->allDeliveriesTab.refreshGrid());

        root.addComponent(tabs);
    }

}
