package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

@Component
@Scope("prototype")
public class AddPickerTab extends VerticalLayout {

    private FormLayout formLayout;
    private TextField name;
    private TextField lastName;
    private RadioButtonGroup<String> radioButtonGroup;
    private Button saveButton;

    private FruitPickerService fruitPickerService;

    @Autowired
    public AddPickerTab(FruitPickerService fruitPickerService){
        this.fruitPickerService = fruitPickerService;
        initFields();

        this.add(formLayout);
    }

    private void initFields(){
        formLayout = new FormLayout();
        name = new TextField("Imię");
        lastName = new TextField("Nazwisko");
        radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setItems("Kobieta","Mężczyzna");
        saveButton = new Button("ZAPISZ");

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

        formLayout.add(name,lastName,radioButtonGroup,saveButton);
    }

}
