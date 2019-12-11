package pl.adamsiedlecki.Pickeri.web.tab.employeesTab;

import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.interfaces.Removeable;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

import java.util.List;
import java.util.stream.Collectors;

@SpringComponent
@Scope("prototype")
public class DeletePickerTab extends VerticalLayout {

    private VerticalLayout root = new VerticalLayout();
    private FruitPickerService fruitPickerService;
    private Environment env;

    @Autowired
    public DeletePickerTab(FruitPickerService fruitPickerService, Environment environment) {
        this.env = environment;
        this.fruitPickerService = fruitPickerService;
        this.addComponent(root);
        addPanel(env.getProperty("id.caption"),env.getProperty("delete.employee"), fruitPickerService);
        this.forEach(component -> this.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
    }

    private void addPanel(String idFieldName, String buttonName, Removeable service) {
        ComboBox<String> employeesComboBox = new ComboBox<>();
        List<FruitPicker> list = service.findAll();
        employeesComboBox.setItems(list.stream().map(FruitPicker::getIdNameLastName).collect(Collectors.toList()));
        Button button = new Button();
        button.setStyleName(ValoTheme.BUTTON_DANGER);
        button.setCaption(buttonName);
        button.addClickListener(x -> {
            String value = employeesComboBox.getValue();
            String id = value.split(" ")[0];
            if (id==null || !id.isEmpty() || NumberUtils.isDigits(id) ) {
                Long idLong = Long.parseLong(value);
                service.removeById(idLong);
                Notification.show(env.getProperty("operation.is.done.notification"));
            }else{
                Notification.show(env.getProperty("incorrect.values.notification"));
            }
        });
        HorizontalLayout panel = new HorizontalLayout(employeesComboBox, button);
        root.addComponent(panel);
    }

}
