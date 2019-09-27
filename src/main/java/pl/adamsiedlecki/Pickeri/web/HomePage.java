package pl.adamsiedlecki.Pickeri.web;

import com.vaadin.server.ExternalResource;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@SpringUI(path = "/home")
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
    private Label totalWeightLabel;
    private Label todayWeightLabel;

    public HomePage(FruitDeliveryService fruitDeliveryService) {
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
        todayWeightLabel = new Label();
        totalWeightLabel = new Label();
        qrUpload.setAcceptMimeTypes("image/jpg");
        qrUpload.setButtonCaption("Naciśnij aby wybrać obraz");
        qrUpload.setReceiver(new ImageUploader());

        qrUpload.addSucceededListener(e -> {
            String value = QRCodeReader.decodeQRCode(new File(path));
            if (value != null) {
                List<String> items = Arrays.asList(value.split("\\s*,\\s*"));
                System.out.println(items.size() + value);
                if (items.size() == 2) {
                    fruitPickerId.setValue(items.get(0));
                    nameLabel.setValue(items.get(1));

                } else {
                    Notification.show("Obraz nie zawiera poprawnego kodu QR!");
                    fruitPickerId.setValue("");
                    nameLabel.setValue("");
                    File f = new File(path);
                    f.delete();
                }
            } else {
                Notification.show("Obraz nie zawiera kodu QR - może zrób wyraźniejsze zdjęcie?");
                fruitPickerId.setValue("");
                nameLabel.setValue("");
                File f = new File(path);
                f.delete();
            }
        });

        root.addComponent(ResourceGetter.getPickeriLogoAsEmbeddedComponent());
        root.addComponent(new Label("Welcome to Pickeri!"));
        root.addComponent(new Link("LOGIN PAGE", new ExternalResource("/login")));
        root.addComponent(new Label("   "));
        root.addComponent(new Label("Podaj swoje ID lub zeskanuj kod QR aby sprawdzić stan:"));
        root.addComponent(new HorizontalLayout(fruitPickerId, qrUpload, findInfoButton));
        root.addComponent(todayAmountOfPackagesLabel);
        root.addComponent(totalAmountOfPackagesLabel);
        root.addComponent(todayWeightLabel);
        root.addComponent(totalWeightLabel);

        findInfoButton.addClickListener(e -> {
            if (NumberUtils.isCreatable(fruitPickerId.getValue())) {
                List<FruitDelivery> deliveryList = fruitDeliveryService.getDeliveriesByPickerId(Long.parseLong(fruitPickerId.getValue()));
                int totalPackages = 0;
                int todayPackages = 0;
                BigDecimal totalWeight = new BigDecimal(0);
                BigDecimal todayWeight = new BigDecimal(0);

                for (FruitDelivery fd : deliveryList) {
                    totalPackages += fd.getPackageAmount();
                    totalWeight = totalWeight.add(fd.getFruitWeight());
                    if (fd.getDeliveryTime().getDayOfYear() == LocalDateTime.now().getDayOfYear()) {
                        todayPackages += fd.getPackageAmount();
                        todayWeight = todayWeight.add(fd.getFruitWeight());
                    }
                }
                todayAmountOfPackagesLabel.setValue("Ilość opakowań dzisiaj: " + todayPackages);
                totalAmountOfPackagesLabel.setValue("Suma wszystkich opakowań: " + totalPackages);
                todayWeightLabel.setValue("Waga owoców dzisiaj [kg] : " + todayWeight.divide(BigDecimal.valueOf(1000), 2, RoundingMode.FLOOR));
                totalWeightLabel.setValue("Waga wszystkich owoców [kg] : " + totalWeight.divide(BigDecimal.valueOf(1000), 2, RoundingMode.FLOOR));
                Grid<FruitDelivery> grid = new Grid<>();
                grid.addColumn(FruitDelivery::getFruitWeightKgPlainString).setCaption("WAGA");
                grid.addColumn(FruitDelivery::getPackageAmount).setCaption("OPAKOWANIA");
                grid.addColumn(FruitDelivery::getFruitVarietyName).setCaption("ODMIANA");
                grid.addColumn(FruitDelivery::getType).setCaption("TYP");
                grid.addColumn(FruitDelivery::getDeliveryTimeFormatted).setCaption("CZAS");
                grid.setItems(fruitDeliveryService.getDeliveriesByPickerId(Long.parseLong(fruitPickerId.getValue())));
                grid.setWidth(80, Unit.PERCENTAGE);
                grid.setHeight(700, Unit.PIXELS);
                root.addComponent(grid);
                root.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
            }
        });
        this.setContent(root);
    }

    private class ImageUploader implements Upload.Receiver, Upload.SucceededListener {
        private File file;

        public OutputStream receiveUpload(String filename,
                                          String mimeType) {
            System.out.println("UPLOAD RECEIVED");
            // Create upload stream
            FileOutputStream fos; // Stream to write to
            try {
                // Open the file for writing.
                file = new File(filename);
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
