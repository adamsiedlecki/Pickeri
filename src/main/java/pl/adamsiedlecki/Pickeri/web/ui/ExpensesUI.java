package pl.adamsiedlecki.Pickeri.web.ui;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.service.SettingsService;
import pl.adamsiedlecki.Pickeri.tools.UserInterfaceTools.AlignmentSetter;
import pl.adamsiedlecki.Pickeri.tools.UserInterfaceTools.HeaderAdder;
import pl.adamsiedlecki.Pickeri.tools.UserInterfaceTools.ThemeSetter;
import pl.adamsiedlecki.Pickeri.web.tab.expensesTabs.AddExpenseTab;
import pl.adamsiedlecki.Pickeri.web.tab.expensesTabs.AllExpensesTab;
import pl.adamsiedlecki.Pickeri.web.tab.expensesTabs.ExpensesStatistics;
import pl.adamsiedlecki.Pickeri.web.tab.independentTabs.MenuTab;


@SpringUI(path = "/expenses")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${expenses.title}")
public class ExpensesUI extends UI {

    private VerticalLayout root;
    private TabSheet tabs;
    private AddExpenseTab addExpenseTab;
    private AllExpensesTab allExpensesTab;
    private ExpensesStatistics expensesStatistics;
    private MenuTab othersTab;
    private Environment environment;
    private SettingsService settingsService;

    @Autowired
    public ExpensesUI(AddExpenseTab addExpenseTab, AllExpensesTab allExpensesTab, ExpensesStatistics expensesStatistics,
                      MenuTab othersTab, Environment environment, SettingsService settingsService) {
        this.addExpenseTab = addExpenseTab;
        this.allExpensesTab = allExpensesTab;
        this.expensesStatistics = expensesStatistics;
        this.othersTab = othersTab;
        this.environment = environment;
        this.settingsService = settingsService;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        tabs = new TabSheet();
        HeaderAdder.add(root, settingsService);
        addTabs();
        AlignmentSetter.apply(root, tabs);
        this.setContent(root);
        ThemeSetter.set(this);
    }

    private void addTabs(){
        tabs.addTab(addExpenseTab, environment.getProperty("add.expense.tab.caption"));
        tabs.addTab(allExpensesTab, environment.getProperty("all.expenses.tab.caption"));
        tabs.addTab(expensesStatistics, environment.getProperty("statistics.expenses.tab"));
        tabs.addTab(othersTab, environment.getProperty("menu.tab.caption"));
        root.addComponent(tabs);
    }

}
