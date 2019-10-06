package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.AllDeliveriesTab;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.MenuTab;

@SpringUI(path = "/all-deliveries")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${deliveries.title}")
public class AllDeliveriesUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AllDeliveriesTab allDeliveriesTab;
    private MenuTab othersTab;
    private Environment environment;

    @Autowired
    public AllDeliveriesUI(AllDeliveriesTab allDeliveriesTab, MenuTab othersTab, Environment environment) {
        this.allDeliveriesTab = allDeliveriesTab;
        this.othersTab = othersTab;
        this.environment = environment;

    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.addComponent(ResourceGetter.getPickeriLogoAsEmbeddedComponent());
        root.setMargin(new MarginInfo(false, true, true, true));
        addTabs();
        this.setContent(root);
    }

    private void addTabs() {
        tabs = new TabSheet();
        tabs.addTab(allDeliveriesTab, environment.getProperty("all.deliveries.tab.caption"));
        tabs.addTab(othersTab, environment.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }

}
