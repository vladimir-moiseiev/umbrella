package com.teamdev.umbrella.app.parsing;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.teamdev.umbrella.app.KsRow;
import com.teamdev.umbrella.app.Parser;
import com.teamdev.umbrella.app.TriolanRow;
import com.teamdev.umbrella.app.VolyaRow;
import com.teamdev.umbrella.app.dbimport.DatabaseImporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.cellprocessor.ift.CellProcessor;

import javax.inject.Inject;
import java.io.File;
import java.util.List;

@Component
public class ProviderParser {
    @Inject
    DatabaseImporter databaseImporter;

    @Value("${ks.folder}")
    private String ksFolder;
    @Value("${ks.encoding}")
    private String ksEncoding;
    @Value("${ks.startFrom}")
    private long ksStartFrom;

    @Value("${triolan.folder}")
    private String triolanFolder;
    @Value("${triolan.encoding}")
    private String triolanEncoding;
    @Value("${triolan.startFrom}")
    private long triolanStartFrom;

    @Value("${volya.folder}")
    private String volyaFolder;
    @Value("${volya.encoding}")
    private String volyaEncoding;
    @Value("${volya.startFrom}")
    private long volyaStartFrom;

    public ProviderParser() {
    }

    @Transactional
    public void parse() {
        List<KsRow> ksRows = parseKs(this.ksFolder, ParserProperties.KS_PROCESSORS, ParserProperties.KS_FIELD_MAPPINGS);
        List<TriolanRow> triolanRows = parseTriolan(this.triolanFolder, ParserProperties.TRIOLAN_PROCESSORS, ParserProperties.TRIOLAN_FIELD_MAPPINGS);
        List<VolyaRow> volyaRows = parseVolya(this.volyaFolder, ParserProperties.VOLYA_PROCESSORS, ParserProperties.VOLYA_FIELD_MAPPINGS);


        databaseImporter.initFromDb();
        databaseImporter.importKsToDatabase(ksRows);
        databaseImporter.importTriolanToDatabase(triolanRows);
        databaseImporter.importVolyaToDatabase(volyaRows);
        int x = 1;
    }


    private List<VolyaRow> parseVolya(String folderName, CellProcessor[] processors, String[] fieldMappings) {

        List<VolyaRow> result = Lists.newLinkedList();

        File folder = new File(folderName);
        if(!folder.isDirectory()) {
            throw new RuntimeException(folderName + " is not a directory");
        }

        File[] files = folder.listFiles();
        if(files == null) {
            return result;
        }
        for (final File fileEntry : files) {
            final String volyaCity = Parser.parseVolyaCity(fileEntry, ParserProperties.VOLYA_CITY_PROCESSORS, ParserProperties.VOLYA_CITY_FIELD_MAPPINGS, volyaEncoding);
            List<VolyaRow> parseResults = Parser.parse(fileEntry, processors, fieldMappings, VolyaRow.class, volyaEncoding, volyaStartFrom);

            result.addAll(Lists.transform(parseResults, new Function<VolyaRow, VolyaRow>() {
                @Override
                public VolyaRow apply(VolyaRow input) {
                    input.setCity(volyaCity);
                    return input;
                }
            }));
        }
        return result;
    }

    private List<TriolanRow> parseTriolan(String folderName, CellProcessor[] processors, String[] fieldMappings) {
        List<TriolanRow> parseResult = Lists.newLinkedList();

        File folder = new File(folderName);
        if(!folder.isDirectory()) {
            throw new RuntimeException(folderName + " is not a directory");
        }

        File[] files = folder.listFiles();
        if(files == null) {
            return parseResult;
        }
        for (final File fileEntry : files) {
            parseResult.addAll(Parser.parse(fileEntry, processors, fieldMappings,TriolanRow.class, triolanEncoding,triolanStartFrom));
        }
        return parseResult;
    }

    private List<KsRow>  parseKs(String folderName, CellProcessor[] processors, String[] fieldMappings) {
        List<KsRow> parseResult = Lists.newLinkedList();

        File folder = new File(folderName);
        if(!folder.isDirectory()) {
            throw new RuntimeException(folderName + " is not a directory");
        }

        File[] files = folder.listFiles();
        if(files == null) {
            return parseResult;
        }
        for (final File fileEntry : files) {
            parseResult.addAll(Parser.parse(fileEntry, processors, fieldMappings,KsRow.class, ksEncoding, ksStartFrom));
        }
        return parseResult;
    }
}
