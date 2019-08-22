package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;

@Component
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

        fruitDeliveryGrid.addColumn(FruitDelivery::getPackageAmount);
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitPickerId);
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitType);
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitVariety);
        fruitDeliveryGrid.addColumn(FruitDelivery::getComment);

        this.addComponent(fruitDeliveryGrid);
        refreshGrid();
    }

    public void refreshGrid(){
        fruitDeliveryGrid.setItems(fruitDeliveryService.findAll());
    }
}
