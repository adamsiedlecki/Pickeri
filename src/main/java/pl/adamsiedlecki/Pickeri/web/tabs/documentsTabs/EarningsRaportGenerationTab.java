package pl.adamsiedlecki.Pickeri.web.tabs.documentsTabs;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.*;
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
import pl.adamsiedlecki.Pickeri.tools.file.FileDownloader;
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
        
        Optional<TextField> priceTypeOneFieldKg = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(0, earningsByKgLayout, true);
        Optional<TextField> priceTypeTwoFieldKg = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(1, earningsByKgLayout, true);
        Optional<TextField> priceTypeThreeFieldKg = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(2, earningsByKgLayout, true);
        Optional<TextField> priceTypeFourFieldKg = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(3, earningsByKgLayout, true);

        this.addComponent(generateEarningsByKgRaportButton);
        this.addComponent(earningsByKgLayout);

        generateEarningsByKgRaportButton.addClickListener(e -> {
            String price1 = priceTypeOneFieldKg.isPresent() ? priceTypeOneFieldKg.get().getValue() : "0.00";
            String price2 = priceTypeTwoFieldKg.isPresent() ? priceTypeTwoFieldKg.get().getValue() : "0.00";
            String price3 = priceTypeThreeFieldKg.isPresent() ? priceTypeThreeFieldKg.get().getValue() : "0.00";
            String price4 = priceTypeFourFieldKg.isPresent() ? priceTypeFourFieldKg.get().getValue() : "0.00";
            if (NumberUtils.isCreatable(price1) && NumberUtils.isCreatable(price2) && NumberUtils.isCreatable(price3)
                    && NumberUtils.isCreatable(price4)) {
                String pdfPath = "src\\main\\resources\\downloads\\earnings.pdf";
                DeleteFile.ifExists(new File(pdfPath));
                List<FruitPicker> fruitPickers = fruitPickerService.findAll();
                PickersToPdfWriter.writeEarningsRaportByKg(fruitPickers, pdfPath, price1,
                        price2, price3, price4, true, env);
                FileDownloader.action(this, env.getProperty("download.earnings.pdf.button"), "/download/pdf/earnings.pdf");
            } else {
                Notification.show(env.getProperty("incorrect.values.notification"));
            }
        });

        // Earnings based on amount of packages
        Button generateEarningsRaportPackagesButton = new Button(env.getProperty("generate.earnings.by.packages.raport"));
        HorizontalLayout earningsByPackagesLayout = new HorizontalLayout();

        Optional<TextField> priceTypeOneFieldPackages = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(0, earningsByPackagesLayout, false);
        Optional<TextField> priceTypeTwoFieldPackages = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(1, earningsByPackagesLayout, false);
        Optional<TextField> priceTypeThreeFieldPackages = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(2, earningsByPackagesLayout, false);
        Optional<TextField> priceTypeFourFieldPackages = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(3, earningsByPackagesLayout, false);

        this.addComponent(generateEarningsRaportPackagesButton);
        this.addComponent(earningsByPackagesLayout);

        generateEarningsRaportPackagesButton.addClickListener(e -> {
            String price1 = priceTypeOneFieldPackages.isPresent() ? priceTypeOneFieldPackages.get().getValue() : "0.00";
            String price2 = priceTypeTwoFieldPackages.isPresent() ? priceTypeTwoFieldPackages.get().getValue() : "0.00";
            String price3 = priceTypeThreeFieldPackages.isPresent() ? priceTypeThreeFieldPackages.get().getValue() : "0.00";
            String price4 = priceTypeFourFieldPackages.isPresent() ? priceTypeFourFieldPackages.get().getValue() : "0.00";
            if (NumberUtils.isCreatable(price1) && NumberUtils.isCreatable(price2) &&
                    NumberUtils.isCreatable(price3) && NumberUtils.isCreatable(price4)) {
                String pdfPath = "src\\main\\resources\\downloads\\earningsBasedOnPackages.pdf";
                DeleteFile.ifExists(new File(pdfPath));
                List<FruitPicker> fruitPickers = fruitPickerService.findAll();
                PickersToPdfWriter.writeEarningsRaportByPackages(fruitPickers, pdfPath, price1, price2, price3, price4, true, env);
                FileDownloader.action(this, env.getProperty("download.earnings.by.packages.pdf.button"),
                        "/download/pdf/earningsBasedOnPackages.pdf");
            } else {
                Notification.show(env.getProperty("incorrect.values.notification"));
            }
        });
    }

    public void addSecondRow(){
        Label warning = new Label(env.getProperty("pdf.not.includes.payments.warning"));
        this.addComponent(warning);
        Button generateEarningsRaportButton = new Button(env.getProperty("generate.earnings.by.kg.raport"));
        HorizontalLayout earningsByKgLayout = new HorizontalLayout();

        Optional<TextField> priceTypeOneFieldKg = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(0, earningsByKgLayout, true);
        Optional<TextField> priceTypeTwoFieldKg = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(1, earningsByKgLayout, true);
        Optional<TextField> priceTypeThreeFieldKg = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(2, earningsByKgLayout, true);
        Optional<TextField> priceTypeFourFieldKg = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(3, earningsByKgLayout, true);

        this.addComponent(generateEarningsRaportButton);
        this.addComponent(earningsByKgLayout);

        generateEarningsRaportButton.addClickListener(e -> {
            String price1 = priceTypeOneFieldKg.isPresent() ? priceTypeOneFieldKg.get().getValue() : "0.00";
            String price2 = priceTypeTwoFieldKg.isPresent() ? priceTypeTwoFieldKg.get().getValue() : "0.00";
            String price3 = priceTypeThreeFieldKg.isPresent() ? priceTypeThreeFieldKg.get().getValue() : "0.00";
            String price4 = priceTypeFourFieldKg.isPresent() ? priceTypeFourFieldKg.get().getValue() : "0.00";
            if (NumberUtils.isCreatable(price1) && NumberUtils.isCreatable(price2) &&
                    NumberUtils.isCreatable(price3) && NumberUtils.isCreatable(price4)) {
                String pdfPath = "src\\main\\resources\\downloads\\earnings.pdf";
                DeleteFile.ifExists(new File(pdfPath));
                List<FruitPicker> fruitPickers = fruitPickerService.findAll();
                PickersToPdfWriter.writeEarningsRaportByKg(fruitPickers, pdfPath, price1, price2, price3, price4,
                        false, env);
                FileDownloader.action(this, env.getProperty("download.earnings.pdf.button"), "/download/pdf/earnings.pdf");
            } else {
                Notification.show(env.getProperty("incorrect.values.notification"));
            }
        });

        // Earnings based on amount of packages
        Button generateEarningsRaportPackages = new Button(env.getProperty("generate.earnings.by.packages.raport"));
        HorizontalLayout earningsByPackagesLayout = new HorizontalLayout();

        Optional<TextField> priceTypeOneFieldPackages = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(0, earningsByPackagesLayout, false);
        Optional<TextField> priceTypeTwoFieldPackages = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(1, earningsByPackagesLayout, false);
        Optional<TextField> priceTypeThreeFieldPackages = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(2, earningsByPackagesLayout, false);
        Optional<TextField> priceTypeFourFieldPackages = getPriceFieldIfTypeNameIsNotNullAndAddToLayout(3, earningsByPackagesLayout, false);

        this.addComponent(generateEarningsRaportPackages);
        this.addComponent(earningsByPackagesLayout);

        generateEarningsRaportPackages.addClickListener(e -> {
            String price1 = priceTypeOneFieldPackages.isPresent() ? priceTypeOneFieldPackages.get().getValue() : "0.00";
            String price2 = priceTypeTwoFieldPackages.isPresent() ? priceTypeTwoFieldPackages.get().getValue() : "0.00";
            String price3 = priceTypeThreeFieldPackages.isPresent() ? priceTypeThreeFieldPackages.get().getValue() : "0.00";
            String price4 = priceTypeFourFieldPackages.isPresent() ? priceTypeFourFieldPackages.get().getValue() : "0.00";
            if (NumberUtils.isCreatable(price1) && NumberUtils.isCreatable(price2) &&
                    NumberUtils.isCreatable(price3) && NumberUtils.isCreatable(price4)) {
                String pdfPath = "src\\main\\resources\\downloads\\earningsBasedOnPackages.pdf";
                DeleteFile.ifExists(new File(pdfPath));
                List<FruitPicker> fruitPickers = fruitPickerService.findAll();
                PickersToPdfWriter.writeEarningsRaportByPackages(fruitPickers, pdfPath, price1, price2, price3, price4, false, env);
                FileDownloader.action(this, env.getProperty("download.earnings.by.packages.pdf.button"),"/download/pdf/earningsBasedOnPackages.pdf");
            } else {
                Notification.show(env.getProperty("incorrect.values.notification"));
            }
        });

    }

    private Optional<TextField> getPriceFieldIfTypeNameIsNotNullAndAddToLayout(int typeSlot, Layout layout, boolean isPerKgPrice){
        TextField textField;
        if (fruitTypeService.getType(typeSlot).getName() != null) {
            textField = new TextField();
            if(isPerKgPrice){
                textField.setCaption(env.getProperty("price.field.kg.caption") + fruitTypeService.getType(typeSlot).getName());
            }else{
                textField.setCaption(env.getProperty("price.per.package.for.type") + fruitTypeService.getType(typeSlot).getName());
            }
            textField.setValue("0.00");
            layout.addComponent(textField);
            return Optional.of(textField);
        }
        return Optional.empty();
    }

}
