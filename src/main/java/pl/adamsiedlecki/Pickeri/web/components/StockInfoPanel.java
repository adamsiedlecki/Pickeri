package pl.adamsiedlecki.Pickeri.web.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.tools.ApiReader.InfoGetter;


@Component
@Scope("prototype")
public class StockInfoPanel extends VerticalLayout {

    private Environment env;
    private Logger log = LoggerFactory.getLogger(StockInfoPanel.class);
    private Label btcPriceLabel;
    private Label dollarPriceLabel;


    @Autowired
    public StockInfoPanel(Environment env){
        this.env = env;
        setWidth(100, Unit.PERCENTAGE);
        setHeight(50, Unit.PIXELS);
        btcPriceLabel = new Label();
        dollarPriceLabel = new Label();
        this.addComponents(btcPriceLabel, new Label(), new Label(), dollarPriceLabel);
        refreshPrice();
        this.forEach(component -> {this.setComponentAlignment(component, Alignment.MIDDLE_CENTER);});
        this.addLayoutClickListener(event -> refreshPrice());
    }

    private void refreshPrice(){
        btcPriceLabel.setValue(InfoGetter.getBitcoinPrice(env) +" PLN");
        btcPriceLabel.setCaption(env.getProperty("bitcoin.price"));
        dollarPriceLabel.setValue(InfoGetter.getDollarPrice(env)+" PLN");
        dollarPriceLabel.setCaption(env.getProperty("dollar.price"));
    }
}
