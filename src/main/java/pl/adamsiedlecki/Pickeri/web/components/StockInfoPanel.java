package pl.adamsiedlecki.Pickeri.web.components;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import elemental.json.JsonArray;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.tools.ApiReader.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;


@Component
@Scope("prototype")
public class StockInfoPanel extends VerticalLayout {

    private Environment env;
    private Logger log = LoggerFactory.getLogger(StockInfoPanel.class);
    private String price = "";

    @Autowired
    public StockInfoPanel(Environment env){
        this.env = env;
        setWidth(100, Unit.PERCENTAGE);
        setHeight(50, Unit.PIXELS);
        getInfo();
        this.addComponent(new Label("PRICE BTC/PLN: "+price));
        this.forEach(component -> {this.setComponentAlignment(component, Alignment.MIDDLE_CENTER);});
    }

    public void getInfo(){
        String url = env.getProperty("bitcoin.api");
        try {
            JSONObject json = JsonReader.readJsonFromUrl(url);
            //price = json.get("price").toString();
            log.info(json.get("price").toString());
            log.info(json.names().getString(1));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



}
