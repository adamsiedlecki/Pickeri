package pl.adamsiedlecki.Pickeri.web.ui;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.interfaces.Removeable;
import pl.adamsiedlecki.Pickeri.service.ExpenseService;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;
import pl.adamsiedlecki.Pickeri.tools.UserInterfaceTools.ThemeSetter;

@SpringUI(path = "/usuwaniedanych")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${delete.title}")
public class RemoveUI extends UI {

    private VerticalLayout root;
    private FruitPickerService fruitPickerService;
    private FruitDeliveryService fruitDeliveryService;
    private FruitVarietyService fruitVarietyService;
    private Environment env;
    private ExpenseService expenseService;

    public RemoveUI(FruitPickerService fruitPickerService, FruitDeliveryService fruitDeliveryService,
                    FruitVarietyService fruitVarietyService, Environment environment, ExpenseService expenseService) {
        this.expenseService = expenseService;
        this.fruitVarietyService = fruitVarietyService;
        this.fruitDeliveryService = fruitDeliveryService;
        this.fruitPickerService = fruitPickerService;
        this.env = environment;
    }

    @Override
    protected void init(VaadinRequest request) {
        Notification.show(env.getProperty("remove.data.notification"));
        initComponents();
        ThemeSetter.set(this);
    }

    private void initComponents() {
        root = new VerticalLayout();

        Label warningLabel = new Label(env.getProperty("remove.data.warning"));
        warningLabel.setStyleName(ValoTheme.LABEL_COLORED);

        Button deleteFruitPickersButton = new Button(env.getProperty("delete.all.employees.button"));
        deleteFruitPickersButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteFruitPickersButton.addClickListener(e -> fruitPickerService.removeAll());

        Button deleteFruitDeliveriesButton = new Button(env.getProperty("delete.all.deliveries.button"));
        deleteFruitDeliveriesButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteFruitDeliveriesButton.addClickListener(e -> fruitDeliveryService.removeAll());

        Button deleteFruitVarietiesButton = new Button(env.getProperty("delete.all.varieties.button"));
        deleteFruitVarietiesButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteFruitVarietiesButton.addClickListener(e -> fruitVarietyService.removeAll());

        Button deleteExpensesButton = new Button(env.getProperty("delete.all.expenses.button"));
        deleteExpensesButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteExpensesButton.addClickListener(e -> expenseService.removeAll());

        root.addComponent(warningLabel);

        addIndividualsByIdDeletePanel();

        addSeparator(5);
        root.addComponent(deleteFruitPickersButton);
        root.addComponent(deleteFruitDeliveriesButton);
        root.addComponent(deleteFruitVarietiesButton);
        root.addComponent(deleteExpensesButton);
        root.addComponent(new Link(env.getProperty("password.change.link"), new ExternalResource("/password-change")));

        this.setContent(root);
    }

    private void addIndividualsByIdDeletePanel() {
        addPanel(env.getProperty("delivery.id.field"), env.getProperty("delete.delivery.button"), fruitDeliveryService);
        addPanel(env.getProperty("employee.id.field"), env.getProperty("delete.employee.button"), fruitPickerService);
        addPanel(env.getProperty("variety.id.field"), env.getProperty("delete.variety.button"), fruitVarietyService);
        addPanel(env.getProperty("expense.id.field"), env.getProperty("delete.expense.button"), expenseService);

    }

    private void addPanel(String idFieldName, String buttonName, Removeable service) {
        TextField idField = new TextField();
        idField.setDescription(idFieldName);
        Button button = new Button();
        button.setStyleName(ValoTheme.BUTTON_DANGER);
        button.setCaption(buttonName);
        button.addClickListener(x -> {
            String value = idField.getValue();
            if (value != null && !value.equals("")) {
                Long id = Long.parseLong(value);
                service.removeById(id);
                idField.setValue("");
                Notification.show(env.getProperty("operation.is.done.notification"));
            }
        });
        HorizontalLayout panel = new HorizontalLayout(idField, button);
        root.addComponent(panel);
    }

    private void addSeparator(int amount) {
        VerticalLayout separator = new VerticalLayout();
        for (int i = 0; i < amount; i++) {
            separator.addComponent(new Label("ฅ^•ﻌ•^ฅ"));
        }
        root.addComponent(separator);
    }

}
