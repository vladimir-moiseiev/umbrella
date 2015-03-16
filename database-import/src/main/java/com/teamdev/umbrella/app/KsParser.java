package com.teamdev.umbrella.app;


import com.google.common.collect.Lists;
import com.teamdev.umbrella.app.parsing.NameProcessor;
import com.teamdev.umbrella.app.parsing.TrimProcessor;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.dozer.CsvDozerBeanReader;
import org.supercsv.io.dozer.ICsvDozerBeanReader;
import org.supercsv.prefs.CsvPreference;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

import static org.supercsv.prefs.CsvPreference.EXCEL_NORTH_EUROPE_PREFERENCE;

public class KsParser {

    public static final String ENCODING = "utf8";


    public static List<KsRow> parse(File file) {
        LinkedList<KsRow> result = Lists.newLinkedList();

        NameProcessor nameProcessor = new NameProcessor();
        TrimProcessor trim = new TrimProcessor();

        final CellProcessor[] processors = {null,trim,trim,trim,null,null,trim,trim,trim,trim,null,null,trim,null,trim,null,trim,null,trim,null,
                null,null,null,null,null,null,null,null,null,null,trim,null,null,null,null,null,null,null,null,trim,null,null,null,null,null,null,null,null,null,null,null,
                trim,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            try (ICsvDozerBeanReader beanReader = new CsvDozerBeanReader(new InputStreamReader(fileInputStream, ENCODING), EXCEL_NORTH_EUROPE_PREFERENCE)) {
                final String[] fieldMappings = {null,"lastName","firstName","secondName",null,null,"city","street","building","apartment",null,null,"phone1",null,"phone2",null,"phone3",null,"phone4",null,
                        null,null,null,null,null,null,null,null,null,null,null,null,"status",null,null,null,null,null,null,null,null,"installDate",null,null,null,null,null,null,null,null,null,
                        null,null,"activityDate",null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null};
                beanReader.configureBeanMapping(KsRow.class, fieldMappings);

                KsRow row;
                while ((row = beanReader.read(KsRow.class, processors)) != null) {
                    result.add(row);
                }
            }
        } catch (IOException e) {
            return result;
        }

        return  result;

    }

}
