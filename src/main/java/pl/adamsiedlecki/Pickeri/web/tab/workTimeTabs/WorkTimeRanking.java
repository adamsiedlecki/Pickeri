package pl.adamsiedlecki.Pickeri.web.tab.workTimeTabs;

import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

import java.util.Objects;

@Component
@Scope("prototype")
public class WorkTimeRanking extends VerticalLayout {

    private Grid<FruitPicker> fruitPickerGrid;
    private FruitPickerService fruitPickerService;

    public WorkTimeRanking(Environment env, FruitPickerService fruitPickerService){
        this.fruitPickerService = fruitPickerService;
        fruitPickerGrid = new Grid<>();
        Button refreshButton = new Button(env.getProperty("refresh.button"));

        fruitPickerGrid.addColumn(FruitPicker::getId).setCaption(Objects.requireNonNull(env.getProperty("id.column")));
        fruitPickerGrid.addColumn(FruitPicker::getName).setCaption(Objects.requireNonNull(env.getProperty("name.person.column")));
        fruitPickerGrid.addColumn(FruitPicker::getLastName).setCaption(Objects.requireNonNull(env.getProperty("surname.column")));
        fruitPickerGrid.addColumn(FruitPicker::getWorkTimeHours).setId("hours").setCaption(Objects.requireNonNull(env.getProperty("hours.amount")));
        fruitPickerGrid.setWidth(80, Unit.PERCENTAGE);
        this.addComponents(refreshButton, fruitPickerGrid);
        refreshButton.addClickListener(e->refreshData());
        refreshData();
        this.forEach(component -> this.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
    }

    private void refreshData(){
        fruitPickerGrid.setItems(fruitPickerService.findAll());
        fruitPickerGrid.sort("hours", SortDirection.DESCENDING);
    }

}
