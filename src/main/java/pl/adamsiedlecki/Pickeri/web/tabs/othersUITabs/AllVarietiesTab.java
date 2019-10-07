package pl.adamsiedlecki.Pickeri.web.tabs.othersUITabs;

import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;

import java.util.Objects;

@SpringUI
@Scope("prototype")
public class AllVarietiesTab extends VerticalLayout {

    private Grid<FruitVariety> varietyGrid;
    private Button refreshButton;
    private FruitVarietyService fruitVarietyService;
    private Environment env;

    @Autowired
    public AllVarietiesTab(FruitVarietyService fruitVarietyService, Environment environment) {
        this.env = environment;
        this.fruitVarietyService = fruitVarietyService;
        refreshButton = new Button(env.getProperty("refresh.button"));
        this.addComponent(refreshButton);
        varietyGrid = new Grid<>();
        varietyGrid.addColumn(FruitVariety::getId).setCaption(Objects.requireNonNull(env.getProperty("id.column")));
        varietyGrid.addColumn(FruitVariety::getName).setCaption(Objects.requireNonNull(env.getProperty("name.column")));
        varietyGrid.addColumn(FruitVariety::getComment).setCaption(Objects.requireNonNull(env.getProperty("comment")));
        varietyGrid.setWidth(80, Unit.PERCENTAGE);
        varietyGrid.setHeight(700, Unit.PIXELS);
        refreshButton.addClickListener(x -> refreshTable());
        this.addComponent(varietyGrid);
        this.setComponentAlignment(varietyGrid, Alignment.MIDDLE_CENTER);
        refreshTable();
    }

    private void refreshTable() {
        varietyGrid.setItems(fruitVarietyService.findAll());
    }

}
