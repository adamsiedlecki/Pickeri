package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import pl.adamsiedlecki.Pickeri.interfaces.Removeable;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;

@SpringUI(path = "/usuwaniedanych")
public class RemoveUI extends UI {

    private VerticalLayout root;
    private FruitPickerService fruitPickerService;
    private FruitDeliveryService fruitDeliveryService;
    private FruitVarietyService fruitVarietyService;

    public RemoveUI(FruitPickerService fruitPickerService, FruitDeliveryService fruitDeliveryService,
                    FruitVarietyService fruitVarietyService) {
        this.fruitVarietyService = fruitVarietyService;
        this.fruitDeliveryService = fruitDeliveryService;
        this.fruitPickerService = fruitPickerService;
    }

    @Override
    protected void init(VaadinRequest request) {
        Notification.show("Uwaga! Ta strona przeznaczona jest do usuwania danych!");
        initComponents();
    }

    private void initComponents() {
        root = new VerticalLayout();

        Label warningLabel = new Label("UWAGA! UWAŻAJ, CO USUWASZ!");
        warningLabel.setStyleName(ValoTheme.LABEL_COLORED);

        Button deleteFruitPickersButton = new Button("USUŃ WSZYSTKICH PRACOWNIKÓW");
        deleteFruitPickersButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteFruitPickersButton.addClickListener(e -> fruitPickerService.removeAll());

        Button deleteFruitDeliveriesButton = new Button("USUŃ WSZYSTKIE DOSTAWY");
        deleteFruitDeliveriesButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteFruitDeliveriesButton.addClickListener(e -> fruitDeliveryService.removeAll());

        Button deleteFruitVarietiesButton = new Button("USUŃ WSZYSTKIE ODMIANY");
        deleteFruitVarietiesButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteFruitVarietiesButton.addClickListener(e -> fruitVarietyService.removeAll());

        root.addComponent(warningLabel);

        addIndividualsByIdDeletePanel();

        addSeparator(5);
        root.addComponent(deleteFruitPickersButton);
        root.addComponent(deleteFruitDeliveriesButton);
        root.addComponent(deleteFruitVarietiesButton);
        root.addComponent(new Link("ZMIANA HASŁA", new ExternalResource("/password-change")));

        this.setContent(root);
    }

    private void addIndividualsByIdDeletePanel() {
        addPanel("ID DOSTAWY", "USUŃ DOSTAWĘ O PODANYM ID", fruitDeliveryService);
        addPanel("ID PRACOWNIKA", "USUŃ PRACOWNIKA O PODANYM ID", fruitPickerService);
        addPanel("ID ODMIANY", "USUŃ ODMIANĘ O PODANYM ID", fruitVarietyService);

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
                Notification.show("Wykonano operację...");
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
