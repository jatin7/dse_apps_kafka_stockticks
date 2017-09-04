package com.datastax.tickdata.utils;

import com.datastax.tickdata.utils.TickData;
import com.opencsv.CSVReader;
import com.opencsv.ICSVParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ExchangeUtils {

    private static final Logger log = LoggerFactory.getLogger("ExchangeUtils");

    private static final CharSequence EXCHANGEDATA = "exchangedata";

    public static List<String> getExchangeData() {

        List<String> allExchangeSymbols = new ArrayList<>();

        // Process all the files from the csv directory
        File cvsDir = new File(".", "src/main/resources/csv");

        File[] files = cvsDir.listFiles(new FileFilter() {
            public boolean accept(File file) {
                return file.isFile();
            }
        });

        for (File file : files) {
            try {
                if (file.getName().contains(EXCHANGEDATA)) {
                    allExchangeSymbols.addAll(getExchangeData(file));
                }

            } catch (FileNotFoundException e) {
                log.warn("Could not process file : " + file.getAbsolutePath(), e);
            } catch (IOException e) {
                log.warn("Could not process file : " + file.getAbsolutePath(), e);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return allExchangeSymbols;
    }

    private static List<String> getExchangeData(File file) throws IOException, InterruptedException {

        CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()), ICSVParser.DEFAULT_SEPARATOR,
                ICSVParser.DEFAULT_QUOTE_CHARACTER, 1);
        String[] items = null;
        List<String> exchangeItems = new ArrayList<>();

        List<TickData> list = new ArrayList<>();

        while ((items = reader.readNext()) != null) {
            String exchange = items[0].trim();
            String symbol = items[1].trim();

            exchangeItems.add(exchange + "-" + symbol);
        }

        reader.close();
        return exchangeItems;
    }
}
