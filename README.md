# DSE Kafka Stockticks

Borrowed heavily from [Patrick Callaghan's datastax-kafka-example](https://github.com/PatrickCallaghan/datastax-kafka-example).

The goal of this version is to have a producer generate stock tick data and write to the `tick-stream` topic and then
have a consumer read from the topic and write the tick data to a Cassandra table.

### Setup
First start Kafka and add the topic tick-stream following [these basic instructions](http://kafka.apache.org/quickstart).

Download and start DSE.

Setup the Cassandra schema by running the SchemaSetup class.
**Note:** the keyspace replication settings use `SimpleStrategy`.
Modify as needed.

### Running
Create the schema in DSE

    mvn clean compile exec:java -Dexec.mainClass="com.datastax.demo.SchemaSetup"

Start the TickProducer with the desired number of events.

    mvn clean compile exec:java -Dexec.mainClass="com.datastax.tickdata.producer.TickProducer"

Start the TickConsumer.

    mvn clean compile exec:java -Dexec.mainClass="com.datastax.tickdata.consumer.TickConsumer"

### Verification
You can run the following in `cqlsh`:

```$xslt
select * from datastax_tickdata_demo.tick_data ;
```
