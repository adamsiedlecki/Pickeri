package pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs;

import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.entity.Expense;
import pl.adamsiedlecki.Pickeri.service.ExpenseService;

import java.util.List;

@SpringComponent
@Scope("prototype")
public class AllExpensesTab extends VerticalLayout {

    private ExpenseService expenseService;
    private Grid<Expense> grid;
    private TextField filterField;

    @Autowired
    public AllExpensesTab(ExpenseService expenseService){
        this.expenseService = expenseService;
        filterField = new TextField("Filtr");

        grid = new Grid<>();
        grid.addColumn(Expense::getId).setCaption("ID");
        grid.addColumn(Expense::getName).setCaption("Nazwa wydatku");
        grid.addColumn(Expense::getMoneyAmount).setCaption("Kwota wydatku");
//        grid.setHeight(500, Unit.PIXELS);
//        filterField.setValueChangeMode(ValueChangeMode.LAZY);
//        filterField.addValueChangeListener(e->{
//                    if(filterField.getValue().equals("")){
//                        refreshData(expenseService.findAll());
//                    }else{
//                        refreshData(expenseService.findAll(filterField.getValue()));
//                    }
//                }
//        );
//        grid.setWidth(1300, Unit.PIXELS);
        grid.setData(expenseService.findAll());
        this.addComponents(filterField, grid);
//        this.setComponentAlignment(filterField, Alignment.MIDDLE_CENTER);
//        this.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
//        refreshData(expenseService.findAll());
    }

    private void refreshData(List<Expense> expenses){
        grid.setData(expenses);
    }



}
