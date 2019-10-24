package pl.adamsiedlecki.Pickeri.tools.excel;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.springframework.core.env.Environment;
import pl.adamsiedlecki.Pickeri.entity.FruitPicker;
import pl.adamsiedlecki.Pickeri.service.FruitPickerService;
import pl.adamsiedlecki.Pickeri.service.FruitTypeService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ExcelCreator {

    public static File getEmployeesExcelRaport(List<FruitPicker> fruitPickers, String filePath, FruitTypeService fruitTypeService, Environment env) {
        try {
            String fileName = filePath;
            File file = new File(fileName);
            HSSFWorkbook workbook = new HSSFWorkbook();
            HSSFSheet sheet = workbook.createSheet(Objects.requireNonNull(env.getProperty("employee.raport.title")));
            sheet.setDefaultColumnWidth(11);

            HSSFRow rowhead = sheet.createRow((short) 0);
            rowhead.createCell(0).setCellValue(env.getProperty("id.column"));
            rowhead.createCell(1).setCellValue(env.getProperty("name.column"));
            rowhead.createCell(2).setCellValue(env.getProperty("surname.column"));
            rowhead.createCell(3).setCellValue(env.getProperty("work.time.raport"));
            rowhead.createCell(4).setCellValue(env.getProperty("packages.sum.raport"));
            rowhead.createCell(5).setCellValue(env.getProperty("weight.sum.raport"));
            addFruitTypes(fruitTypeService, rowhead,6);

            short i = 1;
            for (FruitPicker fruitPicker : fruitPickers) {
                HSSFRow row = sheet.createRow(i);
                row.createCell(0).setCellValue(fruitPicker.getId());
                row.createCell(1).setCellValue(fruitPicker.getName());
                row.createCell(2).setCellValue(fruitPicker.getLastName());
                row.createCell(3).setCellValue(fruitPicker.getWorkTimeHours().toPlainString());
                row.createCell(4).setCellValue(fruitPicker.getPackageDeliveryAmount());
                row.createCell(5).setCellValue(fruitPicker.getWeightSumKgPlainText());
                addFruitTypesData(fruitTypeService, fruitPicker, row,6);
                i++;
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

    private static void addFruitTypes(FruitTypeService fruitTypeService, HSSFRow row, int startColumn) {
        int slot = 0;
            while(slot < 4){
                if(fruitTypeService.getType(0).getName()!=null){
                    row.createCell(startColumn).setCellValue(fruitTypeService.getType(0).getName());
                }
                slot++;
                startColumn++;
            }
    }

    private static void addFruitTypesData(FruitTypeService fruitTypeService, FruitPicker fruitPicker, HSSFRow row, int startColumn) {
        int slot = 0;
            while(slot < 4){
                if(fruitTypeService.getType(0).getName()!=null){
                    if(slot==0){
                        row.createCell(startColumn).setCellValue(fruitPicker.getPackageDeliveryWithTypeOne());
                    }else if(slot==1){
                        row.createCell(startColumn).setCellValue(fruitPicker.getPackageDeliveryWithTypeTwo());
                    }else if(slot==2){
                        row.createCell(startColumn).setCellValue(fruitPicker.getPackageDeliveryWithTypeThree());
                    }else if(slot==3){
                        row.createCell(startColumn).setCellValue(fruitPicker.getPackageDeliveryWithTypeFour());
                    }
                }
                slot++;
                startColumn++;
            }
    }
}


