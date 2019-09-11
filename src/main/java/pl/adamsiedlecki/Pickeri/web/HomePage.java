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
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.tools.QRCodeReader;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.web.tabs.AddDeliveryTab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@SpringUI(path="/home")
public class HomePage extends UI {

    private VerticalLayout root;
    private TextField fruitPickerId;
    private Upload qrUpload;
    private Label nameLabel;
    private String path;
    private Button findInfoButton;
    private FruitDeliveryService fruitDeliveryService;
    private Label totalAmountOfPackagesLabel;
    private Label todayAmountOfPackagesLabel;

    public HomePage(FruitDeliveryService fruitDeliveryService){
        this.fruitDeliveryService = fruitDeliveryService;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        fruitPickerId = new TextField();
        fruitPickerId.setCaption("ID");
        qrUpload = new Upload();
        qrUpload.setCaption("Załaduj kod QR");
        nameLabel = new Label();
        findInfoButton = new Button("SZUKAJ");
        totalAmountOfPackagesLabel = new Label();
        todayAmountOfPackagesLabel = new Label();
        qrUpload.setAcceptMimeTypes("image/jpg");
        qrUpload.setButtonCaption("Naciśnij aby wybrać obraz");
        qrUpload.setReceiver(new ImageUploader());

        qrUpload.addSucceededListener(e->{
            String value = QRCodeReader.decodeQRCode(new File(path));
            if(value!=null){
                List<String> items = Arrays.asList(value.split("\\s*,\\s*"));
                System.out.println(items.size()+ value);
                if(items.size()==2){
                    fruitPickerId.setValue(items.get(0));
                    nameLabel.setValue(items.get(1));

                }else{
                    Notification.show("Obraz nie zawiera poprawnego kodu QR!");
                    fruitPickerId.setValue("");
                    nameLabel.setValue("");
                    File f = new File(path);
                    f.delete();
                }
            }else{
                Notification.show("Obraz nie zawiera kodu QR - może zrób wyraźniejsze zdjęcie?");
                fruitPickerId.setValue("");
                nameLabel.setValue("");
                File f = new File(path);
                f.delete();
            }
        });

        root.addComponent(new Embedded("", new FileResource(ResourceGetter.getPickeriLogo())));
        root.addComponent(new Label("Welcome to Pickeri!"));
        root.addComponent(new Link("LOGIN PAGE",new ExternalResource("/login")));
        root.addComponent(new Label("   "));
        root.addComponent(new Label("Podaj swóje ID lub zeskanuj kod QR aby sprawdzić stan:"));
        root.addComponent(new HorizontalLayout(fruitPickerId,qrUpload,findInfoButton));
        root.addComponent(todayAmountOfPackagesLabel);
        root.addComponent(totalAmountOfPackagesLabel);

        findInfoButton.addClickListener(e->{
            if(NumberUtils.isCreatable(fruitPickerId.getValue())){
                List<FruitDelivery> deliveryList = fruitDeliveryService.getDeliveriesByPickerId(Long.parseLong(fruitPickerId.getValue()));
                int total = 0;
                int today = 0;

                for(FruitDelivery fd : deliveryList){
                    total+=fd.getPackageAmount();
                    if(fd.getDeliveryTime().getDayOfYear()== LocalDateTime.now().getDayOfYear()){
                        today+=fd.getPackageAmount();
                    }
                }
                todayAmountOfPackagesLabel.setValue("Ilość opakowań dzisiaj: "+today);
                totalAmountOfPackagesLabel.setValue("Suma wszystkich opakowań: "+total);
            }
        });
        this.setContent(root);
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
