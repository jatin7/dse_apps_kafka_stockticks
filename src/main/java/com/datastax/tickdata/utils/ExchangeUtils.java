package com.datastax.tickdata.utils;

import com.opencsv.CSVReader;
import com.opencsv.ICSVParser;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExchangeUtils {

    private static final Logger log = LoggerFactory.getLogger("ExchangeUtils");

    private static final CharSequence EXCHANGEDATA = "exchangedata";

    public static HashMap<String,Double> getExchangeData() {

        HashMap<String, Double> allExchangeSymbols = new HashMap();

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
                    allExchangeSymbols.putAll(getExchangeData(file));
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

    private static HashMap<String, Double> getExchangeData(File file) throws IOException, InterruptedException {

        CSVReader reader = new CSVReader(new FileReader(file.getAbsolutePath()), ICSVParser.DEFAULT_SEPARATOR,
                ICSVParser.DEFAULT_QUOTE_CHARACTER, 1);
        String[] items = null;
        HashMap<String, Double> exchangeItems = new HashMap();

        List<TickData> list = new ArrayList<>();

        while ((items = reader.readNext()) != null) {
            String exchange = items[0].trim();
            String symbol = items[1].trim();
            Double value = Double.valueOf(items[2]);
            exchangeItems.put(exchange + "-" + symbol, value);
        }

        reader.close();
        return exchangeItems;
    }
}
