package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;

@SpringComponent
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
        varietiesGrid.addColumn(FruitVariety::getName).setCaption("Nazwa");
        varietiesGrid.addColumn(FruitVariety::getTotalPackages).setCaption("Suma opakowań").setId("totalPackages");
        varietiesGrid.addColumn(FruitVariety::getPercentageParticipationInPackagesAmount).setCaption("% udział");

        refreshData();

        root.addComponent(refreshButton);
        root.addComponent(pickersSum);
        root.addComponent(packagesSum);
        root.addComponent(varietiesSum);
        root.addComponent(varietiesPackageAmountAndPercentageLayout);
        root.addComponent(varietiesGrid);

        this.addComponent(root);
    }

    private void refreshData(){
        pickersSum.setValue("Ilość pracowników w systemie: "+fruitPickerService.getTotalAmountOfPickers());
        packagesSum.setValue("Suma szystkich opakowań: "+fruitDeliveryService.getTotalAmountOfPackages());
        varietiesSum.setValue("Suma odmian w systemie: "+fruitVarietyService.findAll().size());

        varietiesGrid.setItems(fruitVarietyService.findAll());
        varietiesGrid.setSizeFull();
        varietiesGrid.sort("totalPackages", SortDirection.DESCENDING);
    }

}
