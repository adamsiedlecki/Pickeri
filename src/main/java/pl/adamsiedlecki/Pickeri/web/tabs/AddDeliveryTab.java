package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.addon.geolocation.Coordinates;
import com.vaadin.addon.geolocation.Geolocation;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.entity.FruitType;
import pl.adamsiedlecki.Pickeri.entity.FruitVariety;
import pl.adamsiedlecki.Pickeri.entity.GeoLocalization;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitTypeService;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;
import pl.adamsiedlecki.Pickeri.tools.QRCodeReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@SpringComponent
@Scope("prototype")
public class AddDeliveryTab extends VerticalLayout {

    private VerticalLayout root;
    private TextField fruitPickerId;
    private TextField packageAmount;
    private RadioButtonGroup<String> fruitType;
    private RadioButtonGroup<String> fruitVariety;
    private TextField comment;
    private Button save;
    private FruitDeliveryService fruitDeliveryService;
    private FruitVarietyService fruitVarietyService;
    private Upload qrUpload;
    private String path;
    private FruitTypeService fruitTypeService;
    private TextField weightField;

    @Autowired
    public AddDeliveryTab(FruitDeliveryService fruitDeliveryService, FruitVarietyService fruitVarietyService,
                          FruitTypeService fruitTypeService){
        this.fruitVarietyService = fruitVarietyService;
        this.fruitDeliveryService = fruitDeliveryService;
        this.fruitTypeService = fruitTypeService;

        initComponents();

        qrUpload.addSucceededListener(e->{
            String value = QRCodeReader.decodeQRCode(new File(path));
            if(value!=null){
                List<String> items = Arrays.asList(value.split("\\s*,\\s*"));
                System.out.println(items.size()+ value);
                if(items.size()==2){
                    fruitPickerId.setValue(items.get(0));
                    comment.setValue(items.get(1));

                }else{
                    Notification.show("Obraz nie zawiera poprawnego kodu QR!");
                    fruitPickerId.setValue("");
                    comment.setValue("");
                    File f = new File(path);
                    f.delete();
                }
            }else{
                Notification.show("Obraz nie zawiera kodu QR - może zrób wyraźniejsze zdjęcie?");
                fruitPickerId.setValue("");
                comment.setValue("");
                File f = new File(path);
                f.delete();
            }
        });

        save.addClickListener(e-> saveAction());
    }

    private void initComponents(){
        FormLayout formLayout = new FormLayout();
        HorizontalLayout pickerInfoLayout = new HorizontalLayout();
        root = new VerticalLayout();

        Button refreshVarietiesButton = new Button("Odśwież formularz");
        refreshVarietiesButton.addClickListener(e->
           refreshVarieties()
        );

        qrUpload = new Upload();
        qrUpload.setCaption("Załaduj kod QR ");
        qrUpload.setAcceptMimeTypes("image/jpg");
        qrUpload.setButtonCaption("Naciśnij aby wybrać obraz");
        qrUpload.setReceiver(new ImageUploader());
        qrUpload.setImmediateMode(true);

        fruitPickerId = new TextField("ID pracownika"); // "ID pracownika"
        packageAmount = new TextField("Ilość opakowań"); // "Ilość opakowań"
        packageAmount.setValue("0");
        weightField = new TextField("Waga w gramach");
        weightField.setValue("0");
        HorizontalLayout amountAndWeight = new HorizontalLayout(packageAmount,weightField);
        fruitType = new RadioButtonGroup<>();
        fruitVariety = new RadioButtonGroup<>();
        comment = new TextField("Komentarz");
        save = new Button("Zapisz");

        refreshTypes();
        fruitType.setCaption("Typ owocu");

        refreshVarieties();
        fruitVariety.setCaption("Odmiana owocu");

        pickerInfoLayout.addComponents(fruitPickerId,qrUpload);
        formLayout.addComponents(pickerInfoLayout,amountAndWeight,fruitType,fruitVariety,comment,save);

        root.addComponent(refreshVarietiesButton);
        root.addComponent(formLayout);
        this.addComponent(root);
    }

    private void refreshVarieties(){
        List<String> fruitVarietyNames = fruitVarietyService.findAll().stream().map(FruitVariety::getName).collect(Collectors.toList());
        fruitVariety.setItems(fruitVarietyNames);
    }

    private void refreshTypes(){
        List<String> types = fruitTypeService.findAll().stream().map(FruitType::getName).collect(Collectors.toList());
        fruitType.setItems(types);
    }

    private void saveAction(){
        if(NumberUtils.isCreatable(fruitPickerId.getValue())&&NumberUtils.isCreatable(packageAmount.getValue())
                &&NumberUtils.isCreatable(weightField.getValue())){
            if(fruitPickerId.isEmpty()||packageAmount.isEmpty()||fruitType.isEmpty()||fruitVariety.isEmpty()
                    ||weightField.isEmpty()){
                Notification.show("Uzupełnij wymagane pola!");
            }else{
                FruitDelivery fruitDelivery = new FruitDelivery(Long.parseLong(fruitPickerId.getValue()),
                        fruitType.getValue(),Long.parseLong(packageAmount.getValue()),comment.getValue(),
                        fruitVariety.getValue(), LocalDateTime.now());
                fruitDelivery.setFruitWeight(new BigDecimal(weightField.getValue()));
                Geolocation geo = new Geolocation(this.getUI());
                geo.getCurrentPosition(position ->{
                    Coordinates coordinates = position.getCoordinates();
                    fruitDelivery.setGeoLocalization(new GeoLocalization(coordinates.getLatitude(),
                            coordinates.getLongitude()));
                    });
                fruitDeliveryService.addDelivery(fruitDelivery);
                cleanFields();
            }
        }else{
            Notification.show("Id pracownika oraz ilość opakowań powinny być liczbami całkowitymi!");
        }

    }

    private void cleanFields(){
        fruitPickerId.clear();
        packageAmount.clear();
        fruitType.clear();
        fruitVariety.clear();
        comment.clear();
        weightField.clear();
    }

    private class ImageUploader implements Upload.Receiver, Upload.SucceededListener  {
        private File file;


        public OutputStream receiveUpload(String filename,
                                          String mimeType) {
            System.out.println("UPLOAD RECEIVED");
            // Create upload stream
            FileOutputStream fos; // Stream to write to
            try {
                // Open the file for writing.
                file = new File( filename);
                path = file.getAbsolutePath();
                fos = new FileOutputStream(file);
            } catch (final java.io.FileNotFoundException e) {
                new Notification("Could not open file",
                        e.getMessage(),
                        Notification.Type.ERROR_MESSAGE)
                        .show(Page.getCurrent());
                return null;
            }

            return fos; // Return the output stream to write to
        }

        public void uploadSucceeded(Upload.SucceededEvent event) {

        }


    }

}
