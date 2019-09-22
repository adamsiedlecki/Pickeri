package pl.adamsiedlecki.Pickeri.web.tabs.statisticsTabs;

import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@SpringComponent
@Scope("prototype")
public class StatisticsTab extends VerticalLayout {

    private FruitDeliveryService fruitDeliveryService;
    private FruitPickerService fruitPickerService;
    private FruitVarietyService fruitVarietyService;
    private Label pickersSumLabel;
    private Label packagesSumLabel;
    private Label varietiesSumLabel;
    private Label weightSumLabel;
    private Grid<FruitVariety> varietiesGridPackageStat;
    private Grid<FruitVariety> varietiesGridWeightStat;

    @Autowired
    public StatisticsTab(FruitDeliveryService fruitDeliveryService, FruitPickerService fruitPickerService,
                         FruitVarietyService fruitVarietyService) {
        this.fruitVarietyService = fruitVarietyService;
        this.fruitDeliveryService = fruitDeliveryService;
        this.fruitPickerService = fruitPickerService;
        initComponents();
    }

    private void initComponents() {
        VerticalLayout root = new VerticalLayout();
        pickersSumLabel = new Label();
        packagesSumLabel = new Label();
        varietiesSumLabel = new Label();
        weightSumLabel = new Label();
        VerticalLayout varietiesPackageAmountAndPercentageLayout = new VerticalLayout();
        Button refreshButton = new Button("Odśwież");
        refreshButton.addClickListener(e -> refreshData());
        varietiesGridPackageStat = new Grid<>();
        varietiesGridPackageStat.addColumn(FruitVariety::getId).setCaption("ID");
        varietiesGridPackageStat.addColumn(FruitVariety::getName).setCaption("Nazwa");
        varietiesGridPackageStat.addColumn(FruitVariety::getTotalPackages).setCaption("Suma opakowań").setId("total");
        varietiesGridPackageStat.addColumn(FruitVariety::getPercentageParticipationInPackagesAmountPlainText).setCaption("% udział");

        varietiesGridWeightStat = new Grid<>();
        varietiesGridWeightStat.addColumn(FruitVariety::getId).setCaption("ID");
        varietiesGridWeightStat.addColumn(FruitVariety::getName).setCaption("Nazwa");
        varietiesGridWeightStat.addColumn(FruitVariety::getTotalWeightKgPlainText).setCaption("Waga całkowita").setId("total");
        varietiesGridWeightStat.addColumn(FruitVariety::getPercentageParticipationInWeightPlainText).setCaption("% udział");

        refreshData();

        root.addComponent(refreshButton);
        root.addComponent(pickersSumLabel);
        root.addComponent(packagesSumLabel);
        root.addComponent(varietiesSumLabel);
        root.addComponent(weightSumLabel);
        root.addComponent(varietiesPackageAmountAndPercentageLayout);
        root.addComponent(varietiesGridPackageStat);
        root.addComponent(varietiesGridWeightStat);

        this.addComponent(root);
    }

    private void refreshData() {
        pickersSumLabel.setValue("Ilość pracowników w systemie: " + fruitPickerService.getTotalAmountOfPickers());
        packagesSumLabel.setValue("Suma wszystkich opakowań: " + fruitDeliveryService.getTotalAmountOfPackages());
        varietiesSumLabel.setValue("Suma odmian w systemie: " + fruitVarietyService.findAll().size());
        weightSumLabel.setValue("Całkowita masa owoców w systemie [w kg]: " + fruitDeliveryService.getWeightSum()
                .divide(new BigDecimal(1000), 4, RoundingMode.FLOOR));
        List<FruitVariety> varieties = fruitVarietyService.findAll();

        setGrid(varietiesGridPackageStat, varieties);
        setGrid(varietiesGridWeightStat, varieties);
    }

    private void setGrid(Grid<FruitVariety> grid, List<FruitVariety> varieties) {
        grid.setItems(varieties);
        grid.setSizeFull();
        grid.sort("total", SortDirection.DESCENDING);
    }

}
