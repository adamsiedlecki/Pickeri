package pl.adamsiedlecki.Pickeri.web.tabs.paymentTabs;

import com.vaadin.ui.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;

import java.math.BigDecimal;
import java.util.Optional;

@Component
@Scope("prototype")
public class AddPaymentTab extends VerticalLayout {

    private FruitPickerService fruitPickerService;

    @Autowired
    public AddPaymentTab(FruitPickerService fruitPickerService) {
        TextField pickerIdField = new TextField("ID pracownika");
        TextField paymentAmount = new TextField("Dokonana wypłata [zł] (grosze oddzielamy kropką)");
        Button saveButton = new Button("ZAPISZ");
        HorizontalLayout fieldsLayout = new HorizontalLayout(pickerIdField, paymentAmount, saveButton);

        this.addComponent(fieldsLayout);

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
                Notification.show("Niepoprawne dane",
                        "Coś poszło nie tak. Możliwe, że podane wartości nie są liczbowe.", Notification.Type.ERROR_MESSAGE);
            }
        });

    }

}
