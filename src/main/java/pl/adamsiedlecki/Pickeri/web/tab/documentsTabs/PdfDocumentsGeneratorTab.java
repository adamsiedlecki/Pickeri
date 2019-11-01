package pl.adamsiedlecki.Pickeri.web.tab.documentsTabs;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitTypeService;
import pl.adamsiedlecki.Pickeri.tools.file.FileDownloader;
import pl.adamsiedlecki.Pickeri.tools.pdf.PickersToPdfWriter;

import java.io.File;
import java.util.List;

@SpringComponent
@Scope("prototype")
public class PdfDocumentsGeneratorTab extends VerticalLayout {

    private FruitPickerService fruitPickerService;
    private FruitTypeService fruitTypeService;
    private Environment env;

    @Autowired
    public PdfDocumentsGeneratorTab(FruitPickerService fruitPickerService, FruitTypeService fruitTypeService,
                                    Environment environment) {
        this.env = environment;
        this.fruitTypeService = fruitTypeService;
        this.fruitPickerService = fruitPickerService;

        Button generatePdfButton = new Button(env.getProperty("generate.qr.codes.button"));
        generatePdfButton.setIcon(VaadinIcons.QRCODE);
        this.addComponent(generatePdfButton);

        Button generateListButton = new Button(env.getProperty("generate.id.list.button"));
        this.addComponent(generateListButton);

        Button generatePickersRaport = new Button(env.getProperty("generate.raport.button"));
        this.addComponent(generatePickersRaport);

        generatePdfButton.addClickListener(e -> {
            String pdfPath = "src\\main\\resources\\downloads\\qrcodes.pdf";
            File check = new File(pdfPath);
            if (check.exists()) {
                check.delete();
            }
            List<FruitPicker> fruitPickers = fruitPickerService.findAll();
            PickersToPdfWriter.writeWithQR(fruitPickers, pdfPath, env);
            //this.addComponent(new Link(env.getProperty("download.pdf.with.qr.and.id"), new ExternalResource("/download/pdf/qrcodes.pdf")));
            FileDownloader.action(this, "","/download/pdf/qrcodes.pdf");
        });

        generateListButton.addClickListener(e -> {
            String pdfPath = "src\\main\\resources\\downloads\\idList.pdf";
            File check = new File(pdfPath);
            if (check.exists()) {
                check.delete();
            }
            List<FruitPicker> fruitPickers = fruitPickerService.findAll();
            PickersToPdfWriter.writeWithoutQR(fruitPickers, pdfPath, env);
            //this.addComponent(new Link(env.getProperty("download.pdf.with.id"), new ExternalResource("/download/pdf/idList.pdf")));
            FileDownloader.action(this, "","/download/pdf/idList.pdf");
        });

        generatePickersRaport.addClickListener(e -> {
            String pdfPath = "src\\main\\resources\\downloads\\pickersRaport.pdf";
            File check = new File(pdfPath);
            if (check.exists()) {
                check.delete();
            }
            List<FruitPicker> fruitPickers = fruitPickerService.findAll();
            PickersToPdfWriter.writeRaport(fruitPickers, pdfPath, fruitTypeService, env);
            //this.addComponent(new Link(env.getProperty("download.raport.link"), new ExternalResource("/download/pdf/pickersRaport.pdf")));
            FileDownloader.action(this, "","/download/pdf/pickersRaport.pdf");
        });

        this.forEach(component -> this.setComponentAlignment(component, Alignment.MIDDLE_CENTER));
    }

}
