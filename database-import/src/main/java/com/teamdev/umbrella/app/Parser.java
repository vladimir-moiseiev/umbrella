package com.teamdev.umbrella.app;


import com.google.common.collect.Lists;
import com.teamdev.umbrella.app.parsing.NameProcessor;
import com.teamdev.umbrella.app.parsing.TrimProcessor;
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

    public static final String ENCODING = "utf8";


    public static <T> List<T> parse(File file, CellProcessor[] processors, String[] fieldMappings,Class<T> dataType) {
        LinkedList<T> result = Lists.newLinkedList();

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try (ICsvDozerBeanReader beanReader = new CsvDozerBeanReader(new InputStreamReader(fileInputStream, ENCODING), EXCEL_NORTH_EUROPE_PREFERENCE)) {

                beanReader.configureBeanMapping(dataType, fieldMappings);

                T row;
                while ((row = beanReader.read(dataType, processors)) != null) {
                    result.add(row);
                }
            }
        } catch (IOException e) {
            return result;
        }

        return  result;

    }

}
