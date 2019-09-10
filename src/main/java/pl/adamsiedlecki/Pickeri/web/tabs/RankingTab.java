package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

import java.util.List;

@SpringComponent
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
        pickersGrid.addColumn(FruitPicker::getPackageDeliveryAmount).setCaption("Suma opakowań").setId("packageDeliveryAmount");
        pickersGrid.addColumn(FruitPicker::getWeightSumKgPlainText).setCaption("Waga [kg]");
        pickersGrid.addColumn(FruitPicker::getName).setCaption("Imię");
        pickersGrid.addColumn(FruitPicker::getLastName).setCaption("Nazwisko");
        pickersGrid.addColumn(FruitPicker::getGender).setCaption("Płeć");

        pickersGrid.setSizeFull();

        refreshData();
        this.addComponent(refreshButton);
        this.addComponent(pickersGrid);
    }

    private List<FruitPicker> getCurrentPickers(){
        return fruitPickerService.findAll();
    }

    private void refreshData(){
        pickersGrid.setItems(getCurrentPickers());
        pickersGrid.sort("packageDeliveryAmount", SortDirection.DESCENDING);
    }

}
