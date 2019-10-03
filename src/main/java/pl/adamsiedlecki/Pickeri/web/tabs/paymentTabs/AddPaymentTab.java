package pl.adamsiedlecki.Pickeri.web.tabs.paymentTabs;

import com.vaadin.ui.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@Scope("prototype")
public class AddPaymentTab extends VerticalLayout {

    private FruitPickerService fruitPickerService;
    private Environment env;

    @Autowired
    public AddPaymentTab(FruitPickerService fruitPickerService, Environment environment) {
        this.env = environment;
        TextField pickerIdField = new TextField(env.getProperty("employee.id"));
        TextField paymentAmount = new TextField(env.getProperty("done.payments"));
        Button saveButton = new Button(env.getProperty("save.button"));
        HorizontalLayout fieldsLayout = new HorizontalLayout(pickerIdField, paymentAmount);

        this.addComponent(fieldsLayout);
        this.addComponent(saveButton);

        saveButton.addClickListener(e -> {
            if (NumberUtils.isDigits(pickerIdField.getValue()) && NumberUtils.isCreatable(paymentAmount.getValue())) {
                Optional<FruitPicker> picker = fruitPickerService.getFruitPickerById(Long.parseLong(pickerIdField.getValue()));
                picker.ifPresent(p -> {
                    BigDecimal old = p.getFundsPaid();
                    if (old == null) {
                        p.setFundsPaid(new BigDecimal(0));
                        old = p.getFundsPaid();
                    }
                    p.setFundsPaid(old.add(new BigDecimal(paymentAmount.getValue())));
                    fruitPickerService.addFruitPicker(p);
                    pickerIdField.clear();
                    paymentAmount.clear();
                });
            } else {
                Notification.show(env.getProperty("incorrect.values.notification"),
                        env.getProperty("not.numeric.values.notification"), Notification.Type.ERROR_MESSAGE);
            }
        });

    }

}
