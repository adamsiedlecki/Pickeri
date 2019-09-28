package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.OthersTab;
import pl.adamsiedlecki.Pickeri.web.tabs.paymentTabs.AddPaymentTab;
import pl.adamsiedlecki.Pickeri.web.tabs.paymentTabs.PickersPaymentsTableTab;


@SpringUI(path = "/picker-payments")
public class PickerPaymentsUI extends UI {

    private VerticalLayout root;
    private TabSheet tabSheet;
    private AddPaymentTab addPaymentTab;
    private PickersPaymentsTableTab pickersPaymentsTableTab;
    private OthersTab othersTab;

    @Autowired
    public PickerPaymentsUI(OthersTab othersTab, AddPaymentTab addPaymentTab, PickersPaymentsTableTab pickersPaymentsTableTab) {
        this.addPaymentTab = addPaymentTab;
        this.pickersPaymentsTableTab = pickersPaymentsTableTab;
        this.othersTab = othersTab;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        tabSheet = new TabSheet();
        tabSheet.addTab(addPaymentTab, "Dodaj wypłatę");
        tabSheet.addTab(pickersPaymentsTableTab, "Podsumowanie wypłat");
        tabSheet.addTab(othersTab, "Reszta");
        root.addComponent(ResourceGetter.getPickeriLogoAsEmbeddedComponent());
        root.addComponents(tabSheet);
        this.setContent(root);
    }
}
