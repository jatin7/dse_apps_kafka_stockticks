package com.datastax.tickdata.producer;

import java.util.Properties;

import com.datastax.tickdata.utils.ExchangeUtils;
import com.datastax.tickdata.utils.PropertyHelper;
import com.datastax.tickdata.producer.TickGenerator.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TickProducer {

    private static Logger log = LoggerFactory.getLogger("TickProducer");

    public static final String TOPIC = "tick-stream";

    public static void main(String [] args) {

        long events = Long.parseLong(PropertyHelper.getProperty("noOfTicks", "14"));
        //long events = Long.parseLong(PropertyHelper.getProperty("noOfTicks", "31"));

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        props.put("acks", "1");

        final KafkaProducer<String, String> producer = new KafkaProducer<>(props);
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() {
                producer.close();
            }
        }, "Shutdown-thread"));

        TickGenerator generator = new TickGenerator(ExchangeUtils.getExchangeData());

        while (true) {

            for (int i = 0; i < events-1; i++) {
                TickValue tickValueRandom = generator.getTickValueRandom(i);
                String s = tickValueRandom.tickSymbol + ':' + tickValueRandom.value;
                try {
                    producer.send(new ProducerRecord<String, String>(TOPIC, null, s));
                } catch (Exception e) {
                    log.error("Error adding to topic", e);
                }
            }
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
