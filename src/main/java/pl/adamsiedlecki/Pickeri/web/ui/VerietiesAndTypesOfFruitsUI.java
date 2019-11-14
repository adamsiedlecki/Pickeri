package pl.adamsiedlecki.Pickeri.web.ui;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.service.SettingsService;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.AlignmentSetter;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.HeaderAdder;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.ThemeSetter;
import pl.adamsiedlecki.Pickeri.web.tab.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tab.othersUITabs.AddDeleteTypeTab;
import pl.adamsiedlecki.Pickeri.web.tab.othersUITabs.AddFruitVarietyTab;
import pl.adamsiedlecki.Pickeri.web.tab.othersUITabs.AllVarietiesTab;

@SpringUI(path = "/varieties-and-types")
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
    private SettingsService settingsService;

    @Autowired
    public VerietiesAndTypesOfFruitsUI(AddFruitVarietyTab addFruitVarietyTab, MenuTab othersTab,
                                       AddDeleteTypeTab addDeleteTypeTab, AllVarietiesTab allVarietiesTab,
                                       Environment environment, SettingsService settingsService) {
        this.addDeleteTypeTab = addDeleteTypeTab;
        this.fruitVarietyTab = addFruitVarietyTab;
        this.othersTab = othersTab;
        this.allVarietiesTab = allVarietiesTab;
        this.env = environment;
        this.settingsService = settingsService;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        HeaderAdder.add(root, settingsService);
        addTabs();
        AlignmentSetter.apply(root, tabs);
        this.setContent(root);
        ThemeSetter.set(this);
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
