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
import pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs.AddExpenseTab;
import pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs.AllExpensesTab;
import pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs.ExpensesStatistics;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.MenuTab;


@SpringUI(path = "/expenses")
@Theme("mytheme")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${expenses.title}")
public class ExpensesUI extends UI {

    private VerticalLayout root;
    private TabSheet tabSheet;
    private AddExpenseTab addExpenseTab;
    private AllExpensesTab allExpensesTab;
    private ExpensesStatistics expensesStatistics;
    private MenuTab othersTab;
    private Environment environment;

    @Autowired
    public ExpensesUI(AddExpenseTab addExpenseTab, AllExpensesTab allExpensesTab, ExpensesStatistics expensesStatistics,
                      MenuTab othersTab, Environment environment) {
        this.addExpenseTab = addExpenseTab;
        this.allExpensesTab = allExpensesTab;
        this.expensesStatistics = expensesStatistics;
        this.othersTab = othersTab;
        this.environment = environment;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        tabSheet = new TabSheet();
        tabSheet.addTab(addExpenseTab, environment.getProperty("add.expense.tab.caption"));
        tabSheet.addTab(allExpensesTab, environment.getProperty("all.expenses.tab.caption"));
        tabSheet.addTab(expensesStatistics, environment.getProperty("statistics.expenses.tab"));
        tabSheet.addTab(othersTab, environment.getProperty("menu.tab.caption"));
        Embedded pickeriLogo = ResourceGetter.getPickeriLogoAsEmbeddedComponent();
        root.addComponent(pickeriLogo);
        root.addComponents(tabSheet);
        AlignmentSetter.apply(root, pickeriLogo, tabSheet);
        this.setContent(root);
    }
}
