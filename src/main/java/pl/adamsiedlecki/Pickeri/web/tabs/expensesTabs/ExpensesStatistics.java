package pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.pojos.MonthExpanses;
import pl.adamsiedlecki.Pickeri.service.ExpenseService;
import pl.adamsiedlecki.Pickeri.tools.MonthExpensesStatisticsGetter;

@SpringComponent
@Scope("prototype")
public class ExpensesStatistics extends VerticalLayout {

    private VerticalLayout root;
    private Button refreshButton;
    private Label totalAmountOfSpentMoney;
    private Label averageAmountOfMoney;
    private ExpenseService expenseService;
    private Grid<MonthExpanses> monthsGrid;

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
        monthsGrid = new Grid<>();
        monthsGrid.addColumn(MonthExpanses::getMonthName).setCaption("Miesiąc");
        monthsGrid.addColumn(MonthExpanses::getExpansesSum).setCaption("Ilość pieniędzy [zł]");
        monthsGrid.setHeight(900, Unit.PIXELS);
        monthsGrid.setWidth(40, Unit.PERCENTAGE);
        root.addComponentsAndExpand(refreshButton, totalAmountOfSpentMoney, averageAmountOfMoney, monthsGrid);
        root.setComponentAlignment(monthsGrid, Alignment.MIDDLE_CENTER);
    }

    private void refreshData() {
        totalAmountOfSpentMoney.setValue("Suma wydanych środków: " + expenseService.getTotalAmountOfSpentMoney().toPlainString() + " zł");
        averageAmountOfMoney.setValue("Średni koszt zakupu: " + expenseService.getAverageAmountOfSpentMoney().toPlainString() + " zł");
        monthsGrid.setItems(MonthExpensesStatisticsGetter.getMontExpansesStatistics(expenseService));
    }

}
