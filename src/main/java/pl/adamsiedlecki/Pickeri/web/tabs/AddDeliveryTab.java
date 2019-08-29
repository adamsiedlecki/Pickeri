package pl.adamsiedlecki.Pickeri.web.tabs;



import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class AddDeliveryTab extends VerticalLayout {

    private FormLayout formLayout;
    private TextField fruitPickerId;
    private TextField packageAmount;
    private RadioButtonGroup<String> fruitType;
    private RadioButtonGroup<String> fruitVariety;
    private TextField comment;
    private Button save;
    private Button refreshVarietiesButton;

    private FruitDeliveryService fruitDeliveryService;
    private FruitVarietyService fruitVarietyService;



    @Autowired
    public AddDeliveryTab(FruitDeliveryService fruitDeliveryService, FruitVarietyService fruitVarietyService){
        this.fruitVarietyService = fruitVarietyService;
        this.fruitDeliveryService = fruitDeliveryService;

        initComponents();
        save.addClickListener(e->{
            saveAction();
            fruitPickerId.clear();
            packageAmount.clear();
            fruitType.clear();
            fruitVariety.clear();
            comment.clear();
        });
    }

    private void initComponents(){
        formLayout = new FormLayout();

        refreshVarietiesButton = new Button("Odśwież formularz");
        refreshVarietiesButton.addClickListener(e->{
           refreshVarieties();
        });

        fruitPickerId = new TextField("ID pracownika"); // "ID pracownika"
        packageAmount = new TextField("Ilość opakowań"); // "Ilość opakowań"
        fruitType = new RadioButtonGroup<>();
        fruitVariety = new RadioButtonGroup<>();
        comment = new TextField("Komentarz");
        save = new Button("Zapisz");

        fruitType.setItems("truskawka z szypułką"," truskawka bez szypułki");
        //fruitType.setCaption("Typ owocu");

        refreshVarieties();
        //fruitVariety.setCaption("Odmiana owocu");

        formLayout.add(fruitPickerId,packageAmount,fruitType,fruitVariety,comment,save);

        this.add(refreshVarietiesButton);
        //this.addComponent(qrReader);
        this.add(formLayout);
    }

    private void refreshVarieties(){
        List<String> fruitVarietyNames = fruitVarietyService.findAll().stream().map(FruitVariety::getName).collect(Collectors.toList());
        fruitVariety.setItems(fruitVarietyNames);
    }

    private void saveAction(){
        if(NumberUtils.isCreatable(fruitPickerId.getValue())&&NumberUtils.isCreatable(packageAmount.getValue())){
            if(fruitPickerId.isEmpty()||packageAmount.isEmpty()||fruitType.isEmpty()||fruitVariety.isEmpty()){
                Notification.show("Uzupełnij wymagane pola!");
            }else{
                FruitDelivery fruitDelivery = new FruitDelivery(Long.parseLong(fruitPickerId.getValue()),fruitType.getValue(),Long.parseLong(packageAmount.getValue()),comment.getValue(),fruitVariety.getValue(), LocalDateTime.now());
                fruitDeliveryService.addDelivery(fruitDelivery);
            }
        }else{
            Notification.show("Id pracownika oraz ilość opakowań powinny być liczbami całkowitymi!");
        }

    }

}
