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
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.OthersUITabs.*;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.MenuTab;

@SpringUI(path = "/other")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${other.title}")
public class OthersUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AddFruitVarietyTab fruitVarietyTab;
    private PdfDocumentsGeneratorTab pdfDocumentsGeneratorTab;
    private MenuTab othersTab;
    private AddPickerTab addPickerTab;
    private AddDeleteTypeTab addDeleteTypeTab;
    private AllVarietiesTab allVarietiesTab;
    private Environment env;

    @Autowired
    public OthersUI(AddFruitVarietyTab addFruitVarietyTab, PdfDocumentsGeneratorTab pdfDocumentsGeneratorTab, MenuTab othersTab,
                    AddPickerTab addPickerTab, AddDeleteTypeTab addDeleteTypeTab, AllVarietiesTab allVarietiesTab,
                    Environment environment) {
        this.addDeleteTypeTab = addDeleteTypeTab;
        this.addPickerTab = addPickerTab;
        this.pdfDocumentsGeneratorTab = pdfDocumentsGeneratorTab;
        this.fruitVarietyTab = addFruitVarietyTab;
        this.othersTab = othersTab;
        this.allVarietiesTab = allVarietiesTab;
        this.env = environment;
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
        tabs.addTab(fruitVarietyTab, env.getProperty("add.fruit.type.tab"));
        tabs.addTab(allVarietiesTab, env.getProperty("fruit.varieties.list.tab"));
        tabs.addTab(addPickerTab, env.getProperty("add.picker.tab"));
        tabs.addTab(addDeleteTypeTab, env.getProperty("add.or.delete.fruit.tab"));
        tabs.addTab(pdfDocumentsGeneratorTab, env.getProperty("generate.pdf.tab"));
        tabs.addTab(othersTab, env.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }

}
