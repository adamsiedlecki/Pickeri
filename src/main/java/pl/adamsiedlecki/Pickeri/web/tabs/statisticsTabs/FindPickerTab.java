package pl.adamsiedlecki.Pickeri.web.tabs.statisticsTabs;

import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitTypeService;

import java.util.Objects;

@SpringComponent
@Scope("prototype")
public class FindPickerTab extends VerticalLayout {

    private VerticalLayout root;
    private TextField filter;
    private Grid<FruitPicker> fruitPickerGrid;
    private FruitTypeService fruitTypeService;
    private FruitPickerService fruitPickerService;
    private Environment env;

    @Autowired
    public FindPickerTab(FruitPickerService fruitPickerService, FruitTypeService fruitTypeService, Environment env) {
        this.fruitTypeService = fruitTypeService;
        this.fruitPickerService = fruitPickerService;
        this.env = env;
        initComponents();
        filter.addValueChangeListener(e ->
                fruitPickerGrid.setItems(fruitPickerService.findAll(filter.getValue()))
        );
        filter.setValueChangeMode(ValueChangeMode.LAZY);
    }

    private void initComponents() {
        root = new VerticalLayout();
        filter = new TextField();
        fruitPickerGrid = new Grid<>();
        Button refreshButton = new Button(env.getProperty("refresh.button"));
        refreshButton.addClickListener(e -> refreshData());
        root.addComponent(refreshButton);
        root.addComponents(filter);
        refreshData();
        this.addComponent(root);
    }

    private void refreshData() {
        fruitPickerGrid.removeAllColumns();
        fruitPickerGrid.addColumn(FruitPicker::getId).setCaption(Objects.requireNonNull(env.getProperty("id.column")));
        fruitPickerGrid.addColumn(FruitPicker::getName).setCaption(Objects.requireNonNull(env.getProperty("name.person.column")));
        fruitPickerGrid.addColumn(FruitPicker::getLastName).setCaption(Objects.requireNonNull(env.getProperty("surname.column")));
        fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryAmount).setCaption(Objects.requireNonNull(env.getProperty("all.packages.sum.label")));
        addTypeColumnIfExists(0);
        addTypeColumnIfExists(1);
        addTypeColumnIfExists(2);
        addTypeColumnIfExists(3);
        fruitPickerGrid.setSizeFull();
        fruitPickerGrid.setItems(fruitPickerService.findAll(filter.getValue()));
        fruitPickerGrid.setHeight(700, Unit.PIXELS);
        root.addComponents(fruitPickerGrid);
    }

    private void addTypeColumnIfExists(int slot) {
        if (fruitTypeService.getType(slot) != null
                && fruitTypeService.getType(slot).getName() != null
                && !fruitTypeService.getType(slot).getName().equals("")) {
            if (slot == 0) {
                fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeOne)
                        .setCaption(fruitTypeService.getType(slot).getName() + env.getProperty("package.unit"));
                fruitPickerGrid.addColumn(FruitPicker::getWeightKgWithTypeOnePlainText)
                        .setCaption(fruitTypeService.getType(slot).getName() + env.getProperty("weight.kg"));
            } else if (slot == 1) {
                fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeTwo)
                        .setCaption(fruitTypeService.getType(slot).getName() + env.getProperty("package.unit"));
                fruitPickerGrid.addColumn(FruitPicker::getWeightKgWithTypeTwoPlainText)
                        .setCaption(fruitTypeService.getType(slot).getName() + env.getProperty("weight.kg"));
            } else if (slot == 2) {
                fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeThree)
                        .setCaption(fruitTypeService.getType(2).getName() + env.getProperty("package.unit"));
                fruitPickerGrid.addColumn(FruitPicker::getWeightKgWithTypeThreePlainText)
                        .setCaption(fruitTypeService.getType(slot).getName() + env.getProperty("weight.kg"));
            } else if (slot == 3) {
                fruitPickerGrid.addColumn(FruitPicker::getPackageDeliveryWithTypeFour)
                        .setCaption(fruitTypeService.getType(3).getName() + env.getProperty("package.unit"));
                fruitPickerGrid.addColumn(FruitPicker::getWeightKgWithTypeFourPlainText)
                        .setCaption(fruitTypeService.getType(slot).getName() + env.getProperty("weight.kg"));
            }
        }
    }

}
