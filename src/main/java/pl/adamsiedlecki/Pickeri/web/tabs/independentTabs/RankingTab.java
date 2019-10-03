package pl.adamsiedlecki.Pickeri.web.tabs.independentTabs;

import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

import java.util.List;
import java.util.Objects;

@SpringComponent
@Scope("prototype")
public class RankingTab extends VerticalLayout {

    private FruitPickerService fruitPickerService;
    private Grid<FruitPicker> pickersGrid;
    private Button refreshButton;
    private Environment env;

    @Autowired
    public RankingTab(FruitPickerService fruitPickerService, Environment environment) {
        this.fruitPickerService = fruitPickerService;
        this.env = environment;
        addContent();
    }

    private void addContent() {
        refreshButton = new Button(env.getProperty("refresh.button"));
        refreshButton.addClickListener(e -> refreshData());
        pickersGrid = new Grid<>();
        pickersGrid.addColumn(FruitPicker::getPackageDeliveryAmount).setCaption(Objects.requireNonNull(env.getProperty("packages.sum.column.caption"))).setId("packageDeliveryAmount");
        pickersGrid.addColumn(FruitPicker::getWeightSumKgPlainText).setCaption(Objects.requireNonNull(env.getProperty("weight.kg.column")));
        pickersGrid.addColumn(FruitPicker::getName).setCaption(Objects.requireNonNull(env.getProperty("name.column")));
        pickersGrid.addColumn(FruitPicker::getLastName).setCaption(Objects.requireNonNull(env.getProperty("surname.column")));
        pickersGrid.addColumn(FruitPicker::getGender).setCaption(Objects.requireNonNull(env.getProperty("gender.column")));
        pickersGrid.setSizeFull();
        refreshData();
        this.addComponent(refreshButton);
        this.addComponent(pickersGrid);
    }

    private List<FruitPicker> getCurrentPickers() {
        return fruitPickerService.findAll();
    }

    private void refreshData() {
        pickersGrid.setItems(getCurrentPickers());
        pickersGrid.sort("packageDeliveryAmount", SortDirection.DESCENDING);
        pickersGrid.setHeight(700, Unit.PIXELS);
    }

}
