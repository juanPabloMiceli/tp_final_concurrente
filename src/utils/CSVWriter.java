package src.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.ArrayList;


public class CSVWriter {
    public static <T> void writeCSV(List<T> dataList, String[] headers, String csvFile) {
        try (FileWriter fileWriter = new FileWriter(csvFile);
             CSVPrinter csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(headers))) {

            for (T data : dataList) {
                String[] record = extractFieldValues(data);
                csvPrinter.printRecord((Object[]) record);
            }

            System.out.println("CSV file generated successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static <T> String[] extractFieldValues(T data) {
        Field[] fields = data.getClass().getDeclaredFields();
        String[] values = new String[fields.length];

        try {
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                Object value = fields[i].get(data);
                values[i] = (value != null) ? value.toString() : "";
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return values;
    }

}
