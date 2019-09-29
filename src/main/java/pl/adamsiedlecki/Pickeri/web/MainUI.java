package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.AddDeliveryTab;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.MenuTab;

@SpringUI
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Squada+One|Ubuntu&display=swap"})
public class MainUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AddDeliveryTab addDeliveryTab;
    private MenuTab othersTab;

    @Autowired
    public MainUI(AddDeliveryTab addDeliveryTab, MenuTab othersTab) {
        this.addDeliveryTab = addDeliveryTab;
        this.othersTab = othersTab;
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
        tabs.addTab(addDeliveryTab, "Dodaj owoce");
        tabs.addTab(othersTab, "Menu");
        root.addComponent(tabs);
    }

}
