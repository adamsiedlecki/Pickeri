package pl.adamsiedlecki.Pickeri.tools.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;

import java.io.IOException;

public class HeaderFooterPageEvent extends PdfPageEventHelper {

    public void onStartPage(PdfWriter writer, Document document) {
//        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase(" "), 30, 800, 0);
//        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Pickeri"), 550, 800, 0);
        Image image;
        try {
            image = Image.getInstance(ResourceGetter.getSiedleckiWhiteLogo().getAbsolutePath());
            image.setAlignment(Element.ALIGN_RIGHT);
            image.setAbsolutePosition(285, 770);
            image.scalePercent(13, 13);
            writer.getDirectContent().addImage(image, true);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    public void onEndPage(PdfWriter writer, Document document) {

    }
}
