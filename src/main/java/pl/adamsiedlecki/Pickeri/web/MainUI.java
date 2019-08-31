package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.*;

import java.io.File;

@SpringUI
public class MainUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AddDeliveryTab addDeliveryTab;
    private OthersTab othersTab;

    @Autowired
    public MainUI(AddDeliveryTab addDeliveryTab,  OthersTab othersTab){
        this.addDeliveryTab = addDeliveryTab;this.othersTab = othersTab;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.addComponent(new Embedded("", new FileResource(ResourceGetter.getPickeriLogo())));
        addTabs();
        this.setContent(root);
    }

    private void addTabs(){
        tabs = new TabSheet();
        tabs.addTab(addDeliveryTab,"Dodaj owoce");
        tabs.addTab(othersTab,"Reszta");
        root.addComponent(tabs);
    }

}
