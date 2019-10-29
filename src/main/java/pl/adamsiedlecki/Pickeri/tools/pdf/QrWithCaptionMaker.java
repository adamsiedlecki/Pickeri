package pl.adamsiedlecki.Pickeri.tools.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;

class QrWithCaptionMaker {

    static Image getCaptionedImage(PdfContentByte cb, Image img, String watermark)
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
