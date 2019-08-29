package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

@Component
@Scope("prototype")
public class FindPickerTab extends VerticalLayout {

    private VerticalLayout root;
    private TextField filter;
    private Grid<FruitPicker> fruitPickerGrid;
    private FruitPickerService fruitPickerService;

    @Autowired
    public FindPickerTab(FruitPickerService fruitPickerService){
        this.fruitPickerService = fruitPickerService;
        initComponents();
        fruitPickerGrid.setItems(fruitPickerService.findAll(filter.getValue()));
        filter.addValueChangeListener(e->{
            fruitPickerGrid.setItems(fruitPickerService.findAll(filter.getValue()));
        });
        filter.setValueChangeMode(ValueChangeMode.ON_CHANGE);

    }

    private void initComponents(){
        root = new VerticalLayout();
        filter = new TextField();
        fruitPickerGrid = new Grid<>();

        root.add(filter,fruitPickerGrid);

        fruitPickerGrid.addColumn(FruitPicker::getId).setHeader("ID");
        fruitPickerGrid.addColumn(FruitPicker::getName).setHeader("Imię");
        fruitPickerGrid.addColumn(FruitPicker::getLastName).setHeader("Nazwisko");
        fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryAmount).setHeader("Suma wszystkich opakowań");
        fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithCalyx).setHeader("Z szypułką");
        fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithoutCalyx).setHeader("Bez szypułki");
        fruitPickerGrid.setSizeFull();

        this.add(root);
    }

}
