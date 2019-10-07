package pl.adamsiedlecki.Pickeri.web.tabs.othersUITabs;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
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
    private Environment env;

    @Autowired
    public AddPickerTab(FruitPickerService fruitPickerService, Environment environment) {
        this.env = environment;
        this.fruitPickerService = fruitPickerService;
        initFields();
        this.addComponent(formLayout);
    }

    private void initFields() {
        formLayout = new FormLayout();
        name = new TextField(env.getProperty("name.person.column"));
        lastName = new TextField(env.getProperty("surname.column"));
        radioButtonGroup = new RadioButtonGroup<>();
        radioButtonGroup.setItems(env.getProperty("female"), env.getProperty("male"));
        Button saveButton = new Button(env.getProperty("save.button"));

        saveButton.addClickListener(x -> {
            if (name.isEmpty() || lastName.isEmpty() || radioButtonGroup.isEmpty()) {
                Notification.show(env.getProperty("input.employee.notification"));
            } else {
                FruitPicker fruitPicker = new FruitPicker(name.getValue(), lastName.getValue(), radioButtonGroup.getValue());
                fruitPickerService.addFruitPicker(fruitPicker);
                name.setValue("");
                lastName.setValue("");
                radioButtonGroup.setValue(null);
            }

        });

        formLayout.addComponents(name, lastName, radioButtonGroup, saveButton);
    }

}
