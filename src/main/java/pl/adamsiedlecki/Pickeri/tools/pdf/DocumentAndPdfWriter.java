package pl.adamsiedlecki.Pickeri.tools.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;

class DocumentAndPdfWriter {

    private Document document;
    private PdfWriter writer;

    public DocumentAndPdfWriter(Document document, PdfWriter writer) {
        this.document = document;
        this.writer = writer;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public PdfWriter getWriter() {
        return writer;
    }

    public void setWriter(PdfWriter writer) {
        this.writer = writer;
    }
}
