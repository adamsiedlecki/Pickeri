package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

@Route("usuwaniedanych")
public class RemoveUI extends Div {

    private VerticalLayout root;
    private Label warningLabel;
    private Button deleteFruitPickersButton;
    private FruitPickerService fruitPickerService;
    private FruitDeliveryService fruitDeliveryService;
    private Button deleteFruitDeliveriesButton;

    @Autowired
    public RemoveUI(FruitPickerService fruitPickerService, FruitDeliveryService fruitDeliveryService){
        this.fruitDeliveryService = fruitDeliveryService;
        this.fruitPickerService = fruitPickerService;
        Notification.show("Uwaga! Ta strona przeznaczona jest do usuwania danych!");
        initComponents();
        this.add(new Button("SSS"));
    }

    private void initComponents(){
        root = new VerticalLayout();

        warningLabel = new Label("UWAGA! UWAŻAJ, CO USUWASZ!");
        //warningLabel.setStyleName(ValoTheme.LABEL_COLORED);

        deleteFruitPickersButton = new Button("USUŃ PRACOWNIKÓW");
        //deleteFruitPickersButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteFruitPickersButton.addClickListener(e->fruitPickerService.removeAll());

        deleteFruitDeliveriesButton = new Button("USUŃ DOSTAWY");
        //deleteFruitDeliveriesButton.setStyleName(ValoTheme.BUTTON_DANGER);
        deleteFruitDeliveriesButton.addClickListener(e->fruitDeliveryService.removeAll());

        root.add(warningLabel);
        root.add(deleteFruitPickersButton);
        root.add(deleteFruitDeliveriesButton);

        this.add(root);
    }

}
