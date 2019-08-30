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
import pl.adamsiedlecki.Pickeri.tools.QRCodeWriterTool;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringComponent
@Scope("prototype")
public class QRCodeGeneratorTab extends VerticalLayout {

    private Button generatePdfButton;
    private FruitPickerService fruitPickerService;

    @Autowired
    public QRCodeGeneratorTab(FruitPickerService fruitPickerService){
        this.fruitPickerService = fruitPickerService;
        generatePdfButton = new Button("Generuj kody qr dla wszystkich pracowników");
        this.addComponent(generatePdfButton);
        this.setComponentAlignment(generatePdfButton, Alignment.MIDDLE_CENTER);

        generatePdfButton.addClickListener(e->{
            System.out.println("pdf generation");

            File check = new File("src\\main\\resources\\downloads\\qrcodes.pdf");
            if(check.exists()){
                check.delete();
            }

            List<FruitPicker> fruitPickers = fruitPickerService.findAll();

            Document document = new Document();
            PdfWriter writer = null;
            try {
                writer = PdfWriter.getInstance(document, new FileOutputStream("src\\main\\resources\\downloads\\qrcodes.pdf"));
                document.open();
                //document.addTitle("Kody QR dla pracowników z dnia: "+LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE));
            } catch (DocumentException e1) {
                e1.printStackTrace();
            } catch (FileNotFoundException e1) {
                e1.printStackTrace();
            }

            for(FruitPicker fp: fruitPickers){
                File qrFile = QRCodeWriterTool.encode(fp.getId(),fp.getName(),fp.getLastName(),"src\\main\\resources\\qr_codes");
                try {
                    document.add(new Paragraph(fp.getId()+" "+fp.getName()+" "+fp.getLastName()));
                    document.add(Image.getInstance(qrFile.getAbsolutePath()));
                    document.add(new Paragraph("- - - - - - - - - - -"));
                } catch (DocumentException e1) {
                    e1.printStackTrace();
                } catch (MalformedURLException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                System.out.println(qrFile.exists()+qrFile.getAbsolutePath());
            }
            document.close();
            writer.close();

            this.addComponent(new Link("Pobierz pdf",new ExternalResource("/download/pdf/qrcodes.pdf")));

        });
    }

}
