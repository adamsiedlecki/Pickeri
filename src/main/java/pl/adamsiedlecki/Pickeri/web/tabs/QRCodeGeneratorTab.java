package pl.adamsiedlecki.Pickeri.web.tabs;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.tools.PickersToPdfWriter;
import java.io.File;
import java.util.List;

@SpringComponent
@Scope("prototype")
public class QRCodeGeneratorTab extends VerticalLayout {

    private Button generatePdfButton;
    private FruitPickerService fruitPickerService;

    @Autowired
    public QRCodeGeneratorTab(FruitPickerService fruitPickerService){
        this.fruitPickerService = fruitPickerService;
        generatePdfButton = new Button("Generuj kody QR dla wszystkich pracowników");
        this.addComponent(generatePdfButton);
        this.setComponentAlignment(generatePdfButton, Alignment.MIDDLE_CENTER);

        generatePdfButton.addClickListener(e->{
            System.out.println("pdf generation");
            String pdfPath = "src\\main\\resources\\downloads\\qrcodes.pdf";

            File check = new File(pdfPath);
            if(check.exists()){
                check.delete();
            }

            List<FruitPicker> fruitPickers = fruitPickerService.findAll();

            PickersToPdfWriter.write(fruitPickers,pdfPath);

            this.addComponent(new Link("Pobierz pdf",new ExternalResource("/download/pdf/qrcodes.pdf")));

        });
    }

}
