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
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.MenuTab;
import pl.adamsiedlecki.Pickeri.web.tabs.paymentTabs.AddPaymentTab;
import pl.adamsiedlecki.Pickeri.web.tabs.paymentTabs.PdfGenerationTab;
import pl.adamsiedlecki.Pickeri.web.tabs.paymentTabs.PickersPaymentsTableTab;


@SpringUI(path = "/picker-payments")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Squada+One|Ubuntu&display=swap"})
@Title("Wypłaty")
public class PickerPaymentsUI extends UI {

    private VerticalLayout root;
    private TabSheet tabSheet;
    private AddPaymentTab addPaymentTab;
    private PickersPaymentsTableTab pickersPaymentsTableTab;
    private PdfGenerationTab pdfGenerationTab;
    private MenuTab othersTab;

    @Autowired
    public PickerPaymentsUI(MenuTab othersTab, AddPaymentTab addPaymentTab, PickersPaymentsTableTab pickersPaymentsTableTab,
                            PdfGenerationTab pdfGenerationTab) {
        this.addPaymentTab = addPaymentTab;
        this.pickersPaymentsTableTab = pickersPaymentsTableTab;
        this.pdfGenerationTab = pdfGenerationTab;
        this.othersTab = othersTab;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        tabSheet = new TabSheet();
        tabSheet.addTab(addPaymentTab, "Dodaj wypłatę");
        tabSheet.addTab(pickersPaymentsTableTab, "Podsumowanie wypłat");
        tabSheet.addTab(pdfGenerationTab, "Generowanie PDF");
        tabSheet.addTab(othersTab, "Menu");
        root.addComponent(ResourceGetter.getPickeriLogoAsEmbeddedComponent());
        root.addComponents(tabSheet);
        this.setContent(root);
    }
}
