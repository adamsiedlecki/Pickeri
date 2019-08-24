package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;

@SpringComponent
@Scope("prototype")
public class AllDeliveriesTab extends VerticalLayout {

    private Grid<FruitDelivery> fruitDeliveryGrid;
    private FruitDeliveryService fruitDeliveryService;
    private Button refreshButton;

    @Autowired
    public AllDeliveriesTab(FruitDeliveryService fruitDeliveryService){
        this.fruitDeliveryService = fruitDeliveryService;
        addComponents();
    }

    private void addComponents(){
        refreshButton = new Button("Odśwież");
        refreshButton.addClickListener(e->refreshGrid());
        fruitDeliveryGrid = new Grid<>();

        fruitDeliveryGrid.addColumn(FruitDelivery::getPackageAmount).setCaption("Ilość opakowań");
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitPickerId).setCaption("Id pracownika");
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitType).setCaption("Typ");
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitVariety).setCaption("Odmiana");
        fruitDeliveryGrid.addColumn(FruitDelivery::getComment).setCaption("Komentarz");
        fruitDeliveryGrid.addColumn(FruitDelivery::getDeliveryTimeFormatted).setCaption("Czas");

        fruitDeliveryGrid.setSizeFull();

        this.addComponent(refreshButton);
        this.addComponent(fruitDeliveryGrid);
        refreshGrid();
    }

    private void refreshGrid(){
        fruitDeliveryGrid.setItems(fruitDeliveryService.findAll());
    }
}
