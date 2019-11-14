package pl.adamsiedlecki.Pickeri.web.ui;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Title;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.ResourceGetter;
import pl.adamsiedlecki.Pickeri.tools.userInterfaceTools.ThemeSetter;
import pl.adamsiedlecki.Pickeri.tools.qr.QRCodeReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@SpringUI(path = "/home")
@StyleSheet({"https://fonts.googleapis.com/css?family=Ubuntu&display=swap"})
@Title("${home.title}")
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
    private Environment environment;

    @Autowired
    public HomePage(FruitDeliveryService fruitDeliveryService, Environment environment) {
        this.fruitDeliveryService = fruitDeliveryService;
        this.environment = environment;
    }

    @Override
    protected void init(VaadinRequest request) {
        root = new VerticalLayout();
        fruitPickerId = new TextField();
        fruitPickerId.setCaption(environment.getProperty("id.column.caption"));
        qrUpload = new Upload();
        qrUpload.setCaption(environment.getProperty("qr.upload.caption"));
        nameLabel = new Label();
        findInfoButton = new Button(environment.getProperty("search.button.caption"));
        totalAmountOfPackagesLabel = new Label();
        todayAmountOfPackagesLabel = new Label();
        todayWeightLabel = new Label();
        totalWeightLabel = new Label();
        qrUpload.setAcceptMimeTypes("image/jpg");
        qrUpload.setButtonCaption(environment.getProperty("browse.images.button"));
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
                    Notification.show(environment.getProperty("not.valid.qr"));
                    fruitPickerId.setValue("");
                    nameLabel.setValue("");
                    File f = new File(path);
                    f.delete();
                }
            } else {
                Notification.show(environment.getProperty("not.valid.qr.notification"));
                fruitPickerId.setValue("");
                nameLabel.setValue("");
                File f = new File(path);
                f.delete();
            }
        });

        root.addComponent(ResourceGetter.getPickeriLogoAsEmbeddedComponent());
        root.addComponent(new Label(environment.getProperty("welcome.message")));
        root.addComponent(new Link(environment.getProperty("login.page.link"), new ExternalResource("/login")));
        root.addComponent(new Label("   "));
        root.addComponent(new Label(environment.getProperty("input.label.id.or.qr")));
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
                todayAmountOfPackagesLabel.setValue(environment.getProperty("today.amount.of.packages.label") + todayPackages);
                totalAmountOfPackagesLabel.setValue(environment.getProperty("all.packages.sum.label") + totalPackages);
                todayWeightLabel.setValue(environment.getProperty("today.weight.label") + todayWeight.divide(BigDecimal.valueOf(1000), 2, RoundingMode.FLOOR));
                totalWeightLabel.setValue(environment.getProperty("total.weight.label") + totalWeight.divide(BigDecimal.valueOf(1000), 2, RoundingMode.FLOOR));
                Grid<FruitDelivery> grid = new Grid<>();
                grid.addColumn(FruitDelivery::getFruitWeightKgPlainString).setCaption(Objects.requireNonNull(environment.getProperty("weight.column.caption")));
                grid.addColumn(FruitDelivery::getPackageAmount).setCaption(Objects.requireNonNull(environment.getProperty("packages.column.caption")));
                grid.addColumn(FruitDelivery::getFruitVarietyName).setCaption(Objects.requireNonNull(environment.getProperty("fruit.variety.name.column")));
                grid.addColumn(FruitDelivery::getType).setCaption(Objects.requireNonNull(environment.getProperty("fruit.delivery.type.name.column")));
                grid.addColumn(FruitDelivery::getDeliveryTimeFormatted).setCaption(Objects.requireNonNull(environment.getProperty("time.column")));
                grid.setItems(fruitDeliveryService.getDeliveriesByPickerId(Long.parseLong(fruitPickerId.getValue())));
                grid.setWidth(80, Unit.PERCENTAGE);
                grid.setHeight(700, Unit.PIXELS);
                root.addComponent(grid);
                root.setComponentAlignment(grid, Alignment.MIDDLE_CENTER);
            }
        });
        this.setContent(root);
        ThemeSetter.set(this);
    }

    private class ImageUploader implements Upload.Receiver, Upload.SucceededListener {
        private File file;

        public OutputStream receiveUpload(String filename, String mimeType) {
            // Create upload stream
            FileOutputStream fos; // Stream to write to
            try {
                // Open the file for writing.
                file = new File(filename);
                path = file.getAbsolutePath();
                fos = new FileOutputStream(file);
            } catch (final java.io.FileNotFoundException e) {
                new Notification(environment.getProperty("could.not.open.file.notification"),
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
