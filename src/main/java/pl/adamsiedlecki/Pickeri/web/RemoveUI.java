package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

@SpringUI(path="/usuwaniedanych")
public class RemoveUI extends UI {

    private VerticalLayout root;
    private Label warningLabel;
    private Button deleteFruitPickersButton;
    private FruitPickerService fruitPickerService;
    private FruitDeliveryService fruitDeliveryService;
    private Button deleteFruitDeliveriesButton;

    public RemoveUI(FruitPickerService fruitPickerService, FruitDeliveryService fruitDeliveryService){
        this.fruitDeliveryService = fruitDeliveryService;
        this.fruitPickerService = fruitPickerService;
    }

    @Override
    protected void init(VaadinRequest request) {
        Notification.show("Uwaga! Ta strona przeznaczona jest do usuwania danych!");
        initComponents();
    }

    private void initComponents(){
        root = new VerticalLayout();

        warningLabel = new Label("UWAGA! UWAŻAJ, CO USUWASZ!");
        warningLabel.setStyleName(ValoTheme.LABEL_COLORED);

        deleteFruitPickersButton = new Button("USUŃ PRACOWNIKÓW");
        deleteFruitPickersButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteFruitPickersButton.addClickListener(e->fruitPickerService.removeAll());

        deleteFruitDeliveriesButton = new Button("USUŃ DOSTAWY");
        deleteFruitDeliveriesButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteFruitDeliveriesButton.addClickListener(e->fruitDeliveryService.removeAll());

        root.addComponent(warningLabel);
        root.addComponent(deleteFruitPickersButton);
        root.addComponent(deleteFruitDeliveriesButton);

        this.setContent(root);
    }

}
