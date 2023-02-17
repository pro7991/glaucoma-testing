package com.glucoma.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadExcel {

	public static Map<Integer, List<String>> readExcel(String fileLocation) throws IOException {

        Map<Integer, List<String>> data = new HashMap<>();
        FileInputStream file = new FileInputStream(new File(fileLocation));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        //increaseId(sheet);
        int i = 0;
        int coutHeader = 0;
        for (Row row : sheet) {
        	for(Cell cell: row) {
        		coutHeader ++; 
        	}
        	break;
        }
        for (Row row : sheet) {
            data.put(i, new ArrayList<String>());
            for(int c = 0; c<coutHeader; c++){
            	Cell cell = row.getCell(c, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                switch (cell.getCellType()) {
                case STRING:
                    data.get(i)
                        .add(cell.getStringCellValue());
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        data.get(i)
                            .add(cell.getDateCellValue() + "");
                    } else {
                        data.get(i)
                            .add((int)cell.getNumericCellValue() + "");
                    }
                    break;
                case BOOLEAN:
                    data.get(i)
                        .add(cell.getBooleanCellValue() + "");
                    break;
                case FORMULA:
                    data.get(i)
                        .add(cell.getStringCellValue());
//                	.add(cell.getCellFormula() + "");
                    break;
                case BLANK:
                	data.get(i)
                		.add("");
                	break;
                default:
                    data.get(i)
                        .add(" ");
                }
            }
            i++;
        }
        if (workbook != null){
        	// Save the changes
//            FileOutputStream outputStream = new FileOutputStream(new File(fileLocation));
//            workbook.write(outputStream);
            workbook.close();
        }
        return data;
    }
	
	private static void increaseId(Sheet sheet) {
		Row row = sheet.getRow(3);
		Cell cell = row.getCell(0);
		Integer numericCellValue = Integer.valueOf(cell.getStringCellValue()) + 1;
		cell.setCellValue(String.valueOf(numericCellValue));
		
	}

	public static void main(String[] args) throws IOException {
		Map<Integer, List<String>> readExcel = readExcel("C:\\Selenium\\testdata.xlsx");
		System.out.println(readExcel);
	}

}
