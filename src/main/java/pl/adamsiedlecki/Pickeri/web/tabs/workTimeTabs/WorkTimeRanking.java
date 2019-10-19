package pl.adamsiedlecki.Pickeri.web.tabs.workTimeTabs;

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

@Component
@Scope("prototype")
public class WorkTimeRanking extends VerticalLayout {

    private Grid<FruitPicker> fruitPickerGrid;
    private FruitPickerService fruitPickerService;

    public WorkTimeRanking(Environment env, FruitPickerService fruitPickerService){
        this.fruitPickerService = fruitPickerService;
        fruitPickerGrid = new Grid<>();
        Button refreshButton = new Button(env.getProperty("refresh.button"));

        fruitPickerGrid.addColumn(FruitPicker::getId);
        fruitPickerGrid.addColumn(FruitPicker::getName);
        fruitPickerGrid.addColumn(FruitPicker::getWorkTimeHours).setId("hours");
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
