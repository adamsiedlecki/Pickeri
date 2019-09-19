package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.service.ExpenseService;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.tools.QRCodeReader;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs.AddExpenseTab;
import pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs.AllExpensesTab;
import pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs.ExpensesStatistics;
import pl.adamsiedlecki.Pickeri.web.tabs.independentTabs.OthersTab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@SpringUI(path="/expenses")
public class ExpensesUI extends UI {

    private VerticalLayout root;
    private TabSheet tabSheet;
    private AddExpenseTab addExpenseTab;
    private AllExpensesTab allExpensesTab;
    private ExpensesStatistics expensesStatistics;
    private OthersTab othersTab;

    @Autowired
    public ExpensesUI(AddExpenseTab addExpenseTab, AllExpensesTab allExpensesTab, ExpensesStatistics expensesStatistics,
                      OthersTab othersTab){
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
        tabSheet.addTab(othersTab, "Reszta");
        root.addComponent(new Embedded("", new FileResource(ResourceGetter.getPickeriLogo())));
        root.addComponentsAndExpand(tabSheet);
        this.setContent(root);
    }
}
