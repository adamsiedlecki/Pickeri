package pl.adamsiedlecki.Pickeri.web.tab.independentTabs;

import com.vaadin.addon.geolocation.Coordinates;
import com.vaadin.addon.geolocation.Geolocation;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.ValueChangeMode;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.FruitDelivery;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.entity.GeoLocalization;
import pl.adamsiedlecki.Pickeri.service.FruitDeliveryService;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitTypeService;
import pl.adamsiedlecki.Pickeri.service.FruitVarietyService;
import pl.adamsiedlecki.Pickeri.tools.qr.QRCodeReader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;


@SpringComponent
@Scope("prototype")
public class AddDeliveryTab extends VerticalLayout {

    private VerticalLayout root;
    private TextField fruitPickerIdField;
    private TextField packageAmount;
    private RadioButtonGroup<String> fruitTypeRadioButton;
    private RadioButtonGroup<String> fruitVarietyRadioButton;
    private final FruitDeliveryService fruitDeliveryService;
    private TextField commentField;
    private Button save;
    private final FruitVarietyService fruitVarietyService;
    private final FruitTypeService fruitTypeService;
    private Upload qrUpload;
    private String path;
    private final Environment env;
    private TextField weightField;
    private final FruitPickerService fruitPickerService;
    private ComboBox<String> pickersComboBox;
    private final Label weightInKgLabel = new Label();
    private RadioButtonGroup<String> fruitClassRadioButton;

    @Autowired
    public AddDeliveryTab(FruitDeliveryService fruitDeliveryService, FruitVarietyService fruitVarietyService,
                          FruitTypeService fruitTypeService, Environment environment, FruitPickerService fruitPickerService) {
        this.fruitPickerService = fruitPickerService;
        this.fruitVarietyService = fruitVarietyService;
        this.fruitDeliveryService = fruitDeliveryService;
        this.fruitTypeService = fruitTypeService;
        this.env = environment;

        initComponents();

        qrUpload.addSucceededListener(e -> {
            String value = QRCodeReader.decodeQRCode(new File(path));
            if (value != null) {
                List<String> items = Arrays.asList(value.split("\\s*,\\s*"));
                System.out.println(items.size() + value);
                if (items.size() == 2) {
                    fruitPickerIdField.setValue(items.get(0));
                    commentField.setValue(items.get(1));

                } else {
                    Notification.show(env.getProperty("not.valid.qr"));
                    fruitPickerIdField.setValue("");
                    commentField.setValue("");
                    File f = new File(path);
                    f.delete();
                }
            } else {
                Notification.show(env.getProperty("not.valid.qr.notification"));
                fruitPickerIdField.setValue("");
                commentField.setValue("");
                File f = new File(path);
                f.delete();
            }
        });
        save.addClickListener(e -> saveAction());
    }

    private void initComponents() {
        FormLayout formLayout = new FormLayout();
        HorizontalLayout pickerInfoLayout = new HorizontalLayout();
        root = new VerticalLayout();

        Button refreshVarietiesButton = new Button(env.getProperty("refresh.button"));
        refreshVarietiesButton.addClickListener(e ->
                refreshVarieties()
        );
        HorizontalLayout horizontalLayout = new HorizontalLayout(formLayout, refreshVarietiesButton);
        horizontalLayout.setComponentAlignment(refreshVarietiesButton, Alignment.MIDDLE_CENTER);

        qrUpload = new Upload();
        qrUpload.setCaption(env.getProperty("qr.upload.caption"));
        qrUpload.setAcceptMimeTypes("image/jpg");
        qrUpload.setButtonCaption(env.getProperty("browse.images.button"));
        qrUpload.setReceiver(new ImageUploader());
        qrUpload.setImmediateMode(true);

        AtomicBoolean isEventAfterEnteringNotExistingId = new AtomicBoolean(false);
        fruitPickerIdField = new TextField(env.getProperty("employee.id.field"));
        fruitPickerIdField.setValueChangeMode(ValueChangeMode.LAZY);
        pickersComboBox = new ComboBox<>();
        pickersComboBox.setCaption(env.getProperty("fruit.pickers.list"));
        pickersComboBox.setWidth(210, Unit.PIXELS);
        pickersComboBox.setEmptySelectionAllowed(true);
        pickersComboBox.addValueChangeListener(e->{
            if(pickersComboBox.getValue()==null || pickersComboBox.getValue().isEmpty()){
                if(!isEventAfterEnteringNotExistingId.get()){
                    fruitPickerIdField.setValue("");
                }
                isEventAfterEnteringNotExistingId.set(false);
            }else {
                String[] tab = pickersComboBox.getValue().split(" ");
                int id = Integer.parseInt(tab[0]);
                fruitPickerIdField.setValue(String.valueOf(id));
            }
        });
        refreshFruitPickers();

        fruitPickerIdField.addValueChangeListener(e->{
            if(NumberUtils.isDigits(fruitPickerIdField.getValue())){
                Optional<FruitPicker> fp = fruitPickerService.getFruitPickerById(Long.parseLong(fruitPickerIdField.getValue()));
                if(fp.isPresent()){
                    pickersComboBox.setSelectedItem(fp.get().getIdNameLastName());
                }else{
                    isEventAfterEnteringNotExistingId.set(true);
                    pickersComboBox.setSelectedItem("");
                }
            }
        });

        packageAmount = new TextField(env.getProperty("package.amount"));
        packageAmount.setValue("0");
        weightField = new TextField(env.getProperty("weight.in.gram"));
        weightField.setValue("0");
        weightField.addValueChangeListener(e -> {
            if (NumberUtils.isCreatable(weightField.getValue())) {
                float value = Float.parseFloat(weightField.getValue());
                value = value / 1000;
                weightInKgLabel.setValue(value + " kg");
            }
        });
        HorizontalLayout amountAndWeightLayout = new HorizontalLayout(packageAmount, weightField, weightInKgLabel);
        amountAndWeightLayout.setIcon(VaadinIcons.STORAGE);

        fruitClassRadioButton = new RadioButtonGroup<>();
        fruitClassRadioButton.setIcon(VaadinIcons.STAR_HALF_LEFT_O);
        fruitClassRadioButton.setItems(List.of(env.getProperty("first.category"), env.getProperty("second.category")));
        fruitClassRadioButton.setCaption(env.getProperty("fruit.category"));


        fruitTypeRadioButton = new RadioButtonGroup<>();
        fruitTypeRadioButton.setIcon(VaadinIcons.DIAMOND);

        fruitVarietyRadioButton = new RadioButtonGroup<>();
        fruitVarietyRadioButton.setIcon(VaadinIcons.CLUSTER);
        commentField = new TextField(env.getProperty("comment"));
        commentField.setIcon(VaadinIcons.CLIPBOARD_TEXT);
        save = new Button(env.getProperty("save.button"));

        refreshTypes();
        fruitTypeRadioButton.setCaption(env.getProperty("fruit.type.caption"));

        refreshVarieties();
        fruitVarietyRadioButton.setCaption(env.getProperty("fruit.variety.caption"));

        pickerInfoLayout.addComponents(fruitPickerIdField, pickersComboBox, qrUpload);
        pickerInfoLayout.setIcon(VaadinIcons.USER);
        formLayout.addComponents(pickerInfoLayout, amountAndWeightLayout, fruitTypeRadioButton, fruitVarietyRadioButton, fruitClassRadioButton, commentField, save);

        root.addComponent(horizontalLayout);
        this.addComponent(root);
    }

