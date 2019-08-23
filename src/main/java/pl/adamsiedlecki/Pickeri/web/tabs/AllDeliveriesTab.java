package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;

@SpringComponent
public class AllDeliveriesTab extends VerticalLayout {

    private Grid<FruitDelivery> fruitDeliveryGrid;
    private FruitDeliveryService fruitDeliveryService;

    @Autowired
    public AllDeliveriesTab(FruitDeliveryService fruitDeliveryService){
        this.fruitDeliveryService = fruitDeliveryService;
        addComponents();
    }

    private void addComponents(){
        fruitDeliveryGrid = new Grid<>();

        fruitDeliveryGrid.addColumn(FruitDelivery::getPackageAmount).setCaption("Ilość opakowań");
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitPickerId).setCaption("Id pracownika");
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitType).setCaption("Typ");
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitVariety).setCaption("Odmiana");
        fruitDeliveryGrid.addColumn(FruitDelivery::getComment).setCaption("Komentarz");
        fruitDeliveryGrid.addColumn(FruitDelivery::getDeliveryTimeFormatted).setCaption("Czas");

        fruitDeliveryGrid.setSizeFull();

        this.addComponent(fruitDeliveryGrid);
        refreshGrid();
    }

    public void refreshGrid(){
        fruitDeliveryGrid.setItems(fruitDeliveryService.findAll());
    }
}
