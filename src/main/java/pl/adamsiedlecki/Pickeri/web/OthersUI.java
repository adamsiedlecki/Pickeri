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

@SpringUI(path="/other")
public class OthersUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AddFruitVarietyTab fruitVarietyTab;
    private QRCodeGeneratorTab qrCodeGeneratorTab;
    private OthersTab othersTab;
    private AddPickerTab addPickerTab;

    @Autowired
    public OthersUI(AddFruitVarietyTab  addFruitVarietyTab, QRCodeGeneratorTab qrCodeGeneratorTab, OthersTab othersTab,
                    AddPickerTab addPickerTab){
        this.addPickerTab = addPickerTab;
        this.qrCodeGeneratorTab = qrCodeGeneratorTab;
        this.fruitVarietyTab = addFruitVarietyTab;
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
        tabs.addTab(fruitVarietyTab,"Dodaj odmianę owocu");
        tabs.addTab(addPickerTab,"Dodaj pracownika");
        tabs.addTab(qrCodeGeneratorTab,"Wygeneruj kody QR");
        tabs.addTab(othersTab,"Reszta");
        root.addComponent(tabs);
    }

}
