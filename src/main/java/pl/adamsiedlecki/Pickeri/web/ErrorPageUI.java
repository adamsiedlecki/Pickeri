package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.apache.commons.lang3.math.NumberUtils;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.tools.QRCodeReader;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;


@SpringUI(path="/errorPage")
public class ErrorPageUI extends UI {

    private VerticalLayout root;

    public ErrorPageUI(){
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        root.addComponent(new Label(request.getPathInfo()));
        root.addComponent(new Label("Nie znaleziono elementu."));
        Embedded picture = new Embedded("Wystąpił błąd. Jesteś w polu.", new FileResource(ResourceGetter.getTractorPicture()));
        picture.setWidth(50, Unit.PERCENTAGE);
        picture.setHeight(50, Unit.PERCENTAGE);
        root.addComponent(picture);
        root.addComponent(new Embedded("", new FileResource(ResourceGetter.getPickeriLogo())));
        this.setContent(root);
    }

    private String getInfo(VaadinRequest request){
        Map<String, String[]> map = request.getParameterMap();
        String result = " ";
        Set<String> keys = map.keySet();
        for (String key : keys){
            result = result.concat(key+" : "+map.get(key)+" \n ");
        }
        return result;
    }
}
