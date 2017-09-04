package com.datastax.tickdata.consumer;

import com.datastax.tickdata.producer.TickProducer;
import com.datastax.tickdata.utils.TickData;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Properties;

public class TickConsumer {
    private static final Logger log = LoggerFactory.getLogger("TickConsumer");

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "tick-group");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String,String>(props);

        consumer.subscribe(Collections.singletonList(TickProducer.TOPIC));
        TickDataDao dao = new TickDataDao(new String[]{"localhost"});
        String [] split;

        try {
            while (true) {
                ConsumerRecords<String, String> ticks = consumer.poll(100);
                for (ConsumerRecord<String, String> tick: ticks)
                {
                    log.info("topic = " + tick.topic() + " partition = " + tick.partition() +
                             " offset = " + tick.offset() + " tick key = " + tick.key() +
                             " tick value = " + tick.value(),
                    tick.topic(), tick.partition(), tick.offset(), tick.key(), tick.value());
                    split = tick.value().split(":");

                    // Serialize to Cassandra
                    try {
                        dao.insertTickData(new TickData(split[0], Double.valueOf(split[1])));
                    } catch (Exception e) {
                        log.error("Error writing to dse", e);
                    }
                }
            }
        } finally {
            consumer.close();
        }

    }
}
