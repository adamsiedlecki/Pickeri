package pl.adamsiedlecki.Pickeri.tools.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.adamsiedlecki.Pickeri.tools.ResourceGetter;

import java.io.IOException;


class HeaderFooterPageEvent extends PdfPageEventHelper {

    private static final Logger logger = LoggerFactory.getLogger(HeaderFooterPageEvent.class);

    public void onStartPage(PdfWriter writer, Document document) {
        Image image;
        try {
            image = Image.getInstance(ResourceGetter.getSiedleckiWhiteLogo().getAbsolutePath());
            image.setAlignment(Element.ALIGN_RIGHT);
            image.setAbsolutePosition(400, 770);
            image.scalePercent(13, 13);
            writer.getDirectContent().addImage(image, true);
            document.addAuthor("Adam Siedlecki | Pickeri");
            document.addTitle("Raport");
        } catch (IOException | DocumentException e) {
            logger.error("PDF footer, header or logo exception");
        }
    }

    public void onEndPage(PdfWriter writer, Document document) {

    }
}
