package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

import java.util.List;

@Component
@Scope("prototype")
public class RankingTab extends VerticalLayout {

    private FruitPickerService fruitPickerService;
    private Grid<FruitPicker> pickersGrid;
    private Button refreshButton;

    @Autowired
    public RankingTab(FruitPickerService fruitPickerService) {
        this.fruitPickerService = fruitPickerService;

        addContent();
    }

    private void addContent(){
        refreshButton = new Button("Odśwież");
        refreshButton.addClickListener(e->refreshData());
        pickersGrid = new Grid<>();
        pickersGrid.addColumn(FruitPicker::getPackageDeliveryAmount).setHeader("Suma opakowań");
        pickersGrid.addColumn(FruitPicker::getName).setHeader("Imię");
        pickersGrid.addColumn(FruitPicker::getLastName).setHeader("Nazwisko");
        pickersGrid.addColumn(FruitPicker::getGender).setHeader("Płeć");

        pickersGrid.setSizeFull();

        pickersGrid.setItems(getCurrentPickers());
        this.add(refreshButton);
        this.add(pickersGrid);
    }

    private List<FruitPicker> getCurrentPickers(){
        return fruitPickerService.findAll();
    }

    private void refreshData(){
        pickersGrid.setItems(getCurrentPickers());
    }

}
