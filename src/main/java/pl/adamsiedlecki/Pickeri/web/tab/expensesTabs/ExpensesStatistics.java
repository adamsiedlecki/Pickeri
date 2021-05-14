package pl.adamsiedlecki.Pickeri.web.tab.expensesTabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.pojos.MonthExpanses;
import pl.adamsiedlecki.Pickeri.service.ExpenseService;
import pl.adamsiedlecki.Pickeri.tools.MonthExpensesStatisticsGetter;

import java.util.Objects;

@SpringComponent
@Scope("prototype")
public class ExpensesStatistics extends VerticalLayout {

    private VerticalLayout root;
    private Button refreshButton;
    private Label totalAmountOfSpentMoney;
    private Label averageAmountOfMoney;
    private final ExpenseService expenseService;
    private Grid<MonthExpanses> monthsGrid;
    private final Environment env;

    @Autowired
    public ExpensesStatistics(ExpenseService expenseService, Environment environment) {
        this.expenseService = expenseService;
        this.env = environment;
        initComponents();
        this.addComponent(root);
        refreshData();
    }

    private void initComponents() {
        root = new VerticalLayout();
        totalAmountOfSpentMoney = new Label();
        averageAmountOfMoney = new Label();
        refreshButton = new Button(env.getProperty("refresh.button"));
        refreshButton.addClickListener(e -> refreshData());
        monthsGrid = new Grid<>();
        monthsGrid.addColumn(MonthExpanses::getMonthName).setCaption(Objects.requireNonNull(env.getProperty("month.name.column")));
        monthsGrid.addColumn(MonthExpanses::getExpansesSum).setCaption(Objects.requireNonNull(env.getProperty("money.amount.column")));
        monthsGrid.addColumn(MonthExpanses::getPercentageInAll).setCaption(Objects.requireNonNull(env.getProperty("percentage.participation")));
        monthsGrid.setHeight(900, Unit.PIXELS);
        monthsGrid.setWidth(40, Unit.PERCENTAGE);
        root.addComponentsAndExpand(refreshButton, totalAmountOfSpentMoney, averageAmountOfMoney, monthsGrid);
        root.setComponentAlignment(monthsGrid, Alignment.MIDDLE_CENTER);
    }

    private void refreshData() {
        totalAmountOfSpentMoney.setValue(env.getProperty("expenses.sum") + expenseService.getTotalAmountOfSpentMoney().toPlainString() + env.getProperty("currency"));
        averageAmountOfMoney.setValue(env.getProperty("average.expense") + expenseService.getAverageAmountOfSpentMoney().toPlainString() + env.getProperty("currency"));
        monthsGrid.setItems(MonthExpensesStatisticsGetter.getMontExpansesStatistics(expenseService));
    }

}
