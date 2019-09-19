package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.OthersUITabs.AddDeleteTypeTab;
import pl.adamsiedlecki.Pickeri.web.tabs.OthersUITabs.AddFruitVarietyTab;
import pl.adamsiedlecki.Pickeri.web.tabs.OthersUITabs.AddPickerTab;
import pl.adamsiedlecki.Pickeri.web.tabs.OthersUITabs.PdfDocumentsGeneratorTab;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.OthersTab;

@SpringUI(path="/other")
public class OthersUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AddFruitVarietyTab fruitVarietyTab;
    private PdfDocumentsGeneratorTab pdfDocumentsGeneratorTab;
    private OthersTab othersTab;
    private AddPickerTab addPickerTab;
    private AddDeleteTypeTab addDeleteTypeTab;

    @Autowired
    public OthersUI(AddFruitVarietyTab addFruitVarietyTab, PdfDocumentsGeneratorTab pdfDocumentsGeneratorTab, OthersTab othersTab,
                    AddPickerTab addPickerTab, AddDeleteTypeTab addDeleteTypeTab){
        this.addDeleteTypeTab = addDeleteTypeTab;
        this.addPickerTab = addPickerTab;
        this.pdfDocumentsGeneratorTab = pdfDocumentsGeneratorTab;
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
        tabs.addTab(pdfDocumentsGeneratorTab,"Wygeneruj pliki pdf");
        tabs.addTab(othersTab,"Reszta");
        root.addComponent(tabs);
    }

}
