package pl.adamsiedlecki.Pickeri.web.tabs;

import com.vaadin.server.ExternalResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
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
public class QRCodeGeneratorTab extends VerticalLayout {

    private FruitPickerService fruitPickerService;
    private FruitTypeService fruitTypeService;

    @Autowired
    public QRCodeGeneratorTab(FruitPickerService fruitPickerService, FruitTypeService fruitTypeService){
        this.fruitTypeService = fruitTypeService;
        this.fruitPickerService = fruitPickerService;
        Button generatePdfButton = new Button("Generuj kody QR dla wszystkich pracowników");
        this.addComponent(generatePdfButton);

        Button generateListButton = new Button("Generuj listę z ID dla wszystkich pracowników");
        this.addComponent(generateListButton);

        Button generatePickersRaport = new Button("Generuj raport na temat pracowników");
        this.addComponent(generatePickersRaport);

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
