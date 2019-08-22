package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

import java.util.List;

@Component
public class RankingTab extends VerticalLayout {

    private FruitPickerService fruitPickerService;
    private Grid<FruitPicker> pickersGrid;

    @Autowired
    public RankingTab(FruitPickerService fruitPickerService) {
        this.fruitPickerService = fruitPickerService;

        addContent();
    }

    private void addContent(){
        pickersGrid = new Grid<>();
        pickersGrid.addColumn(FruitPicker::getPackageDeliveryAmount);
        pickersGrid.addColumn(FruitPicker::getName).setCaption("Imię");
        pickersGrid.addColumn(FruitPicker::getLastName).setCaption("Nazwisko");
        pickersGrid.addColumn(FruitPicker::getGender).setCaption("Płeć");

        pickersGrid.setItems(getCurrentPickers());
        this.addComponent(pickersGrid);
    }

    private List<FruitPicker> getCurrentPickers(){
        return fruitPickerService.findAll();
    }

    public void refreshData(){
        pickersGrid.setItems(getCurrentPickers());
    }

}
