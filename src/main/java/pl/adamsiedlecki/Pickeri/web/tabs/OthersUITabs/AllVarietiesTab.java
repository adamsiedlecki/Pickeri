package pl.adamsiedlecki.Pickeri.web.tabs.OthersUITabs;

import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;

@SpringUI
@Scope("prototype")
public class AllVarietiesTab extends VerticalLayout {

    private Grid<FruitVariety> varietyGrid;
    private Button refreshButton;
    private FruitVarietyService fruitVarietyService;

    @Autowired
    public AllVarietiesTab(FruitVarietyService fruitVarietyService) {
        this.fruitVarietyService = fruitVarietyService;
        refreshButton = new Button("ODŚWIEŻ");
        this.addComponent(refreshButton);
        varietyGrid = new Grid<>();
        varietyGrid.addColumn(FruitVariety::getId).setCaption("ID");
        varietyGrid.addColumn(FruitVariety::getName).setCaption("Nazwa");
        varietyGrid.addColumn(FruitVariety::getComment).setCaption("Komentarz do odmiany");
        varietyGrid.setWidth(80, Unit.PERCENTAGE);
        varietyGrid.setHeight(700, Unit.PIXELS);
        refreshButton.addClickListener(x -> refreshTable());
        this.addComponent(varietyGrid);
        this.setComponentAlignment(varietyGrid, Alignment.MIDDLE_CENTER);
        refreshTable();
    }

    private void refreshTable() {
        varietyGrid.setItems(fruitVarietyService.findAll());
    }

}
