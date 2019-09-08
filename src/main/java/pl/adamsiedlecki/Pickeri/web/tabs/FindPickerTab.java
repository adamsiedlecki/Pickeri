package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.entity.FruitType;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitTypeService;

@SpringComponent
@Scope("prototype")
public class FindPickerTab extends VerticalLayout {

    private VerticalLayout root;
    private TextField filter;
    private Grid<FruitPicker> fruitPickerGrid;
    private FruitPickerService fruitPickerService;
    private FruitTypeService fruitTypeService;


    @Autowired
    public FindPickerTab(FruitPickerService fruitPickerService, FruitTypeService fruitTypeService){
        this.fruitTypeService = fruitTypeService;
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
        Button refreshButton = new Button("ODŚWIEŻ");
        refreshButton.addClickListener(e->refreshData());
        root.addComponent(refreshButton);
        root.addComponents(filter);
        refreshData();

        this.addComponent(root);
    }

    private void refreshData(){
        fruitPickerGrid.removeAllColumns();
        fruitPickerGrid.addColumn(FruitPicker::getId).setCaption("ID");
        fruitPickerGrid.addColumn(FruitPicker::getName).setCaption("Imię");
        fruitPickerGrid.addColumn(FruitPicker::getLastName).setCaption("Nazwisko");
        fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryAmount).setCaption("Suma wszystkich opakowań");

        if(fruitTypeService.getType(0)!=null
                && fruitTypeService.getType(0).getName()!=null
                && !fruitTypeService.getType(0).getName().equals("")){
                fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeOne)
                    .setCaption(fruitTypeService.getType(0).getName());
        }
        if(fruitTypeService.getType(1)!=null
                && fruitTypeService.getType(1).getName()!=null
                && !fruitTypeService.getType(1).getName().equals("")){
            fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeTwo)
                    .setCaption(fruitTypeService.getType(1).getName());
        }
        if(fruitTypeService.getType(2)!=null
                && fruitTypeService.getType(2).getName()!=null
                && !fruitTypeService.getType(2).getName().equals("")){
            fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeThree)
                    .setCaption(fruitTypeService.getType(2).getName());
        }
        if(fruitTypeService.getType(3)!=null
                && fruitTypeService.getType(3).getName()!=null
                && !fruitTypeService.getType(3).getName().equals("")){
            fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeFour)
                    .setCaption(fruitTypeService.getType(3).getName());
        }
        fruitPickerGrid.setSizeFull();
        root.addComponents(fruitPickerGrid);
    }

}
