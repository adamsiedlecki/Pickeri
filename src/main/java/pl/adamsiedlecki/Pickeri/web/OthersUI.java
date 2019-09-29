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
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.OthersUITabs.*;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.MenuTab;

@SpringUI(path = "/other")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("Inne")
public class OthersUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AddFruitVarietyTab fruitVarietyTab;
    private PdfDocumentsGeneratorTab pdfDocumentsGeneratorTab;
    private MenuTab othersTab;
    private AddPickerTab addPickerTab;
    private AddDeleteTypeTab addDeleteTypeTab;
    private AllVarietiesTab allVarietiesTab;

    @Autowired
    public OthersUI(AddFruitVarietyTab addFruitVarietyTab, PdfDocumentsGeneratorTab pdfDocumentsGeneratorTab, MenuTab othersTab,
                    AddPickerTab addPickerTab, AddDeleteTypeTab addDeleteTypeTab, AllVarietiesTab allVarietiesTab) {
        this.addDeleteTypeTab = addDeleteTypeTab;
        this.addPickerTab = addPickerTab;
        this.pdfDocumentsGeneratorTab = pdfDocumentsGeneratorTab;
        this.fruitVarietyTab = addFruitVarietyTab;
        this.othersTab = othersTab;
        this.allVarietiesTab = allVarietiesTab;
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
        tabs.addTab(fruitVarietyTab, "Dodaj odmianę owocu");
        tabs.addTab(allVarietiesTab, "Lista odmian");
        tabs.addTab(addPickerTab, "Dodaj pracownika");
        tabs.addTab(addDeleteTypeTab, "Dodaj / usuń typ owocu");
        tabs.addTab(pdfDocumentsGeneratorTab, "Wygeneruj pliki pdf");
        tabs.addTab(othersTab, "Menu");
        root.addComponent(tabs);
    }

}
