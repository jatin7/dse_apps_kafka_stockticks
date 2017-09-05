package com.datastax.tickdata.producer;

import com.datastax.tickdata.utils.TickData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TickGenerator {

    static final Logger log = LoggerFactory.getLogger("TickGenerator");

    private long TOTAL_TICKS = 0;

    private List<TickValue> tickValueList = new ArrayList<TickValue>();

    public TickGenerator(HashMap<String,Double> exchangeSymbols) {
        int count = 1;
        Iterator<Map.Entry<String, Double>> symbolIterator = exchangeSymbols.entrySet().iterator();
        while (symbolIterator.hasNext()){
            Map.Entry<String, Double> symbol = symbolIterator.next();
            tickValueList.add(new TickValue(symbol.getKey(), symbol.getValue()));
        }
    }

    public long getTicksGenerated(){
        return TOTAL_TICKS;
    }

    public void generatorTicks(BlockingQueue<List<TickData>> queueTickData, long noOfTicks) {

        List<TickData> flusher = new ArrayList<TickData>();

        for (int i = 0; i < noOfTicks; i++) {

            TickValue tickValue = getTickValueRandom();
            flusher.add(new TickData(tickValue.tickSymbol, tickValue.value));

            TOTAL_TICKS++;

            if (i % 20 == 0) {
                try {
                    queueTickData.put(new ArrayList<TickData>(flusher));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                flusher.clear();
            }

            if (i % 10000 == 0){
                sleepMillis(10);
            }
        }
    }

    private void sleepMillis(int i) {
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    TickValue getTickValueRandom() {
        TickValue tickValue = tickValueList.get((int) (Math.random() * tickValueList.size()));
        tickValue.value = this.createRandomValue(tickValue.value);
        return tickValue;
    }

    class TickValue implements Serializable {
        String tickSymbol;
        double value;

        public TickValue(String tickSymbol, double value) {
            super();
            this.tickSymbol = tickSymbol;
            this.value = value;
        }
    }

    private double createRandomValue(double lastValue) {

        double up = Math.random() * 2;
        double percentMove = (Math.random() * 1.0) / 100;

        if (up < 1) {
            lastValue -= percentMove*lastValue;
        } else {
            lastValue += percentMove*lastValue;
        }

        return lastValue;
    }

}
