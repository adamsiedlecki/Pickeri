package pl.adamsiedlecki.Pickeri.tools.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitTypeService;
import pl.adamsiedlecki.Pickeri.tools.qr.QRCodeWriterTool;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

public class PickersToPdfWriter {

    private static final Logger log = LoggerFactory.getLogger(PickersToPdfWriter.class);

    public static void writeWithQR(List<FruitPicker> fruitPickers, String pathToFile, Environment env) {

        PdfCreator pdfCreator = new PdfCreator();
        DocumentAndPdfWriter documentAndPdfWriter = pdfCreator.getBasics(pathToFile,
                env.getProperty("pickeri.employees.list") + LocalDate.now());
        Document document = documentAndPdfWriter.getDocument();
        PdfWriter pdfWriter = documentAndPdfWriter.getWriter();

        for (FruitPicker fp : fruitPickers) {
            File qrFile = QRCodeWriterTool.encode(fp.getId(), fp.getName(), fp.getLastName(), "src\\main\\resources\\qr_codes");
            try {
                Image image = Image.getInstance(qrFile.getAbsolutePath());
                PdfContentByte cb = pdfWriter.getDirectContentUnder();
                document.add(QrWithCaptionMaker.getCaptionedImage(cb, image, fp.getId() + " " + fp.getName() + " " + fp.getLastName()));
            } catch (DocumentException | IOException e) {
                log.error("Write QR - Document Exception or IOException");
            }
        }
        documentAndPdfWriter.getDocument().close();
        documentAndPdfWriter.getWriter().close();
    }

    public static void writeWithoutQR(List<FruitPicker> fruitPickers, String pathToFile, Environment env) {

        PdfCreator pdfCreator = new PdfCreator();
        DocumentAndPdfWriter documentAndPdfWriter = pdfCreator.getBasics(pathToFile,
                env.getProperty("pickeri.employees.list") + LocalDate.now());
        Document document = documentAndPdfWriter.getDocument();
        PdfWriter writer = documentAndPdfWriter.getWriter();

        for (FruitPicker fp : fruitPickers) {
            try {
                PdfContentByte cb = writer.getDirectContentUnder();
                document.add(new Paragraph(fp.getId() + " " + fp.getName() + " " + fp.getLastName(),
                        FontFactory.getFont(FontFactory.HELVETICA, "CP1250", 12, Font.NORMAL)));
            } catch (DocumentException e1) {
                log.error("Write without QR - DocumentException");
            }
        }
        document.close();
        writer.close();
    }

