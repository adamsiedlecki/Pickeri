package pl.adamsiedlecki.Pickeri.tools;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
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
            writer.setStrictImageSequence(true);
            document.open();
        } catch (DocumentException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        for(FruitPicker fp: fruitPickers){
            File qrFile = QRCodeWriterTool.encode(fp.getId(),fp.getName(),fp.getLastName(),"src\\main\\resources\\qr_codes");
            try {
                Image image = Image.getInstance(qrFile.getAbsolutePath());
                PdfContentByte cb = writer.getDirectContentUnder();
                document.add(getWatermarkedImage(cb,image,fp.getId()+" "+fp.getName()+" "+fp.getLastName()));
            } catch (DocumentException e1) {
                e1.printStackTrace();
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        document.close();
        writer.close();


    }

    private static Image getWatermarkedImage(PdfContentByte cb, Image img, String watermark)
            throws DocumentException {
        float width = img.getScaledWidth();
        float height = img.getScaledHeight();
        PdfTemplate template = cb.createTemplate(width, height);
        template.addImage(img, width, 0, 0, height, 0, 0);
        ColumnText.showTextAligned(template, Element.TITLE,
                new Phrase(watermark), width / 3 , height-15 , 0);
        return Image.getInstance(template);
    }

}
