package pl.adamsiedlecki.Pickeri.tools.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitTypeService;
import pl.adamsiedlecki.Pickeri.tools.QRCodeWriterTool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PickersToPdfWriter {

    public static void writeWithQR(List<FruitPicker> fruitPickers, String pathToFile) {

        Document document = new Document();
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(pathToFile));
            writer.setStrictImageSequence(true);
            addHeaderFooter(writer);
            document.open();
            addTitle(document, "Lista pracowników z Pickeri " + LocalDate.now());
        } catch (DocumentException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        for (FruitPicker fp : fruitPickers) {
            File qrFile = QRCodeWriterTool.encode(fp.getId(), fp.getName(), fp.getLastName(), "src\\main\\resources\\qr_codes");
            try {
                Image image = Image.getInstance(qrFile.getAbsolutePath());
                PdfContentByte cb = writer.getDirectContentUnder();
                document.add(getWatermarkedImage(cb, image, fp.getId() + " " + fp.getName() + " " + fp.getLastName()));
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

    public static void writeWithoutQR(List<FruitPicker> fruitPickers, String pathToFile) {

        Document document = new Document();
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(pathToFile));
            writer.setStrictImageSequence(true);
            addHeaderFooter(writer);

            document.open();
            addTitle(document, "Lista pracowników z Pickeri " + LocalDate.now());
        } catch (DocumentException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }


        for (FruitPicker fp : fruitPickers) {
            try {
                PdfContentByte cb = writer.getDirectContentUnder();
                document.add(new Paragraph(fp.getId() + " " + fp.getName() + " " + fp.getLastName(),
                        FontFactory.getFont(FontFactory.HELVETICA, "CP1250", 12, Font.NORMAL)));
            } catch (DocumentException e1) {
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
                new Phrase(watermark, FontFactory.getFont(FontFactory.HELVETICA, "CP1250", 12, Font.NORMAL)), width / 3, height - 15, 0);
        return Image.getInstance(template);
    }

    private static void addTitle(Document document, String title) {
        try {
            document.add(new Paragraph(title,
                    FontFactory.getFont(FontFactory.HELVETICA, "CP1250", 12, Font.BOLD)));
            document.add(new Paragraph(" "));
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    private static void addHeaderFooter(PdfWriter writer) {
        HeaderFooterPageEvent event = new HeaderFooterPageEvent();
        writer.setPageEvent(event);
    }

    public static void writeRaport(List<FruitPicker> fruitPickers, String pdfPath, FruitTypeService fruitTypeService) {

        Document document = new Document();
        PdfWriter writer = null;

        float [] pointColumnWidths = {60F, 150F, 150F, 150F, 150F, 150F, 150F, 150F};
        PdfPTable table = new PdfPTable(pointColumnWidths);
        table.addCell("ID");
        table.addCell("Imię i nazwisko");
        table.addCell("Suma [opak.]");
        table.addCell("Suma [kg]");
        for (int i = 0; i < 4; i++) {
            if(fruitTypeService.getType(i).getName() != null){
                table.addCell(fruitTypeService.getType(i).getName()+" [opakowania]");
                table.addCell(fruitTypeService.getType(i).getName()+" [kg]");
            }
        }

        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            writer.setStrictImageSequence(true);
            addHeaderFooter(writer);

            document.open();
            addTitle(document, "Raport pracowniczy Pickeri " + LocalDate.now());
        } catch (DocumentException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }


        for (FruitPicker fp : fruitPickers) {

                List<String> amountInfo1 = (getTypeInfoToString(fruitTypeService, fp, 0));
                List<String> amountInfo2 = (getTypeInfoToString(fruitTypeService, fp, 1));
                List<String> amountInfo3 = (getTypeInfoToString(fruitTypeService, fp, 2));
                List<String> amountInfo4 = (getTypeInfoToString(fruitTypeService, fp, 3));

//                document.add(new Paragraph(fp.getId() + " " + fp.getName() + " " + fp.getLastName() + " | suma opakowań: " + fp.getPackageDeliveryAmount()
//                        + " | suma wagi [kg]: " + fp.getWeightSumKgPlainText() + amountInfo,
//                        FontFactory.getFont(FontFactory.HELVETICA, "CP1250", 12, Font.NORMAL)));
                table.addCell(Long.toString(fp.getId()));
                table.addCell(fp.getName() + " " + fp.getLastName());
                table.addCell(Long.toString(fp.getPackageDeliveryAmount()));
                table.addCell(fp.getWeightSumKgPlainText());
                if(amountInfo1.size()==2){
                    table.addCell(amountInfo1.get(0));
                    table.addCell(amountInfo1.get(1));
                }
                if(amountInfo2.size()==2){
                    table.addCell(amountInfo2.get(0));
                    table.addCell(amountInfo2.get(1));
                }
                if(amountInfo3.size()==2){
                    table.addCell(amountInfo3.get(0));
                    table.addCell(amountInfo3.get(1));
                }
                if(amountInfo4.size()==2){
                    table.addCell(amountInfo4.get(0));
                    table.addCell(amountInfo4.get(1));
                }

            try {
                document.add(table);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }

        document.close();
        writer.close();

    }

    private static List<String> getTypeInfoToString(FruitTypeService fruitTypeService, FruitPicker fp, int typeSlot) {
        String result = "";
        String result2 = "";
        if (fruitTypeService.getType(typeSlot) != null && fruitTypeService.getType(typeSlot).getName() != null) {
            if (typeSlot == 0) {
                result = Long.toString(fp.getPackageDeliveryWithTypeOne());
            } else if (typeSlot == 1) {
                result = Long.toString(fp.getPackageDeliveryWithTypeTwo());
            } else if (typeSlot == 2) {
                result = Long.toString(fp.getPackageDeliveryWithTypeThree());
            } else if (typeSlot == 3) {
                result = Long.toString(fp.getPackageDeliveryWithTypeFour());
            }

            if (typeSlot == 0) {
                result2 = (fp.getWeightKgWithTypeOnePlainText());
            } else if (typeSlot == 1) {
                result2 = fp.getWeightKgWithTypeTwoPlainText();
            } else if (typeSlot == 2) {
                result2 = fp.getWeightKgWithTypeThreePlainText();
            } else if (typeSlot == 3) {
                result2 = fp.getWeightKgWithTypeFourPlainText();
            }
        }
        if("".equals(result)||"".equals(result2))
            return List.of();
        return List.of(result,result2);
    }

    public static void writeEarningsRaportByKg(List<FruitPicker> fruitPickers, String pdfPath, String priceForTypeOne,
                                               String priceForTypeTwo, String priceForTypeThree, String priceForTypeFour) {

        Document document = new Document();
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            writer.setStrictImageSequence(true);
            addHeaderFooter(writer);

            document.open();
            addTitle(document, "Raport zarobków pracowników Pickeri [kg] " + LocalDate.now());
        } catch (DocumentException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        for (FruitPicker fp : fruitPickers) {
            try {
                PdfContentByte cb = writer.getDirectContentUnder();

                BigDecimal earnings1 = calculateEarnings(fp.getWeightWithTypeOne(), new BigDecimal(priceForTypeOne));
                BigDecimal earnings2 = calculateEarnings(fp.getWeightWithTypeTwo(), new BigDecimal(priceForTypeTwo));
                BigDecimal earnings3 = calculateEarnings(fp.getWeightWithTypeThree(), new BigDecimal(priceForTypeThree));
                BigDecimal earnings4 = calculateEarnings(fp.getWeightWithTypeFour(), new BigDecimal(priceForTypeFour));
                BigDecimal sum = earnings1.add(earnings2).add(earnings3).add(earnings4);

                document.add(new Paragraph(fp.getId() + " " + fp.getName() + " " + fp.getLastName() + " : " + sum + " zł",
                        FontFactory.getFont(FontFactory.HELVETICA, "CP1250", 12, Font.NORMAL)));
            } catch (DocumentException e1) {
                e1.printStackTrace();
            }
        }
        document.close();
        writer.close();
    }

    private static BigDecimal calculateEarnings(BigDecimal weight, BigDecimal price) {
        BigDecimal weightKg = weight.divide(new BigDecimal(1000), 4, RoundingMode.FLOOR);
        return weightKg.multiply(price);
    }

    private static BigDecimal calculateEarnings(long amountOfPackages, BigDecimal price) {
        BigDecimal amount = new BigDecimal(amountOfPackages);
        return amount.multiply(price);
    }

    public static void writeEarningsRaportByPackages(List<FruitPicker> fruitPickers, String pdfPath, String priceForTypeOne,
                                                     String priceForTypeTwo, String priceForTypeThree, String priceForTypeFour) {

        Document document = new Document();
        PdfWriter writer = null;
        try {
            writer = PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            writer.setStrictImageSequence(true);
            addHeaderFooter(writer);

            document.open();
            addTitle(document, "Raport zarobków pracowników Pickeri [opakowania] " + LocalDate.now());
        } catch (DocumentException e1) {
            e1.printStackTrace();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        for (FruitPicker fp : fruitPickers) {
            try {
                PdfContentByte cb = writer.getDirectContentUnder();

                BigDecimal earnings1 = calculateEarnings(fp.getPackageDeliveryWithTypeOne(), new BigDecimal(priceForTypeOne));
                BigDecimal earnings2 = calculateEarnings(fp.getPackageDeliveryWithTypeTwo(), new BigDecimal(priceForTypeTwo));
                BigDecimal earnings3 = calculateEarnings(fp.getPackageDeliveryWithTypeThree(), new BigDecimal(priceForTypeThree));
                BigDecimal earnings4 = calculateEarnings(fp.getPackageDeliveryWithTypeFour(), new BigDecimal(priceForTypeFour));
                BigDecimal sum = earnings1.add(earnings2).add(earnings3).add(earnings4);

                document.add(new Paragraph(fp.getId() + " " + fp.getName() + " " + fp.getLastName() + " : " + sum + " zł",
                        FontFactory.getFont(FontFactory.HELVETICA, "CP1250", 12, Font.NORMAL)));
            } catch (DocumentException e1) {
                e1.printStackTrace();
            }
        }
        document.close();
        writer.close();
    }
}
