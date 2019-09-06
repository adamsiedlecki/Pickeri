package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

@SpringComponent
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
        filter.addValueChangeListener(e->
            fruitPickerGrid.setItems(fruitPickerService.findAll(filter.getValue()))
        );
        filter.setValueChangeMode(ValueChangeMode.LAZY);
    }

    private void initComponents(){
        root = new VerticalLayout();
        filter = new TextField();
        fruitPickerGrid = new Grid<>();

        root.addComponents(filter,fruitPickerGrid);
        fruitPickerGrid.addColumn(FruitPicker::getId).setCaption("ID");
        fruitPickerGrid.addColumn(FruitPicker::getName).setCaption("Imię");
        fruitPickerGrid.addColumn(FruitPicker::getLastName).setCaption("Nazwisko");
        fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryAmount).setCaption("Suma wszystkich opakowań");
        fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeOne).setCaption("Z szypułką");
        fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeTwo).setCaption("Bez szypułki");
        fruitPickerGrid.setSizeFull();

        this.addComponent(root);
    }

}
