package pl.adamsiedlecki.Pickeri.web.tab.expensesTabs;

import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.Expense;
import pl.adamsiedlecki.Pickeri.service.ExpenseService;

import java.util.Objects;

@SpringComponent
@Scope("prototype")
public class AllExpensesTab extends VerticalLayout {

    private VerticalLayout root;
    private TextField filter;
    private Grid<Expense> expensesGrid;
    private ExpenseService expenseService;
    private Environment env;

    @Autowired
    public AllExpensesTab(ExpenseService expenseService, Environment environment) {
        this.expenseService = expenseService;
        this.env = environment;
        initComponents();
        filter.addValueChangeListener(e ->
                expensesGrid.setItems(expenseService.findAll(filter.getValue()))
        );
        filter.setValueChangeMode(ValueChangeMode.LAZY);
    }

    private void initComponents() {
        root = new VerticalLayout();
        filter = new TextField();
        expensesGrid = new Grid<>();
        Button refreshButton = new Button(env.getProperty("refresh.button"));
        refreshButton.addClickListener(e -> refreshData());
        root.addComponent(refreshButton);
        root.addComponents(filter);
        refreshData();
        this.addComponent(root);
    }

    private void refreshData() {
        expensesGrid.removeAllColumns();
        expensesGrid.addColumn(Expense::getId).setCaption(Objects.requireNonNull(env.getProperty("id.column.caption")));
        expensesGrid.addColumn(Expense::getName).setCaption(Objects.requireNonNull(env.getProperty("name.caption")));
        expensesGrid.addColumn(Expense::getMoneyAmount).setCaption(Objects.requireNonNull(env.getProperty("money.amount")));
        expensesGrid.addColumn(Expense::getTimeFormatted).setCaption(Objects.requireNonNull(env.getProperty("time.column")));
        expensesGrid.setSizeFull();
        expensesGrid.setItems(expenseService.findAll(filter.getValue()));
        expensesGrid.setHeight(700, Unit.PIXELS);
        root.addComponents(expensesGrid);
    }

}