    private void refreshFruitPickers(){
        pickersComboBox.setItems(fruitPickerService.findAll().stream().map(FruitPicker::getIdNameLastName));
    }

    private void refreshVarieties() {
        List<String> fruitVarietyNames = fruitVarietyService.getVarietiesNames();
        fruitVarietyRadioButton.setItems(fruitVarietyNames);
    }

    private void refreshTypes() {
        List<String> types = fruitTypeService.getTypeNames();
        fruitTypeRadioButton.setItems(types);
    }

    private void saveAction() {
        if (areValuesNumeric()) {
            if (areFieldsNotEmpty()) {
                Notification.show(env.getProperty("complete.fields.notification"));
            } else {
                if(areValuesNotLessThanZero()){
                    if(pickerExists(fruitPickerIdField.getValue())) {
                        FruitDelivery fruitDelivery = new FruitDelivery(Long.parseLong(fruitPickerIdField.getValue()),
                                fruitTypeRadioButton.getValue(), Long.parseLong(packageAmount.getValue()), commentField.getValue(),
                                fruitVarietyRadioButton.getValue(), LocalDateTime.now());
                        fruitDelivery.setFruitWeight(new BigDecimal(weightField.getValue()));
                        fruitDelivery.setSecondClassFruit(fruitClassRadioButton.getSelectedItem().isPresent() && fruitClassRadioButton.getSelectedItem()
                                .get()
                                .contains(Objects.requireNonNull(env.getProperty("second.category"))));

                        Geolocation geo = new Geolocation(this.getUI());
                        geo.getCurrentPosition(position -> {
                            Coordinates coordinates = position.getCoordinates();
                            fruitDelivery.setGeoLocalization(new GeoLocalization(coordinates.getLatitude(),
                                    coordinates.getLongitude()));
                        });
                        fruitDeliveryService.addDelivery(fruitDelivery);
                        cleanFields();
                    }else{
                        Notification.show("Picker with provided id does not exist.");
                    }
                }else{
                    Notification.show(env.getProperty("some.values.cannot.be.less.than.zero"));
                }
            }
        } else {
            Notification.show(env.getProperty("id.and.amount.must.be.integr"));
        }

    }

    private boolean pickerExists(String id) {
        Long idLong = Long.parseLong(id);
        Optional<FruitPicker> fp = fruitPickerService.getFruitPickerById(idLong);
        return fp.isPresent();
    }

    private boolean areValuesNotLessThanZero(){
        return Integer.parseInt(fruitPickerIdField.getValue())>=0 && Integer.parseInt(packageAmount.getValue())>=0
                && Integer.parseInt(weightField.getValue())>=0;
    }

    private boolean areValuesNumeric(){
        return NumberUtils.isDigits(fruitPickerIdField.getValue()) && NumberUtils.isCreatable(packageAmount.getValue())
                && NumberUtils.isCreatable(weightField.getValue());
    }

    private boolean areFieldsNotEmpty(){
        return fruitPickerIdField.isEmpty() || packageAmount.isEmpty() || fruitTypeRadioButton.isEmpty() || fruitVarietyRadioButton.isEmpty()
                || weightField.isEmpty();
    }

    private void cleanFields() {
        pickersComboBox.clear();
        fruitPickerIdField.clear();
        packageAmount.clear();
        fruitTypeRadioButton.clear();
        fruitVarietyRadioButton.clear();
        commentField.clear();
        weightField.clear();
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
                new Notification(env.getProperty("could.not.open.file.notification"),
                        e.getMessage(),
                        Notification.Type.ERROR_MESSAGE)
                        .show(Page.getCurrent());
                return null;
            }

            return fos;
        }

        public void uploadSucceeded(Upload.SucceededEvent event) {

        }


    }

}
