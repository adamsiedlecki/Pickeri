package pl.adamsiedlecki.Pickeri.tools.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;

class PdfCreator {

    private static final Logger log = LoggerFactory.getLogger(PdfCreator.class);

    DocumentAndPdfWriter getBasics(String destionationPath, String documentTitle){
        Document document = new Document();
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(destionationPath));
            writer.setStrictImageSequence(true);
            addHeaderFooter(writer);
            document.open();
            addTitle(document, documentTitle);
        } catch (DocumentException | FileNotFoundException e) {
            log.error("Write QR ", e.getCause());
        }

        DocumentAndPdfWriter documentAndWriter = new DocumentAndPdfWriter(document, writer);
        return documentAndWriter;
    }

    private static void addHeaderFooter(PdfWriter writer) {
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        writer.setPageEvent(event);
    }

    private static void addTitle(Document document, String title) {
        try {
            document.add(new Paragraph(title,
                    FontFactory.getFont(FontFactory.HELVETICA, "CP1250", 12, Font.BOLD)));
            document.add(new Paragraph(" "));
        } catch (DocumentException e) {
            log.error("Add title - DocumentException - "+e.getMessage());
        }
    }

    private static Image getWatermarkedImage(PdfContentByte cb, Image img, String watermark)
            throws DocumentException {
        float width = img.getScaledWidth();
        float height = img.getScaledHeight();
        PdfTemplate template = cb.createTemplate(width, height);
        template.addImage(img, width, 0, 0, height, 0, 0);
        ColumnText.showTextAligned(template, Element.TITLE,
                new Phrase(watermark, FontFactory.getFont(FontFactory.HELVETICA, "CP1250", 12, Font.NORMAL)), width / 3, height - 15, 0);
        return Image.getInstance(template);
    }

}
