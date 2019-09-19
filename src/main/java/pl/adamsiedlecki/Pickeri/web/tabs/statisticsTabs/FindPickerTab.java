package pl.adamsiedlecki.Pickeri.web.tabs.statisticsTabs;

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
    private FruitTypeService fruitTypeService;
    private FruitPickerService fruitPickerService;

    @Autowired
    public FindPickerTab(FruitPickerService fruitPickerService, FruitTypeService fruitTypeService){
        this.fruitTypeService = fruitTypeService;
        this.fruitPickerService = fruitPickerService;
        initComponents();
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
        addTypeColumnIfExists(0);
        addTypeColumnIfExists(1);
        addTypeColumnIfExists(2);
        addTypeColumnIfExists(3);
        fruitPickerGrid.setSizeFull();
        fruitPickerGrid.setItems(fruitPickerService.findAll(filter.getValue()));
        fruitPickerGrid.setHeight(700, Unit.PIXELS);
        root.addComponents(fruitPickerGrid);
    }

    private void addTypeColumnIfExists(int slot){
        if(fruitTypeService.getType(slot)!=null
                && fruitTypeService.getType(slot).getName()!=null
                && !fruitTypeService.getType(slot).getName().equals("")){
            if(slot==0){
                fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeOne)
                        .setCaption(fruitTypeService.getType(slot).getName());
            }else if(slot==1){
                fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeTwo)
                        .setCaption(fruitTypeService.getType(slot).getName());
            }else if(slot==2){
                fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeThree)
                        .setCaption(fruitTypeService.getType(2).getName());
            }else if(slot==3){
                fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeFour)
                        .setCaption(fruitTypeService.getType(3).getName());
            }
        }
    }

}