    public static void writeRaport(List<FruitPicker> fruitPickers, String pathToFile, FruitTypeService fruitTypeService, Environment env) {

        PdfCreator pdfCreator = new PdfCreator();
        DocumentAndPdfWriter documentAndPdfWriter = pdfCreator.getBasics(pathToFile,
                env.getProperty("employee.raport.title") + LocalDate.now());
        Document document = documentAndPdfWriter.getDocument();
        PdfWriter writer = documentAndPdfWriter.getWriter();

            Font font = FontFactory.getFont(FontFactory.HELVETICA, "CP1250", 10, Font.NORMAL);
            float[] pointColumnWidths = {100};
            if (fruitTypeService.getTypeAmount() == 0) {
                pointColumnWidths = new float[]{90F, 190F, 180F, 180F, 180F};
            }
            if (fruitTypeService.getTypeAmount() == 1) {
                pointColumnWidths = new float[]{90F, 190F, 180F, 180F, 180F, 180F, 180F};
            }
            if (fruitTypeService.getTypeAmount() == 2) {
                pointColumnWidths = new float[]{90F, 190F, 180F, 180F, 220F, 220F, 220F, 220F, 180F};
            }
            if (fruitTypeService.getTypeAmount() == 3) {
                pointColumnWidths = new float[]{90F, 190F, 180F, 180F, 180F, 180F, 180F, 180F, 180F, 180F, 180F};
                font.setSize(9);
            }
            if (fruitTypeService.getTypeAmount() == 4) {
                pointColumnWidths = new float[]{85F, 190F, 190F, 190F, 190F, 190F, 190F, 190F, 190F, 190F, 185F, 185F, 180F};
                font.setSize(7);
            }
            document.setMargins(10F, 10F, 70F, 10F);
            PdfPTable table = new PdfPTable(pointColumnWidths);
            table.addCell(new Phrase(env.getProperty("id.column"), font));
            table.addCell(new Phrase(env.getProperty("name.and.surname.column"), font));
            table.addCell(new Phrase(env.getProperty("work.time.raport"), font));
            table.addCell(new Phrase(env.getProperty("packages.sum.raport"), font));
            table.addCell(new Phrase(env.getProperty("weight.sum.raport"), font));
            for (int i = 0; i < 4; i++) {
                if (fruitTypeService.getType(i).getName() != null) {
                    table.addCell(new Phrase(fruitTypeService.getType(i).getName() +" "+ env.getProperty("packages.unit"), font));
                    table.addCell(new Phrase(fruitTypeService.getType(i).getName() +" "+ env.getProperty("weight.unit"), font));
                }
            }
            for (FruitPicker fp : fruitPickers) {

                List<String> amountInfo1 = (getTypeInfoToString(fruitTypeService, fp, 0));
                List<String> amountInfo2 = (getTypeInfoToString(fruitTypeService, fp, 1));
                List<String> amountInfo3 = (getTypeInfoToString(fruitTypeService, fp, 2));
                List<String> amountInfo4 = (getTypeInfoToString(fruitTypeService, fp, 3));

                table.addCell(new Phrase(Long.toString(fp.getId()), font));
                table.addCell(new Phrase(fp.getName() + " " + fp.getLastName(), font));
                table.addCell(new Phrase(fp.getWorkTimeHours(), font));
                table.addCell(new Phrase(Long.toString(fp.getPackageDeliveryAmount()), font));
                table.addCell(new Phrase(fp.getWeightSumKgPlainText(), font));
                if (amountInfo1.size() == 2) {
                    table.addCell(new Phrase(amountInfo1.get(0), font));
                    table.addCell(new Phrase(amountInfo1.get(1), font));
                }
                if (amountInfo2.size() == 2) {
                    table.addCell(new Phrase(amountInfo2.get(0), font));
                    table.addCell(new Phrase(amountInfo2.get(1), font));
                }
                if (amountInfo3.size() == 2) {
                    table.addCell(new Phrase(amountInfo3.get(0), font));
                    table.addCell(new Phrase(amountInfo3.get(1), font));
                }
                if (amountInfo4.size() == 2) {
                    table.addCell(new Phrase(amountInfo4.get(0), font));
                    table.addCell(new Phrase(amountInfo4.get(1), font));
                }
            }
            try {
                document.add(table);
            } catch (DocumentException e) {
                log.error(" Raport - DocumentException");
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

    public static void writeEarningsRaportByKg(List<FruitPicker> fruitPickers, String pathToFile, String priceForTypeOne,
                                               String priceForTypeTwo, String priceForTypeThree, String priceForTypeFour,
                                               boolean includeFundsPaid, Environment env) {

        PdfCreator pdfCreator = new PdfCreator();
        DocumentAndPdfWriter documentAndPdfWriter = pdfCreator.getBasics(pathToFile,
                env.getProperty("employees.earnings.based.on.kg") + LocalDate.now());
        Document document = documentAndPdfWriter.getDocument();
        PdfWriter writer = documentAndPdfWriter.getWriter();

        for (FruitPicker fp : fruitPickers) {
            try {
                PdfContentByte cb = writer.getDirectContentUnder();

                BigDecimal earnings1 = calculateEarnings(fp.getWeightWithTypeOne(), new BigDecimal(priceForTypeOne));
                BigDecimal earnings2 = calculateEarnings(fp.getWeightWithTypeTwo(), new BigDecimal(priceForTypeTwo));
                BigDecimal earnings3 = calculateEarnings(fp.getWeightWithTypeThree(), new BigDecimal(priceForTypeThree));
                BigDecimal earnings4 = calculateEarnings(fp.getWeightWithTypeFour(), new BigDecimal(priceForTypeFour));
                BigDecimal sum = earnings1.add(earnings2).add(earnings3).add(earnings4);

                if (includeFundsPaid) {
                    sum = sum.subtract(fp.getFundsPaid());
                }
                document.add(new Paragraph(fp.getId() + " " + fp.getName() + " " + fp.getLastName() + " : " + sum + " zł",
                        FontFactory.getFont(FontFactory.HELVETICA, "CP1250", 12, Font.NORMAL)));
            } catch (DocumentException e1) {
                log.error("Raport - DocumentException");
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

    public static void writeEarningsRaportByPackages(List<FruitPicker> fruitPickers, String pathToFile, String priceForTypeOne,
                                                     String priceForTypeTwo, String priceForTypeThree, String priceForTypeFour,
                                                     boolean includeFundsPaid, Environment env) {

        PdfCreator pdfCreator = new PdfCreator();
        DocumentAndPdfWriter documentAndPdfWriter = pdfCreator.getBasics(pathToFile,
                env.getProperty("employees.earnings.based.on.packages") + LocalDate.now());
        Document document = documentAndPdfWriter.getDocument();
        PdfWriter writer = documentAndPdfWriter.getWriter();

        for (FruitPicker fp : fruitPickers) {
            try {
                PdfContentByte cb = writer.getDirectContentUnder();

                BigDecimal earnings1 = calculateEarnings(fp.getPackageDeliveryWithTypeOne(), new BigDecimal(priceForTypeOne));
                BigDecimal earnings2 = calculateEarnings(fp.getPackageDeliveryWithTypeTwo(), new BigDecimal(priceForTypeTwo));
                BigDecimal earnings3 = calculateEarnings(fp.getPackageDeliveryWithTypeThree(), new BigDecimal(priceForTypeThree));
                BigDecimal earnings4 = calculateEarnings(fp.getPackageDeliveryWithTypeFour(), new BigDecimal(priceForTypeFour));
                BigDecimal sum = earnings1.add(earnings2).add(earnings3).add(earnings4);

                if (includeFundsPaid) {
                    sum = sum.subtract(fp.getFundsPaid());
                }

                document.add(new Paragraph(fp.getId() + " " + fp.getName() + " " + fp.getLastName() + " : " + sum + " zł",
                        FontFactory.getFont(FontFactory.HELVETICA, "CP1250", 12, Font.NORMAL)));
            } catch (DocumentException e1) {
                log.error("Raport - DocumentException");
            }
        }
        document.close();
        writer.close();
    }
}
