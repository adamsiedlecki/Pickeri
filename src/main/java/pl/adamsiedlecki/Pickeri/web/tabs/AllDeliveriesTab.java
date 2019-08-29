package pl.adamsiedlecki.Pickeri.web.tabs;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;

@Component
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

        fruitDeliveryGrid.addColumn(FruitDelivery::getPackageAmount).setHeader("Ilość opakowań");
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitPickerId).setHeader("Id pracownika");
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitType).setHeader("Typ");
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitVariety).setHeader("Odmiana");
        fruitDeliveryGrid.addColumn(FruitDelivery::getComment).setHeader("Komentarz");
        fruitDeliveryGrid.addColumn(FruitDelivery::getDeliveryTimeFormatted).setHeader("Czas");

        fruitDeliveryGrid.setSizeFull();

        this.add(refreshButton);
        this.add(fruitDeliveryGrid);
        refreshGrid();
    }

    private void refreshGrid(){
        fruitDeliveryGrid.setItems(fruitDeliveryService.findAll());
    }
}
