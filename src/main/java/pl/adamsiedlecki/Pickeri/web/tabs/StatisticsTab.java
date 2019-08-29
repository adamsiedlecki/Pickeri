package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;

@Component
@Scope("prototype")
public class StatisticsTab extends VerticalLayout {

    private FruitDeliveryService fruitDeliveryService;
    private FruitPickerService fruitPickerService;
    private FruitVarietyService fruitVarietyService;
    private VerticalLayout root;
    private Label pickersSum;
    private Label packagesSum;
    private Label varietiesSum;
    private VerticalLayout varietiesPackageAmountAndPercentageLayout;
    private Button refreshButton;
    private Grid<FruitVariety> varietiesGrid;

    @Autowired
    public StatisticsTab(FruitDeliveryService fruitDeliveryService, FruitPickerService fruitPickerService,
                         FruitVarietyService fruitVarietyService){
        this.fruitVarietyService = fruitVarietyService;
        this.fruitDeliveryService = fruitDeliveryService;
        this.fruitPickerService = fruitPickerService;
        initComponents();
    }

    private void initComponents(){
        root = new VerticalLayout();
        pickersSum = new Label();
        packagesSum = new Label();
        varietiesSum = new Label();
        varietiesPackageAmountAndPercentageLayout = new VerticalLayout();

        refreshButton = new Button("Odśwież");
        refreshButton.addClickListener(e->refreshData());

        varietiesGrid = new Grid<>();
        varietiesGrid.addColumn(FruitVariety::getName).setHeader("Nazwa");
        varietiesGrid.addColumn(FruitVariety::getTotalPackages).setHeader("Suma opakowań");
        varietiesGrid.addColumn(FruitVariety::getPercentageParticipationInPackagesAmount).setHeader("% udział");

        refreshData();

        root.add(refreshButton);
        root.add(pickersSum);
        root.add(packagesSum);
        root.add(varietiesSum);
        root.add(varietiesPackageAmountAndPercentageLayout);
        root.add(varietiesGrid);

        //root.setComponentAlignment(pickersSum, Alignment.MIDDLE_CENTER);

        this.add(root);
    }

    private void refreshData(){
        pickersSum.setText("Ilość pracowników w systemie: "+fruitPickerService.getTotalAmountOfPickers());
        packagesSum.setText("Suma szystkich opakowań: "+fruitDeliveryService.getTotalAmountOfPackages());
        varietiesSum.setText("Suma odmian w systemie: "+fruitVarietyService.findAll().size());

        varietiesGrid.setItems(fruitVarietyService.findAll());
        varietiesGrid.setSizeFull();
    }

}
