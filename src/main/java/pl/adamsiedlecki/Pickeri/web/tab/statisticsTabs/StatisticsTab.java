package pl.adamsiedlecki.Pickeri.web.tab.statisticsTabs;

import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;
import pl.adamsiedlecki.Pickeri.service.WorkTimeService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@SpringComponent
@Scope("prototype")
public class StatisticsTab extends VerticalLayout {

    private FruitDeliveryService fruitDeliveryService;
    private FruitPickerService fruitPickerService;
    private FruitVarietyService fruitVarietyService;
    private Label pickersSumLabel;
    private Label packagesSumLabel;
    private Label weightSumLabel;
    private Label timeSumLabel;
    private Label varietiesSumLabel;
    private Grid<FruitVariety> varietiesGridPackageStat;
    private Grid<FruitVariety> varietiesGridWeightStat;
    private Environment env;
    private WorkTimeService workTimeService;

    @Autowired
    public StatisticsTab(FruitDeliveryService fruitDeliveryService, FruitPickerService fruitPickerService,
                         FruitVarietyService fruitVarietyService, Environment env, WorkTimeService workTimeService) {
        this.fruitVarietyService = fruitVarietyService;
        this.fruitDeliveryService = fruitDeliveryService;
        this.fruitPickerService = fruitPickerService;
        this.env = env;
        this.workTimeService = workTimeService;
        initComponents();
    }

    private void initComponents() {
        VerticalLayout root = new VerticalLayout();
        pickersSumLabel = new Label();
        packagesSumLabel = new Label();
        varietiesSumLabel = new Label();
        weightSumLabel = new Label();
        timeSumLabel = new Label();
        VerticalLayout varietiesPackageAmountAndPercentageLayout = new VerticalLayout();
        Button refreshButton = new Button(env.getProperty("refresh.button"));
        refreshButton.addClickListener(e -> refreshData());
        varietiesGridPackageStat = new Grid<>();
        varietiesGridPackageStat.addColumn(FruitVariety::getId)
                .setCaption(Objects.requireNonNull(env.getProperty("id.column")));
        varietiesGridPackageStat.addColumn(FruitVariety::getName)
                .setCaption(Objects.requireNonNull(env.getProperty("name.column")));
        varietiesGridPackageStat.addColumn(FruitVariety::getTotalPackages)
                .setCaption(Objects.requireNonNull(env.getProperty("packages.sum.column.caption"))).setId("total");
        varietiesGridPackageStat.addColumn(FruitVariety::getPercentageParticipationInPackagesAmountPlainText)
                .setCaption(Objects.requireNonNull(env.getProperty("percentage.participation")));

        varietiesGridWeightStat = new Grid<>();
        varietiesGridWeightStat.addColumn(FruitVariety::getId)
                .setCaption(Objects.requireNonNull(env.getProperty("id.column")));
        varietiesGridWeightStat.addColumn(FruitVariety::getName)
                .setCaption(Objects.requireNonNull(env.getProperty("name.column")));
        varietiesGridWeightStat.addColumn(FruitVariety::getTotalWeightKgPlainText)
                .setCaption(Objects.requireNonNull(env.getProperty("weight.sum"))).setId("total");
        varietiesGridWeightStat.addColumn(FruitVariety::getPercentageParticipationInWeightPlainText)
                .setCaption(Objects.requireNonNull(env.getProperty("percentage.participation")));

        refreshData();

        root.addComponent(refreshButton);
        root.addComponent(pickersSumLabel);
        root.addComponent(weightSumLabel);
        root.addComponent(timeSumLabel);
        root.addComponent(packagesSumLabel);
        root.addComponent(varietiesSumLabel);
        root.addComponent(varietiesPackageAmountAndPercentageLayout);
        root.addComponent(varietiesGridPackageStat);
        root.addComponent(varietiesGridWeightStat);

        this.addComponent(root);
    }

    private void refreshData() {
        RefreshThread refreshThread = new RefreshThread();
        refreshThread.start();
    }

    private void setGrid(Grid<FruitVariety> grid, List<FruitVariety> varieties) {
        grid.setItems(varieties);
        grid.setSizeFull();
        grid.sort("total", SortDirection.DESCENDING);
    }

    private class RefreshThread extends Thread{
        @Override
        public void run(){
            timeSumLabel.setValue(env.getProperty("time.sum") + workTimeService.getWorkTimeSum());
            pickersSumLabel.setValue(env.getProperty("employees.total.amount") + fruitPickerService.getTotalAmountOfPickers());
            packagesSumLabel.setValue(env.getProperty("all.packages.sum.label") + fruitDeliveryService.getTotalAmountOfPackages());
            varietiesSumLabel.setValue(env.getProperty("varieties.total.amount") + fruitVarietyService.findAll().size());
            weightSumLabel.setValue(env.getProperty("total.fruits.weight") + fruitDeliveryService.getWeightSum()
                    .divide(new BigDecimal(1000), 4, RoundingMode.FLOOR));
            List<FruitVariety> varieties = fruitVarietyService.findAll();
            setGrid(varietiesGridPackageStat, varieties);
            setGrid(varietiesGridWeightStat, varieties);
        }
    }

}
