package pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.service.ExpenseService;

@SpringComponent
@Scope("prototype")
public class ExpensesStatistics extends VerticalLayout {

    private VerticalLayout root;
    private Button refreshButton;
    private Label totalAmountOfSpentMoney;
    private Label averageAmountOfMoney;
    private ExpenseService expenseService;

    @Autowired
    public ExpensesStatistics(ExpenseService expenseService) {
        this.expenseService = expenseService;
        initComponents();
        this.addComponent(root);
        refreshData();
    }

    private void initComponents() {
        root = new VerticalLayout();
        totalAmountOfSpentMoney = new Label();
        averageAmountOfMoney = new Label();
        refreshButton = new Button("ODŚWIEŻ");
        refreshButton.addClickListener(e -> refreshData());
        root.addComponentsAndExpand(refreshButton, totalAmountOfSpentMoney, averageAmountOfMoney);
    }

    private void refreshData() {
        totalAmountOfSpentMoney.setValue("Suma wydanych środków: " + expenseService.getTotalAmountOfSpentMoney().toPlainString() + " zł");
        averageAmountOfMoney.setValue("Średni koszt zakupu: " + expenseService.getAverageAmountOfSpentMoney().toPlainString() + " zł");
    }

}
