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
import pl.adamsiedlecki.Pickeri.web.tab.paymentTabs.AddPaymentTab;
import pl.adamsiedlecki.Pickeri.web.tab.paymentTabs.PickersPaymentsTableTab;
import pl.adamsiedlecki.Pickeri.web.tab.paymentTabs.SubtractPaymentTab;


@SpringUI(path = "/picker-payments")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${payments.title}")
public class PickerPaymentsUI extends UI {

    private VerticalLayout root;
    private TabSheet tabs;
    private AddPaymentTab addPaymentTab;
    private PickersPaymentsTableTab pickersPaymentsTableTab;
    private MenuTab othersTab;
    private Environment env;
    private SubtractPaymentTab subtractPaymentTab;
    private SettingsService settingsService;

    @Autowired
    public PickerPaymentsUI(MenuTab othersTab, AddPaymentTab addPaymentTab, PickersPaymentsTableTab pickersPaymentsTableTab,
                            Environment environment, SubtractPaymentTab subtractPaymentTab, SettingsService settingsService) {
        this.env = environment;
        this.addPaymentTab = addPaymentTab;
        this.pickersPaymentsTableTab = pickersPaymentsTableTab;
        this.subtractPaymentTab = subtractPaymentTab;
        this.othersTab = othersTab;
        this.settingsService = settingsService;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        tabs = new TabSheet();
        tabs.addTab(addPaymentTab, env.getProperty("add.payment.tab"));
        tabs.addTab(pickersPaymentsTableTab, env.getProperty("payment.summary.tab"));
        tabs.addTab(subtractPaymentTab, env.getProperty("subtract.payment.tab.caption"));
        tabs.addTab(othersTab, env.getProperty("menu.tab.caption"));
        HeaderAdder.add(root, settingsService);
        root.addComponent(tabs);
        AlignmentSetter.apply(root, tabs);
        this.setContent(root);
        ThemeSetter.set(this);
    }
}
