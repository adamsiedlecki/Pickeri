package pl.adamsiedlecki.Pickeri.web.tabs.documentsTabs;

import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitTypeService;
import pl.adamsiedlecki.Pickeri.tools.file.DeleteFile;
import pl.adamsiedlecki.Pickeri.tools.pdf.PickersToPdfWriter;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Component
@Scope("prototype")
public class EarningsRaportGenerationTab extends VerticalLayout {

    private FruitPickerService fruitPickerService;
    private FruitTypeService fruitTypeService;
    private Environment env;
    private Logger log = LoggerFactory.getLogger(EarningsRaportGenerationTab.class);

    @Autowired
    public EarningsRaportGenerationTab(FruitPickerService fruitPickerService, FruitTypeService fruitTypeService, Environment environment) {
        this.fruitTypeService = fruitTypeService;
        this.fruitPickerService = fruitPickerService;
        this.env = environment;
        addFirstRow();
        addComponent(new Label("<hr />", ContentMode.HTML));
        addSecondRow();
        this.forEach(component -> this.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
    }

    public void addFirstRow(){
        Label warning = new Label(env.getProperty("payments.included.in.raport.warning"));
        this.addComponent(warning);
        this.setComponentAlignment(warning, Alignment.MIDDLE_CENTER);
        Button generateEarningsByKgRaportButton = new Button(env.getProperty("generate.earnings.by.kg.raport"));
        HorizontalLayout earningsByKgLayout = new HorizontalLayout();
        
        Optional<TextField> priceTypeOneField = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(0, earningsByKgLayout);
        Optional<TextField> priceTypeTwoField = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(1, earningsByKgLayout);
        Optional<TextField> priceTypeThreeField = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(2, earningsByKgLayout);
        Optional<TextField> priceTypeFourField = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(3, earningsByKgLayout);

        this.addComponent(generateEarningsByKgRaportButton);
        this.addComponent(earningsByKgLayout);

        generateEarningsByKgRaportButton.addClickListener(e -> {
            String price1 = priceTypeOneField.isPresent() ? priceTypeOneField.get().getValue() : "0.00";
            String price2 = priceTypeTwoField.isPresent() ? priceTypeTwoField.get().getValue() : "0.00";
            String price3 = priceTypeThreeField.isPresent() ? priceTypeThreeField.get().getValue() : "0.00";
            String price4 = priceTypeFourField.isPresent() ? priceTypeFourField.get().getValue() : "0.00";

            if (NumberUtils.isCreatable(price1) && NumberUtils.isCreatable(price2) && NumberUtils.isCreatable(price3)
                    && NumberUtils.isCreatable(price4)) {
                String pdfPath = "src\\main\\resources\\downloads\\earnings.pdf";
                DeleteFile.ifExists(new File(pdfPath));
                List<FruitPicker> fruitPickers = fruitPickerService.findAll();
                PickersToPdfWriter.writeEarningsRaportByKg(fruitPickers, pdfPath, price1,
                        price2, price3, price4, true, env);
                this.addComponent(new Link(env.getProperty("download.earnings.pdf.button"), new ExternalResource("/download/pdf/earnings.pdf")));
            } else {
                Notification.show(env.getProperty("incorrect.values.notification"));
            }
        });

        /// Earnings based on amount of packages
        Button generateEarningsRaportPackages = new Button(env.getProperty("generate.earnings.by.packages.raport"));
        HorizontalLayout earningsByPackagesLayout = new HorizontalLayout();

        TextField priceTypeOneFieldPackages = new TextField(env.getProperty("price.field.caption") + fruitTypeService.getType(0).getName());
        priceTypeOneFieldPackages.setValue("0");
        if (fruitTypeService.getType(0).getName() != null) {
            earningsByPackagesLayout.addComponent(priceTypeOneFieldPackages);
        }
        TextField priceTypeTwoFieldPackages = new TextField(env.getProperty("price.field.caption") + fruitTypeService.getType(1).getName());
        priceTypeTwoFieldPackages.setValue("0");
        if (fruitTypeService.getType(1).getName() != null) {
            earningsByPackagesLayout.addComponent(priceTypeTwoFieldPackages);
        }
        TextField priceTypeThreeFieldPackages = new TextField(env.getProperty("price.field.caption") + fruitTypeService.getType(2).getName());
        priceTypeThreeFieldPackages.setValue("0");
        if (fruitTypeService.getType(2).getName() != null) {
            earningsByPackagesLayout.addComponent(priceTypeThreeFieldPackages);
        }
        TextField priceTypeFourFieldPackages = new TextField(env.getProperty("price.field.caption") + fruitTypeService.getType(3).getName());
        priceTypeFourFieldPackages.setValue("0");
        if (fruitTypeService.getType(3).getName() != null) {
            earningsByPackagesLayout.addComponent(priceTypeFourFieldPackages);
        }
        this.addComponent(generateEarningsRaportPackages);
        this.addComponent(earningsByPackagesLayout);

        generateEarningsRaportPackages.addClickListener(e -> {
            if (NumberUtils.isCreatable(priceTypeOneFieldPackages.getValue()) && NumberUtils.isCreatable(priceTypeTwoFieldPackages.getValue()) &&
                    NumberUtils.isCreatable(priceTypeThreeFieldPackages.getValue()) && NumberUtils.isCreatable(priceTypeFourFieldPackages.getValue())) {
                String pdfPath = "src\\main\\resources\\downloads\\earningsBasedOnPackages.pdf";
                File check = new File(pdfPath);
                if (check.exists()) {
                    check.delete();
                }
                List<FruitPicker> fruitPickers = fruitPickerService.findAll();
                PickersToPdfWriter.writeEarningsRaportByPackages(fruitPickers, pdfPath, priceTypeOneFieldPackages.getValue(),
                        priceTypeTwoFieldPackages.getValue(), priceTypeThreeFieldPackages.getValue(), priceTypeFourFieldPackages.getValue(), true, env);
                this.addComponent(new Link(env.getProperty("download.earnings.by.packages.pdf.button"), new ExternalResource("/download/pdf/earningsBasedOnPackages.pdf")));
            } else {
                Notification.show(env.getProperty("incorrect.values.notification"));
            }
        });
    }

    private Optional<TextField> getPriceFieldIfTypeNameIsNotNullAndAddToLayout(int typeSlot, Layout layout){
        TextField textField;
        if (fruitTypeService.getType(typeSlot).getName() != null) {
            textField = new TextField();
            textField.setCaption(env.getProperty("price.field.caption") + fruitTypeService.getType(typeSlot).getName());
            textField.setValue("0.00");
            layout.addComponent(textField);
            return Optional.of(textField);
        }
        return Optional.empty();
    }

    public void addSecondRow(){
        Label warning = new Label(env.getProperty("pdf.not.includes.payments.warning"));
        this.addComponent(warning);
        Button generateEarningsRaport = new Button(env.getProperty("generate.earnings.by.kg.raport"));
        HorizontalLayout earningsByKgLayout = new HorizontalLayout();

        TextField priceTypeOneField = new TextField(env.getProperty("price.field.caption") + fruitTypeService.getType(0).getName());
        priceTypeOneField.setValue("0");
        if (fruitTypeService.getType(0).getName() != null) {
            earningsByKgLayout.addComponentsAndExpand(priceTypeOneField);
        }
        TextField priceTypeTwoField = new TextField(env.getProperty("price.field.caption") + fruitTypeService.getType(1).getName());
        priceTypeTwoField.setValue("0");
        if (fruitTypeService.getType(1).getName() != null) {
            earningsByKgLayout.addComponentsAndExpand(priceTypeTwoField);
        }
        TextField priceTypeThreeField = new TextField(env.getProperty("price.field.caption") + fruitTypeService.getType(2).getName());
        priceTypeThreeField.setValue("0");
        if (fruitTypeService.getType(2).getName() != null) {
            earningsByKgLayout.addComponentsAndExpand(priceTypeThreeField);
        }
        TextField priceTypeFourField = new TextField(env.getProperty("price.field.caption") + fruitTypeService.getType(3).getName());
        priceTypeFourField.setValue("0");
        if (fruitTypeService.getType(3).getName() != null) {
            earningsByKgLayout.addComponentsAndExpand(priceTypeFourField);
        }
        this.addComponent(generateEarningsRaport);
        this.addComponent(earningsByKgLayout);

        generateEarningsRaport.addClickListener(e -> {
            if (NumberUtils.isCreatable(priceTypeOneField.getValue()) && NumberUtils.isCreatable(priceTypeTwoField.getValue()) &&
                    NumberUtils.isCreatable(priceTypeThreeField.getValue()) && NumberUtils.isCreatable(priceTypeFourField.getValue())) {
                String pdfPath = "src\\main\\resources\\downloads\\earnings.pdf";
                File check = new File(pdfPath);
                if (check.exists()) {
                    check.delete();
                }
                List<FruitPicker> fruitPickers = fruitPickerService.findAll();
                PickersToPdfWriter.writeEarningsRaportByKg(fruitPickers, pdfPath, priceTypeOneField.getValue(),
                        priceTypeTwoField.getValue(), priceTypeThreeField.getValue(), priceTypeFourField.getValue(), false, env);
                this.addComponent(new Link(env.getProperty("download.earnings.pdf.button"), new ExternalResource("/download/pdf/earnings.pdf")));
            } else {
                Notification.show(env.getProperty("incorrect.values.notification"));
            }
        });

        /// Earnings based on amount of packages
        Button generateEarningsRaportPackages = new Button(env.getProperty("generate.earnings.by.packages.raport"));
        HorizontalLayout earningsByPackagesLayout = new HorizontalLayout();

        TextField priceTypeOneFieldPackages = new TextField(env.getProperty("price.per.package.for.type") + fruitTypeService.getType(0).getName());
        priceTypeOneFieldPackages.setValue("0");
        if (fruitTypeService.getType(0).getName() != null) {
            earningsByPackagesLayout.addComponentsAndExpand(priceTypeOneFieldPackages);
        }
        TextField priceTypeTwoFieldPackages = new TextField(env.getProperty("price.per.package.for.type") + fruitTypeService.getType(1).getName());
        priceTypeTwoFieldPackages.setValue("0");
        if (fruitTypeService.getType(1).getName() != null) {
            earningsByPackagesLayout.addComponentsAndExpand(priceTypeTwoFieldPackages);
        }
        TextField priceTypeThreeFieldPackages = new TextField(env.getProperty("price.per.package.for.type") + fruitTypeService.getType(2).getName());
        priceTypeThreeFieldPackages.setValue("0");
        if (fruitTypeService.getType(2).getName() != null) {
            earningsByPackagesLayout.addComponentsAndExpand(priceTypeThreeFieldPackages);
        }
        TextField priceTypeFourFieldPackages = new TextField(env.getProperty("price.per.package.for.type") + fruitTypeService.getType(3).getName());
        priceTypeFourFieldPackages.setValue("0");
        if (fruitTypeService.getType(3).getName() != null) {
            earningsByPackagesLayout.addComponentsAndExpand(priceTypeFourFieldPackages);
        }
        this.addComponent(generateEarningsRaportPackages);
        this.addComponent(earningsByPackagesLayout);

        generateEarningsRaportPackages.addClickListener(e -> {
            if (NumberUtils.isCreatable(priceTypeOneFieldPackages.getValue()) && NumberUtils.isCreatable(priceTypeTwoFieldPackages.getValue()) &&
                    NumberUtils.isCreatable(priceTypeThreeFieldPackages.getValue()) && NumberUtils.isCreatable(priceTypeFourFieldPackages.getValue())) {
                String pdfPath = "src\\main\\resources\\downloads\\earningsBasedOnPackages.pdf";
                File check = new File(pdfPath);
                if (check.exists()) {
                    check.delete();
                }
                List<FruitPicker> fruitPickers = fruitPickerService.findAll();
                PickersToPdfWriter.writeEarningsRaportByPackages(fruitPickers, pdfPath, priceTypeOneFieldPackages.getValue(),
                        priceTypeTwoFieldPackages.getValue(), priceTypeThreeFieldPackages.getValue(), priceTypeFourFieldPackages.getValue(), false, env);
                this.addComponent(new Link(env.getProperty("download.earnings.by.packages.pdf.button"),
                        new ExternalResource("/download/pdf/earningsBasedOnPackages.pdf")));
            } else {
                Notification.show(env.getProperty("incorrect.values.notification"));
            }
        });

    }

}
