package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.AllDeliveriesTab;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.OthersTab;

@SpringUI(path = "/all-deliveries")
public class AllDeliveriesUI extends UI {

    private TabSheet tabs;
    private VerticalLayout root;
    private AllDeliveriesTab allDeliveriesTab;
    private OthersTab othersTab;

    @Autowired
    public AllDeliveriesUI(AllDeliveriesTab allDeliveriesTab, OthersTab othersTab) {
        this.allDeliveriesTab = allDeliveriesTab;
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
        tabs.addTab(allDeliveriesTab, "Wszystkie dostawy");
        tabs.addTab(othersTab, "Reszta");
        root.addComponent(tabs);
    }

}
