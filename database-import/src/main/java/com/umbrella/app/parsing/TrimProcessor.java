package com.umbrella.app.parsing;

import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.util.CsvContext;

public class TrimProcessor implements CellProcessor {
    @Override
    public Object execute(Object value, CsvContext context) {
        String trimmed = value == null ? "" : String.valueOf(value).trim().replace("=", "").toLowerCase();
        return trimmed;
    }
}
