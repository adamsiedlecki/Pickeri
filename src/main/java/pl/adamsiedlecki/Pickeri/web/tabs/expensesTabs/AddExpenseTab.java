package pl.adamsiedlecki.Pickeri.web.tabs.expensesTabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.Expense;
import pl.adamsiedlecki.Pickeri.service.ExpenseService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringComponent
@Scope("prototype")
public class AddExpenseTab extends VerticalLayout {

    private VerticalLayout root;
    private ExpenseService expenseService;
    private Environment env;

    @Autowired
    public AddExpenseTab(ExpenseService expenseService, Environment environment) {
        this.expenseService = expenseService;
        initComponents();
        this.addComponentsAndExpand(root);
        this.env = environment;
    }

    private void initComponents() {
        root = new VerticalLayout();
        FormLayout formLayout = new FormLayout();
        TextField nameField = new TextField(env.getProperty("expense.name"));
        TextField amountOfMoneyField = new TextField("Kwota [zł] (grosze oddzielamy kropką)");
        Button saveButton = new Button("ZAPISZ");
        saveButton.addClickListener(x -> {
            if (!"".equals(nameField.getValue()) && !"".equals(amountOfMoneyField.getValue()) && NumberUtils.isCreatable(amountOfMoneyField.getValue())) {
                Expense expense = new Expense(nameField.getValue(), new BigDecimal(amountOfMoneyField.getValue()), LocalDateTime.now());
                expenseService.save(expense);
                nameField.clear();
                amountOfMoneyField.clear();
            } else {
                Notification.show("Wprowadzone dane są niepoprawne.");
            }
        });
        formLayout.addComponents(nameField, amountOfMoneyField, saveButton);
        root.addComponent(formLayout);
    }

}
