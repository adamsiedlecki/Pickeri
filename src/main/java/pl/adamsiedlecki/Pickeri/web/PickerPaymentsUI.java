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
import pl.adamsiedlecki.Pickeri.web.tabs.paymentTabs.AddPaymentTab;
import pl.adamsiedlecki.Pickeri.web.tabs.paymentTabs.PickersPaymentsTableTab;
import pl.adamsiedlecki.Pickeri.web.tabs.paymentTabs.SubtractPaymentTab;


@SpringUI(path = "/picker-payments")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${payments.title}")
public class PickerPaymentsUI extends UI {

    private VerticalLayout root;
    private TabSheet tabSheet;
    private AddPaymentTab addPaymentTab;
    private PickersPaymentsTableTab pickersPaymentsTableTab;
    private MenuTab othersTab;
    private Environment env;
    private SubtractPaymentTab subtractPaymentTab;

    @Autowired
    public PickerPaymentsUI(MenuTab othersTab, AddPaymentTab addPaymentTab, PickersPaymentsTableTab pickersPaymentsTableTab,
                             Environment environment, SubtractPaymentTab subtractPaymentTab) {
        this.env = environment;
        this.addPaymentTab = addPaymentTab;
        this.pickersPaymentsTableTab = pickersPaymentsTableTab;
        this.subtractPaymentTab = subtractPaymentTab;
        this.othersTab = othersTab;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        tabSheet = new TabSheet();
        tabSheet.addTab(addPaymentTab, env.getProperty("add.payment.tab"));
        tabSheet.addTab(pickersPaymentsTableTab, env.getProperty("payment.summary.tab"));
        tabSheet.addTab(subtractPaymentTab, env.getProperty("subtract.payment.tab.caption"));
        tabSheet.addTab(othersTab, env.getProperty("menu.tab.caption"));
        Embedded pickeriLogo = ResourceGetter.getPickeriLogoAsEmbeddedComponent();
        root.addComponent(pickeriLogo);
        root.addComponents(tabSheet);
        AlignmentSetter.apply(root, pickeriLogo, tabSheet);
        this.setContent(root);
    }
}
