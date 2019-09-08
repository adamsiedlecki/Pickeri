package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.*;

import java.io.File;

@SpringUI(path="/other")
public class OthersUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AddFruitVarietyTab fruitVarietyTab;
    private QRCodeGeneratorTab qrCodeGeneratorTab;
    private OthersTab othersTab;
    private AddPickerTab addPickerTab;
    private AddDeleteTypeTab addDeleteTypeTab;

    @Autowired
    public OthersUI(AddFruitVarietyTab  addFruitVarietyTab, QRCodeGeneratorTab qrCodeGeneratorTab, OthersTab othersTab,
                    AddPickerTab addPickerTab, AddDeleteTypeTab addDeleteTypeTab){
        this.addDeleteTypeTab = addDeleteTypeTab;
        this.addPickerTab = addPickerTab;
        this.qrCodeGeneratorTab = qrCodeGeneratorTab;
        this.fruitVarietyTab = addFruitVarietyTab;
        this.othersTab = othersTab;
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
        tabs.addTab(fruitVarietyTab,"Dodaj odmianę owocu");
        tabs.addTab(addPickerTab,"Dodaj pracownika");
        tabs.addTab(addDeleteTypeTab,"Dodaj / usuń typ owocu");
        tabs.addTab(qrCodeGeneratorTab,"Wygeneruj kody QR");
        tabs.addTab(othersTab,"Reszta");
        root.addComponent(tabs);
    }

}
