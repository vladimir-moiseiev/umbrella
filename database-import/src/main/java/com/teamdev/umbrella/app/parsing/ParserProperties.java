package com.teamdev.umbrella.app.parsing;

import org.supercsv.cellprocessor.ift.CellProcessor;

public class ParserProperties {
    public static final TrimProcessor trim = new TrimProcessor();

    public static final CellProcessor[] KS_PROCESSORS = new CellProcessor[]{null, trim, trim, trim, null, null, trim, trim, trim, trim, null, null, trim, null, trim, null, trim, null, trim, null,
            null, null, null, null, null, null, null, null, null, null, trim, null, null, null, null, null, null, null, null, trim, null, null, null, null, null, null, null, null, null, null, null,
            trim, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
    public static final String[] KS_FIELD_MAPPINGS = new String[]{null, "lastName", "firstName", "secondName", null, null, "city", "street", "building", "apartment", null, null, "phone1", null, "phone2", null, "phone3", null, "phone4", null,
            null, null, null, null, null, null, null, null, null, null, null, null, "status", null, null, null, null, null, null, null, null, "installDate", null, null, null, null, null, null, null, null, null,
            null, null, "activityDate", null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null};
}
