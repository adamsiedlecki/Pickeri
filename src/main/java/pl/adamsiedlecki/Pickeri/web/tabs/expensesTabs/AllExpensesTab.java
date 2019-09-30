package pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs;

import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.entity.Expense;
import pl.adamsiedlecki.Pickeri.service.ExpenseService;

@SpringComponent
@Scope("prototype")
public class AllExpensesTab extends VerticalLayout {

    private VerticalLayout root;
    private TextField filter;
    private Grid<Expense> expensesGrid;
    private ExpenseService expenseService;

    @Autowired
    public AllExpensesTab(ExpenseService expenseService) {
        this.expenseService = expenseService;
        initComponents();
        filter.addValueChangeListener(e ->
                expensesGrid.setItems(expenseService.findAllWithFilter(filter.getValue()))
        );
        filter.setValueChangeMode(ValueChangeMode.LAZY);
    }

    private void initComponents() {
        root = new VerticalLayout();
        filter = new TextField();
        expensesGrid = new Grid<>();
        Button refreshButton = new Button("ODŚWIEŻ");
        refreshButton.addClickListener(e -> refreshData());
        root.addComponent(refreshButton);
        root.addComponents(filter);
        refreshData();
        this.addComponent(root);
    }

    private void refreshData() {
        expensesGrid.removeAllColumns();
        expensesGrid.addColumn(Expense::getId).setCaption("ID");
        expensesGrid.addColumn(Expense::getName).setCaption("Nazwa");
        expensesGrid.addColumn(Expense::getMoneyAmount).setCaption("Kwota");
        expensesGrid.addColumn(Expense::getTimeFormatted).setCaption("Data");
        expensesGrid.setSizeFull();
        expensesGrid.setItems(expenseService.findAllWithFilter(filter.getValue()));
        expensesGrid.setHeight(700, Unit.PIXELS);
        root.addComponents(expensesGrid);
    }

}
