package pl.adamsiedlecki.Pickeri.tools;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

public class PickersToPdfWriter {

    public static void write(List<FruitPicker> fruitPickers, String pathToFile){

        Document document = new Document();
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(pathToFile));
            document.open();
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


    }

}
