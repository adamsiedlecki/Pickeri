package pl.adamsiedlecki.Pickeri.web.tab.paymentTabs;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

import java.util.Objects;

@Component
@Scope("prototype")
public class PickersPaymentsTableTab extends VerticalLayout {

    private final FruitPickerService fruitPickerService;
    private final Grid<FruitPicker> pickerGrid;
    private final Environment env;

    @Autowired
    public PickersPaymentsTableTab(FruitPickerService fruitPickerService, Environment environment) {
        this.env = environment;
        this.fruitPickerService = fruitPickerService;
        pickerGrid = new Grid<>();
        Button refreshButton = new Button(env.getProperty("refresh.button"));
        pickerGrid.addColumn(FruitPicker::getId).setCaption(Objects.requireNonNull(env.getProperty("id.column.caption")));
        pickerGrid.addColumn(FruitPicker::getName).setCaption(Objects.requireNonNull(env.getProperty("name.person.column")));
        pickerGrid.addColumn(FruitPicker::getLastName).setCaption(Objects.requireNonNull(env.getProperty("surname.column")));
        pickerGrid.addColumn(FruitPicker::getFundsPaid).setCaption(Objects.requireNonNull(env.getProperty("payments.done.column")));
        refreshData();
        refreshButton.addClickListener(e -> refreshData());
        this.addComponents(refreshButton, pickerGrid);
        this.setComponentAlignment(refreshButton, Alignment.MIDDLE_CENTER);
        this.setComponentAlignment(pickerGrid, Alignment.MIDDLE_CENTER);
        pickerGrid.setHeight(90, Unit.PERCENTAGE);
        pickerGrid.setWidth(80, Unit.PERCENTAGE);
    }

    private void refreshData() {
        pickerGrid.setItems(fruitPickerService.findAll());
    }

}
