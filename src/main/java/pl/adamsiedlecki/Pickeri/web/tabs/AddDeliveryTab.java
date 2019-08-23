package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;


import org.apache.commons.lang3.math.NumberUtils;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;

import java.time.LocalDateTime;

@SpringComponent
public class AddDeliveryTab extends VerticalLayout {

    private FormLayout formLayout;
    private TextField fruitPickerId;
    private TextField packageAmount;
    private RadioButtonGroup<String> fruitType;
    private RadioButtonGroup<String> fruitVariety;
    private TextField comment;
    private Button save;

    private FruitDeliveryService fruitDeliveryService;

    @Autowired
    public AddDeliveryTab(FruitDeliveryService fruitDeliveryService){
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

        fruitPickerId = new TextField("ID pracownika"); // "ID pracownika"
        packageAmount = new TextField("Ilość opakowań"); // "Ilość opakowań"
        fruitType = new RadioButtonGroup<>();
        fruitVariety = new RadioButtonGroup<>();
        comment = new TextField("Komentarz");
        save = new Button("Zapisz");

        fruitType.setItems("truskawka z szypułką"," truskawka bez szypułki");
        fruitType.setCaption("Typ owocu");

        fruitVariety.setItems("ALBA","APRICA","GRANDAROSA","JIVE","ALLEGRO","SONATA");
        fruitVariety.setCaption("Odmiana owocu");

        formLayout.addComponents(fruitPickerId,packageAmount,fruitType,fruitVariety,comment,save);

        this.addComponent(formLayout);
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
