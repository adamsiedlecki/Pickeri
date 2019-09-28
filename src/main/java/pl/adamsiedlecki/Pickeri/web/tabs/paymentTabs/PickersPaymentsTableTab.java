package pl.adamsiedlecki.Pickeri.web.tabs.paymentTabs;

import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

@Component
@Scope("prototype")
public class PickersPaymentsTableTab extends VerticalLayout {

    private FruitPickerService fruitPickerService;
    private Grid<FruitPicker> pickerGrid;

    @Autowired
    public PickersPaymentsTableTab(FruitPickerService fruitPickerService) {
        this.fruitPickerService = fruitPickerService;
        pickerGrid = new Grid<>();
        Button refreshButton = new Button("REFRESH");
        pickerGrid.addColumn(FruitPicker::getId).setCaption("ID");
        pickerGrid.addColumn(FruitPicker::getName).setCaption("Imię");
        pickerGrid.addColumn(FruitPicker::getLastName).setCaption("Nazwisko");
        pickerGrid.addColumn(FruitPicker::getFundsPaid).setCaption("Wypłacone środki [zł]");
        refreshData();
        refreshButton.addClickListener(e -> refreshData());
        this.addComponents(refreshButton, pickerGrid);
    }

    private void refreshData() {
        pickerGrid.setItems(fruitPickerService.findAll());
    }

}
