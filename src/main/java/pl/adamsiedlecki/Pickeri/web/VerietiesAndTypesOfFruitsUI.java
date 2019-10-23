package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.tools.AlignmentSetter;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tabs.othersUITabs.AddDeleteTypeTab;
import pl.adamsiedlecki.Pickeri.web.tabs.othersUITabs.AddFruitVarietyTab;
import pl.adamsiedlecki.Pickeri.web.tabs.othersUITabs.AddPickerTab;
import pl.adamsiedlecki.Pickeri.web.tabs.othersUITabs.AllVarietiesTab;

@SpringUI(path = "/varieties-and-types")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${other.title}")
public class VerietiesAndTypesOfFruitsUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AddFruitVarietyTab fruitVarietyTab;
    private MenuTab othersTab;
    private AddDeleteTypeTab addDeleteTypeTab;
    private AllVarietiesTab allVarietiesTab;
    private Environment env;

    @Autowired
    public VerietiesAndTypesOfFruitsUI(AddFruitVarietyTab addFruitVarietyTab, MenuTab othersTab,
                                       AddDeleteTypeTab addDeleteTypeTab, AllVarietiesTab allVarietiesTab,
                                       Environment environment) {
        this.addDeleteTypeTab = addDeleteTypeTab;
        this.fruitVarietyTab = addFruitVarietyTab;
        this.othersTab = othersTab;
        this.allVarietiesTab = allVarietiesTab;
        this.env = environment;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        Embedded pickeriLogo = ResourceGetter.getPickeriLogoAsEmbeddedComponent();
        root.addComponent(pickeriLogo);
        addTabs();
        AlignmentSetter.apply(root, pickeriLogo, tabs);
        this.setContent(root);
    }

    private void addTabs() {
        tabs = new TabSheet();
        tabs.addTab(fruitVarietyTab, env.getProperty("add.fruit.type.tab"));
        tabs.addTab(allVarietiesTab, env.getProperty("fruit.varieties.list.tab"));
        tabs.addTab(addDeleteTypeTab, env.getProperty("add.or.delete.fruit.type.tab"));
        tabs.addTab(othersTab, env.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }

}
