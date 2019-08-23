package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

@SpringComponent
public class StatisticsTab extends VerticalLayout {

    private FruitDeliveryService fruitDeliveryService;
    private FruitPickerService fruitPickerService;
    private VerticalLayout root;
    private Label pickersSum;
    private Label packagesSum;

    @Autowired
    public StatisticsTab(FruitDeliveryService fruitDeliveryService, FruitPickerService fruitPickerService){
        this.fruitDeliveryService = fruitDeliveryService;
        this.fruitPickerService = fruitPickerService;
        initComponents();
    }

    private void initComponents(){
        root = new VerticalLayout();
        pickersSum = new Label("Ilość pracowników w systemie: "+fruitPickerService.getTotalAmountOfPickers());
        packagesSum = new Label("Suma szystkich opakowań: "+fruitDeliveryService.getTotalAmountOfPackages());

        root.addComponent(pickersSum);
        root.addComponent(packagesSum);

        //root.setComponentAlignment(pickersSum, Alignment.MIDDLE_CENTER);


        this.addComponent(root);
    }

}
