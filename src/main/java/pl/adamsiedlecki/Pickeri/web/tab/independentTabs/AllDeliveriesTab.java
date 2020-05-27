package pl.adamsiedlecki.Pickeri.web.tab.independentTabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;

import java.util.Objects;

@SpringComponent
@Scope("prototype")
public class AllDeliveriesTab extends VerticalLayout {

    private Grid<FruitDelivery> fruitDeliveryGrid;
    private final FruitDeliveryService fruitDeliveryService;
    private final Environment env;

    @Autowired
    public AllDeliveriesTab(FruitDeliveryService fruitDeliveryService, Environment environment) {
        this.fruitDeliveryService = fruitDeliveryService;
        this.env = environment;
        addComponents();
    }

    private void addComponents() {
        Button refreshButton = new Button(env.getProperty("refresh.button"));
        refreshButton.addClickListener(e -> refreshGrid());
        fruitDeliveryGrid = new Grid<>();
        fruitDeliveryGrid.addColumn(FruitDelivery::getPackageAmount).setCaption(Objects.requireNonNull(env.getProperty("package.amount")));
        fruitDeliveryGrid.addColumn(FruitDelivery::getWeightSumKgPlainText).setCaption(Objects.requireNonNull(env.getProperty("weight.kg.column")));
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitPickerId).setCaption(Objects.requireNonNull(env.getProperty("employee.id.column")));
        fruitDeliveryGrid.addColumn(FruitDelivery::getType).setCaption(Objects.requireNonNull(env.getProperty("fruit.delivery.type.name.column")));
        fruitDeliveryGrid.addColumn(FruitDelivery::getFruitVarietyName).setCaption(env.getProperty("fruit.variety.name.column", "variety"));
        fruitDeliveryGrid.addColumn(FruitDelivery::getComment).setCaption(Objects.requireNonNull(env.getProperty("comment")));
        fruitDeliveryGrid.addColumn(FruitDelivery::getDeliveryTimeFormatted).setCaption(Objects.requireNonNull(env.getProperty("time.column")));
        fruitDeliveryGrid.addColumn((t) -> {
            if (t.isSecondClassFruit()) {
                return env.getProperty("yes");
            } else {
                return env.getProperty("no");
            }
        }).setCaption(Objects.requireNonNull(env.getProperty("is.second.class")));
        fruitDeliveryGrid.addColumn(FruitDelivery::getId).setCaption(Objects.requireNonNull(env.getProperty("id.column")));
        fruitDeliveryGrid.setSizeFull();
        this.addComponent(refreshButton);
        this.addComponent(fruitDeliveryGrid);
        refreshGrid();
    }

    private void refreshGrid() {
        Thread refreshThread = new RefreshThread();
        refreshThread.start();
    }

    private class RefreshThread extends Thread{
        @Override
        public void run(){
            fruitDeliveryGrid.setItems(fruitDeliveryService.findAll());
            fruitDeliveryGrid.setHeight(700, Unit.PIXELS);
        }
    }
}
