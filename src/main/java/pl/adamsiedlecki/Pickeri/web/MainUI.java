package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.AddDeliveryTab;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.OthersTab;

@SpringUI
public class MainUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AddDeliveryTab addDeliveryTab;
    private OthersTab othersTab;

    @Autowired
    public MainUI(AddDeliveryTab addDeliveryTab, OthersTab othersTab) {
        this.addDeliveryTab = addDeliveryTab;
        this.othersTab = othersTab;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        Embedded logo = new Embedded("", new FileResource(ResourceGetter.getPickeriLogo()));
        logo.setWidth(30, Unit.PERCENTAGE);
        logo.setHeight(30,Unit.PERCENTAGE);
        root.addComponent(logo);
        root.setMargin(new MarginInfo(false,true,true,true));
        addTabs();
        this.setContent(root);
    }

    private void addTabs() {
        tabs = new TabSheet();
        tabs.addTab(addDeliveryTab, "Dodaj owoce");
        tabs.addTab(othersTab, "Reszta");
        root.addComponent(tabs);
    }

}
