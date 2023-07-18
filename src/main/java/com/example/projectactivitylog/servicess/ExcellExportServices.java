package com.example.projectactivitylog.servicess;

import com.example.projectactivitylog.dto.LogDto;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ExcellExportServices {





    public static void export(LogService logService) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Export");
        sheet.setColumnWidth(0, 2000);
        sheet.setColumnWidth(1, 10000);
        sheet.setColumnWidth(2, 10000);
        sheet.setColumnWidth(3, 3000);
        sheet.setColumnWidth(4, 3000);

        Row header = sheet.createRow(0);

        CellStyle headerStyle = workbook.createCellStyle();
        //headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        headerStyle.setFont(font);

        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("id");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("start");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(2);
        headerCell.setCellValue("stop");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(3);
        headerCell.setCellValue("project");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(4);
        headerCell.setCellValue("person");
        headerCell.setCellStyle(headerStyle);

        List<LogDto> list = new ArrayList<>(logService.getAllRecord());

        for (int i = 0; i<list.size()-1; i++) {
            Row row = sheet.createRow(i+1);
            Cell cell0 = row.createCell(0);
            cell0.setCellValue(list.get(i).getId()+"");

            Cell cell1 = row.createCell(1);
            cell1.setCellValue(list.get(i).getStart().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));

            Cell cell2 = row.createCell(2);
            cell2.setCellValue(list.get(i).getStop().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));

            Cell cell3 = row.createCell(3);
            cell3.setCellValue(list.get(i).getProject_id());

            Cell cell4 = row.createCell(4);
            cell4.setCellValue(list.get(i).getPerson_id());
        }

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.subSequence(0, path.length()-1) + "temp.xlsx";
        try{
            FileOutputStream outputStream = new FileOutputStream(fileLocation);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
