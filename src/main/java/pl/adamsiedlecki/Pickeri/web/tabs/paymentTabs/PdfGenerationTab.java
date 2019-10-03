package pl.adamsiedlecki.Pickeri.web.tabs.paymentTabs;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitTypeService;
import pl.adamsiedlecki.Pickeri.tools.pdf.PickersToPdfWriter;

import java.io.File;
import java.util.List;

@Component
@Scope("prototype")
public class PdfGenerationTab extends VerticalLayout {

    private FruitPickerService fruitPickerService;
    private FruitTypeService fruitTypeService;
    private Environment env;

    @Autowired
    public PdfGenerationTab(FruitPickerService fruitPickerService, FruitTypeService fruitTypeService, Environment environment) {
        this.fruitTypeService = fruitTypeService;
        this.fruitPickerService = fruitPickerService;
        this.env = environment;
        Label warning = new Label(env.getProperty("payments.included.in.raport.warning"));
        this.addComponent(warning);
        this.setComponentAlignment(warning, Alignment.MIDDLE_CENTER);
        Button generateEarningsByKgRaport = new Button(env.getProperty("generate.earnings.by.kg.raport"));
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
        this.addComponent(generateEarningsByKgRaport);
        this.addComponent(earningsByKgLayout);

        generateEarningsByKgRaport.addClickListener(e -> {
            if (NumberUtils.isCreatable(priceTypeOneField.getValue()) && NumberUtils.isCreatable(priceTypeTwoField.getValue()) &&
                    NumberUtils.isCreatable(priceTypeThreeField.getValue()) && NumberUtils.isCreatable(priceTypeFourField.getValue())) {
                String pdfPath = "src\\main\\resources\\downloads\\earnings.pdf";
                File check = new File(pdfPath);
                if (check.exists()) {
                    check.delete();
                }
                List<FruitPicker> fruitPickers = fruitPickerService.findAll();
                PickersToPdfWriter.writeEarningsRaportByKg(fruitPickers, pdfPath, priceTypeOneField.getValue(),
                        priceTypeTwoField.getValue(), priceTypeThreeField.getValue(), priceTypeFourField.getValue(), true);
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
            earningsByPackagesLayout.addComponentsAndExpand(priceTypeOneFieldPackages);
        }
        TextField priceTypeTwoFieldPackages = new TextField(env.getProperty("price.field.caption") + fruitTypeService.getType(1).getName());
        priceTypeTwoFieldPackages.setValue("0");
        if (fruitTypeService.getType(1).getName() != null) {
            earningsByPackagesLayout.addComponentsAndExpand(priceTypeTwoFieldPackages);
        }
        TextField priceTypeThreeFieldPackages = new TextField(env.getProperty("price.field.caption") + fruitTypeService.getType(2).getName());
        priceTypeThreeFieldPackages.setValue("0");
        if (fruitTypeService.getType(2).getName() != null) {
            earningsByPackagesLayout.addComponentsAndExpand(priceTypeThreeFieldPackages);
        }
        TextField priceTypeFourFieldPackages = new TextField(env.getProperty("price.field.caption") + fruitTypeService.getType(3).getName());
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
                        priceTypeTwoFieldPackages.getValue(), priceTypeThreeFieldPackages.getValue(), priceTypeFourFieldPackages.getValue(), true);
                this.addComponent(new Link(env.getProperty("download.earnings.by.packages.pdf.button"), new ExternalResource("/download/pdf/earningsBasedOnPackages.pdf")));
            } else {
                Notification.show(env.getProperty("incorrect.values.notification"));
            }
        });
    }

}
