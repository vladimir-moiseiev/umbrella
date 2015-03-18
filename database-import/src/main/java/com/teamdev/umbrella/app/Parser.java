package com.teamdev.umbrella.app;


import com.google.common.collect.Lists;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.dozer.CsvDozerBeanReader;
import org.supercsv.io.dozer.ICsvDozerBeanReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import static org.supercsv.prefs.CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE;

public class Parser {


    public static <T> List<T> parse(File file, CellProcessor[] processors, String[] fieldMappings, Class<T> dataType, String encoding, long startFrom) {
        LinkedList<T> result = Lists.newLinkedList();

        long count = 0;
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try (ICsvDozerBeanReader beanReader = new CsvDozerBeanReader(new InputStreamReader(fileInputStream, encoding), EXCEL_NORTH_EUROPE_PREFERENCE)) {

                beanReader.configureBeanMapping(dataType, fieldMappings);

                T row;
                while ((row = beanReader.read(dataType, processors)) != null) {
                    if(count >= startFrom - 1) {
                        result.add(row);
                    }
                    else {
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            return result;
        }

        return  result;

    }

}
