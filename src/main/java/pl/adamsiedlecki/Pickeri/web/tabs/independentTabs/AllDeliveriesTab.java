package pl.adamsiedlecki.Pickeri.web.tabs.independentTabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;

@SpringComponent
@Scope("prototype")
public class AllDeliveriesTab extends VerticalLayout {

    private Grid<FruitDelivery> fruitDeliveryGrid;
    private FruitDeliveryService fruitDeliveryService;

    @Autowired
    public AllDeliveriesTab(FruitDeliveryService fruitDeliveryService) {
        this.fruitDeliveryService = fruitDeliveryService;
        addComponents();
    }

    private void addComponents() {
        Button refreshButton = new Button("Odśwież");
        refreshButton.addClickListener(e -> refreshGrid());
        fruitDeliveryGrid = new Grid<>();
        fruitDeliveryGrid.addColumn(FruitDelivery::getPackageAmount).setCaption("Ilość opakowań");
        fruitDeliveryGrid.addColumn(FruitDelivery::getWeightSumKgPlainText).setCaption("Waga [kg]");
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitPickerId).setCaption("Id pracownika");
        fruitDeliveryGrid.addColumn(FruitDelivery::getType).setCaption("Typ");
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitVarietyName).setCaption("Odmiana");
        fruitDeliveryGrid.addColumn(FruitDelivery::getComment).setCaption("Komentarz");
        fruitDeliveryGrid.addColumn(FruitDelivery::getDeliveryTimeFormatted).setCaption("Czas");
        fruitDeliveryGrid.addColumn(FruitDelivery::getId).setCaption("ID");
        fruitDeliveryGrid.setSizeFull();
        this.addComponent(refreshButton);
        this.addComponent(fruitDeliveryGrid);
        refreshGrid();
    }

    private void refreshGrid() {
        fruitDeliveryGrid.setItems(fruitDeliveryService.findAll());
        fruitDeliveryGrid.setHeight(700, Unit.PIXELS);
    }
}
