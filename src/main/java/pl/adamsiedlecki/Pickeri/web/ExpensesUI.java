package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs.AddExpenseTab;
import pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs.AllExpensesTab;
import pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs.ExpensesStatistics;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.MenuTab;


@SpringUI(path = "/expenses")
public class ExpensesUI extends UI {

    private VerticalLayout root;
    private TabSheet tabSheet;
    private AddExpenseTab addExpenseTab;
    private AllExpensesTab allExpensesTab;
    private ExpensesStatistics expensesStatistics;
    private MenuTab othersTab;

    @Autowired
    public ExpensesUI(AddExpenseTab addExpenseTab, AllExpensesTab allExpensesTab, ExpensesStatistics expensesStatistics,
                      MenuTab othersTab) {
        this.addExpenseTab = addExpenseTab;
        this.allExpensesTab = allExpensesTab;
        this.expensesStatistics = expensesStatistics;
        this.othersTab = othersTab;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        tabSheet = new TabSheet();
        tabSheet.addTab(addExpenseTab, "Dodaj wydatek");
        tabSheet.addTab(allExpensesTab, "Wszystkie wydatki");
        tabSheet.addTab(expensesStatistics, "Statystyki wydatków");
        tabSheet.addTab(othersTab, "Menu");
        root.addComponent(ResourceGetter.getPickeriLogoAsEmbeddedComponent());
        root.addComponents(tabSheet);
        this.setContent(root);
    }
}
