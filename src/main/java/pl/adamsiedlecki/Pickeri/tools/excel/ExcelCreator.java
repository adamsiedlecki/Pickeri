package pl.adamsiedlecki.Pickeri.tools.excel;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitTypeService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ExcelCreator {

    public static File getEmployeesExcelRaport(List<FruitPicker> fruitPickers, String pdfPath, FruitTypeService fruitTypeService, Environment env) {
        try {
            String fileName = "raport.xls";
            File file = new File(fileName);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(Objects.requireNonNull(env.getProperty("employee.raport.title")));

            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue(env.getProperty("id.column"));
            rowhead.createCell(1).setCellValue(env.getProperty("name.column"));
            rowhead.createCell(2).setCellValue(env.getProperty("surname.column"));
            rowhead.createCell(3).setCellValue(env.getProperty("work.time.raport"));
            rowhead.createCell(4).setCellValue(env.getProperty("packages.sum.raport"));
            rowhead.createCell(4).setCellValue(env.getProperty("weight.sum.raport"));

            for (FruitPicker fruitPicker : fruitPickers) {
                HSSFRow row = sheet.createRow((short) 1);
                row.createCell(0).setCellValue(fruitPicker.getId());
                row.createCell(1).setCellValue(fruitPicker.getName());
                row.createCell(2).setCellValue(fruitPicker.getLastName());
                row.createCell(3).setCellValue(fruitPicker.getWorkTimeHours().toPlainString());
                row.createCell(4).setCellValue(fruitPicker.getPackageDeliveryAmount());
                row.createCell(5).setCellValue(fruitPicker.getWeightSumKgPlainText());
            }

            FileOutputStream fileOut = new FileOutputStream(file);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new File("error");
    }

}
