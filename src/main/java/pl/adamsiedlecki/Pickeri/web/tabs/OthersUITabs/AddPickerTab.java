package pl.adamsiedlecki.Pickeri.web.tabs.OthersUITabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

@SpringComponent
@Scope("prototype")
public class AddPickerTab extends VerticalLayout {

    private FormLayout formLayout;
    private TextField name;
    private TextField lastName;
    private RadioButtonGroup<String> radioButtonGroup;
    private FruitPickerService fruitPickerService;

    @Autowired
    public AddPickerTab(FruitPickerService fruitPickerService){
        this.fruitPickerService = fruitPickerService;
        initFields();
        this.addComponent(formLayout);
    }

    private void initFields(){
        formLayout = new FormLayout();
        name = new TextField("Imię");
        lastName = new TextField("Nazwisko");
        radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setItems("Kobieta","Mężczyzna");
        Button saveButton = new Button("ZAPISZ");

        saveButton.addClickListener(x->{
            if(name.isEmpty()||lastName.isEmpty()||radioButtonGroup.isEmpty()){
                Notification.show("Wprowadź imię, nazwisko i płeć!");
            }else{
                FruitPicker fruitPicker = new FruitPicker(name.getValue(),lastName.getValue(),radioButtonGroup.getValue());
                fruitPickerService.addFruitPicker(fruitPicker);
                name.setValue("");
                lastName.setValue("");
                radioButtonGroup.setValue(null);
            }

        });

        formLayout.addComponents(name,lastName,radioButtonGroup, saveButton);
    }

}