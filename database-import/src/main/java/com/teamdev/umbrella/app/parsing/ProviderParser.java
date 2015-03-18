package com.teamdev.umbrella.app.parsing;

import com.google.common.collect.Lists;
import com.teamdev.umbrella.app.*;
import com.teamdev.umbrella.app.dbimport.DatabaseImporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.ift.CellProcessor;

import javax.inject.Inject;
import java.io.File;
import java.util.List;

@Component
public class ProviderParser {

    public static final String KS_ENCODING = "utf8";
    public static final String TRIOLAN_ENCODING = "cp866";

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

    public void parse() {
        //List<KsRow> ksRows = parseKs(this.ksFolder, ParserProperties.KS_PROCESSORS, ParserProperties.KS_FIELD_MAPPINGS);
        List<TriolanRow> triolanRows = parseTriolan(this.triolanFolder, ParserProperties.TRIOLAN_PROCESSORS, ParserProperties.TRIOLAN_FIELD_MAPPINGS);
        //parseVolya(this.volyaFolder, ParserProperties.KS_PROCESSORS, ParserProperties.KS_FIELD_MAPPINGS);

        //databaseImporter.importKsToDatabase(ksRows);
        databaseImporter.importTriolanToDatabase(triolanRows);
    }


    private List<VolyaRow> parseVolya(String folderName, CellProcessor[] processors, String[] fieldMappings) {

        List<VolyaRow> parseResult = Lists.newLinkedList();

        File folder = new File(folderName);
        if(!folder.isDirectory()) {
            throw new RuntimeException(folderName + " is not a directory");
        }

        File[] files = folder.listFiles();
        if(files == null) {
            return parseResult;
        }
        for (final File fileEntry : files) {
            parseResult.addAll(Parser.parse(fileEntry, processors, fieldMappings,VolyaRow.class, volyaEncoding,volyaStartFrom));
        }
        return parseResult;
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
