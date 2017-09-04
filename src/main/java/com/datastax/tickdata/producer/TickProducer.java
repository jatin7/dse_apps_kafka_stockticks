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

        long events = Long.parseLong(PropertyHelper.getProperty("noOfTicks", "10"));

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", StringSerializer.class.getName());
        props.put("value.serializer", StringSerializer.class.getName());
        props.put("acks", "1");
        KafkaProducer<String, String> producer = new KafkaProducer<>(props);

        TickGenerator generator = new TickGenerator(ExchangeUtils.getExchangeData());

        for (long i= 0; i < events; i++) {
            TickValue tickValueRandom = generator.getTickValueRandom();
            String s = tickValueRandom.tickSymbol + ':' + tickValueRandom.value;
            try {
                producer.send(new ProducerRecord<String, String>(TOPIC, null, s));
            } catch (Exception e) {
                log.error("Error adding to topic", e);
            }
        }
        producer.close();
    }
}
