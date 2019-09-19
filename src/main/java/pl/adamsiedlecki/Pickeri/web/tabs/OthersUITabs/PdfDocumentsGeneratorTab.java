package pl.adamsiedlecki.Pickeri.web.tabs.OthersUITabs;

import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitTypeService;
import pl.adamsiedlecki.Pickeri.tools.pdf.PickersToPdfWriter;
import java.io.File;
import java.util.List;

@SpringComponent
@Scope("prototype")
public class PdfDocumentsGeneratorTab extends VerticalLayout {

    private FruitPickerService fruitPickerService;
    private FruitTypeService fruitTypeService;

    @Autowired
    public PdfDocumentsGeneratorTab(FruitPickerService fruitPickerService, FruitTypeService fruitTypeService){
        this.fruitTypeService = fruitTypeService;
        this.fruitPickerService = fruitPickerService;
        Button generatePdfButton = new Button("Generuj kody QR dla wszystkich pracowników");
        this.addComponent(generatePdfButton);

        Button generateListButton = new Button("Generuj listę z ID dla wszystkich pracowników");
        this.addComponent(generateListButton);

        Button generatePickersRaport = new Button("Generuj raport na temat pracowników");
        this.addComponent(generatePickersRaport);

        Button generateEarningsRaport = new Button("Generuj raport na temat zarobków pracowników [kg]");
        HorizontalLayout earningsByKgLayout = new HorizontalLayout();

        TextField priceTypeOneField = new TextField("Cena [w zł]  [za kg] dla typu: "+fruitTypeService.getType(0).getName());
        priceTypeOneField.setValue("0");
        if(fruitTypeService.getType(0).getName()!=null){
            earningsByKgLayout.addComponentsAndExpand(priceTypeOneField);
        }
        TextField priceTypeTwoField = new TextField("Cena [w zł] [za kg] dla typu: "+fruitTypeService.getType(1).getName());
        priceTypeTwoField.setValue("0");
        if(fruitTypeService.getType(1).getName()!=null){
            earningsByKgLayout.addComponentsAndExpand(priceTypeTwoField);
        }
        TextField priceTypeThreeField = new TextField("Cena [w zł]  [za kg] dla typu: "+fruitTypeService.getType(2).getName());
        priceTypeThreeField.setValue("0");
        if(fruitTypeService.getType(2).getName()!=null){
            earningsByKgLayout.addComponentsAndExpand(priceTypeThreeField);
        }
        TextField priceTypeFourField = new TextField("Cena [w zł]  [za kg] dla typu: "+fruitTypeService.getType(3).getName());
        priceTypeFourField.setValue("0");
        if(fruitTypeService.getType(3).getName()!=null){
            earningsByKgLayout.addComponentsAndExpand(priceTypeFourField);
        }
        this.addComponent(generateEarningsRaport);
        this.addComponent(earningsByKgLayout);

        generateEarningsRaport.addClickListener(e->{
            if(NumberUtils.isCreatable(priceTypeOneField.getValue())&&NumberUtils.isCreatable(priceTypeTwoField.getValue())&&
                    NumberUtils.isCreatable(priceTypeThreeField.getValue())&&NumberUtils.isCreatable(priceTypeFourField.getValue())){
                String pdfPath = "src\\main\\resources\\downloads\\earnings.pdf";
                File check = new File(pdfPath);
                if(check.exists()){
                    check.delete();
                }
                List<FruitPicker> fruitPickers = fruitPickerService.findAll();
                PickersToPdfWriter.writeEarningsRaportByKg(fruitPickers,pdfPath,priceTypeOneField.getValue(),
                        priceTypeTwoField.getValue(), priceTypeThreeField.getValue(), priceTypeFourField.getValue());
                this.addComponent(new Link("Pobierz pdf z zarobkami pracowników",new ExternalResource("/download/pdf/earnings.pdf")));
            }else{
                Notification.show("W polach znajdują się niepoprawne wartości.");
            }
        });

        /// Earnings based on amount of packages
        Button generateEarningsRaportPackages = new Button("Generuj raport na temat zarobków pracowników [opakowania]");
        HorizontalLayout earningsByPackagesLayout = new HorizontalLayout();

        TextField priceTypeOneFieldPackages = new TextField("Cena [w zł]  [za opakowanie] dla typu: "+fruitTypeService.getType(0).getName());
        priceTypeOneFieldPackages.setValue("0");
        if(fruitTypeService.getType(0).getName()!=null){
            earningsByPackagesLayout.addComponentsAndExpand(priceTypeOneFieldPackages);
        }
        TextField priceTypeTwoFieldPackages = new TextField("Cena [w zł] [za opakowanie] dla typu: "+fruitTypeService.getType(1).getName());
        priceTypeTwoFieldPackages.setValue("0");
        if(fruitTypeService.getType(1).getName()!=null){
            earningsByPackagesLayout.addComponentsAndExpand(priceTypeTwoFieldPackages);
        }
        TextField priceTypeThreeFieldPackages = new TextField("Cena [w zł]  [za opakowanie] dla typu: "+fruitTypeService.getType(2).getName());
        priceTypeThreeFieldPackages.setValue("0");
        if(fruitTypeService.getType(2).getName()!=null){
            earningsByPackagesLayout.addComponentsAndExpand(priceTypeThreeFieldPackages);
        }
        TextField priceTypeFourFieldPackages= new TextField("Cena [w zł]  [za opakowanie] dla typu: "+fruitTypeService.getType(3).getName());
        priceTypeFourFieldPackages.setValue("0");
        if(fruitTypeService.getType(3).getName()!=null){
            earningsByPackagesLayout.addComponentsAndExpand(priceTypeFourFieldPackages);
        }
        this.addComponent(generateEarningsRaportPackages);
        this.addComponent(earningsByPackagesLayout);

        generateEarningsRaportPackages.addClickListener(e->{
            if(NumberUtils.isCreatable(priceTypeOneFieldPackages.getValue())&&NumberUtils.isCreatable(priceTypeTwoFieldPackages.getValue())&&
                    NumberUtils.isCreatable(priceTypeThreeFieldPackages.getValue())&&NumberUtils.isCreatable(priceTypeFourFieldPackages.getValue())){
                String pdfPath = "src\\main\\resources\\downloads\\earningsBasedOnPackages.pdf";
                File check = new File(pdfPath);
                if(check.exists()){
                    check.delete();
                }
                List<FruitPicker> fruitPickers = fruitPickerService.findAll();
                PickersToPdfWriter.writeEarningsRaportByPackages(fruitPickers,pdfPath,priceTypeOneFieldPackages.getValue(),
                        priceTypeTwoFieldPackages.getValue(), priceTypeThreeFieldPackages.getValue(), priceTypeFourFieldPackages.getValue());
                this.addComponent(new Link("Pobierz pdf z zarobkami pracowników (na podstawie ilości opakowań)",new ExternalResource("/download/pdf/earningsBasedOnPackages.pdf")));
            }else{
                Notification.show("W polach znajdują się niepoprawne wartości.");
            }
        });

        generatePdfButton.addClickListener(e->{
            String pdfPath = "src\\main\\resources\\downloads\\qrcodes.pdf";
            File check = new File(pdfPath);
            if(check.exists()){
                check.delete();
            }
            List<FruitPicker> fruitPickers = fruitPickerService.findAll();
            PickersToPdfWriter.writeWithQR(fruitPickers,pdfPath);
            this.addComponent(new Link("Pobierz pdf z listą z kodami QR i numerami ID",new ExternalResource("/download/pdf/qrcodes.pdf")));
        });

        generateListButton.addClickListener(e->{
            String pdfPath = "src\\main\\resources\\downloads\\idList.pdf";
            File check = new File(pdfPath);
            if(check.exists()){
                check.delete();
            }
            List<FruitPicker> fruitPickers = fruitPickerService.findAll();
            PickersToPdfWriter.writeWithoutQR(fruitPickers,pdfPath);
            this.addComponent(new Link("Pobierz pdf z listą z numerami ID",new ExternalResource("/download/pdf/idList.pdf")));
        });

        generatePickersRaport.addClickListener(e->{
            String pdfPath = "src\\main\\resources\\downloads\\pickersRaport.pdf";
            File check = new File(pdfPath);
            if(check.exists()){
                check.delete();
            }
            List<FruitPicker> fruitPickers = fruitPickerService.findAll();
            PickersToPdfWriter.writeRaport(fruitPickers,pdfPath,fruitTypeService);
            this.addComponent(new Link("Pobierz raport w pdf",new ExternalResource("/download/pdf/pickersRaport.pdf")));
        });
    }

}
