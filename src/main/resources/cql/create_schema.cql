create keyspace datastax_tickdata_demo WITH replication = {'class': 'SimpleStrategy', 'replication_factor': 1};

use datastax_tickdata_demo;

CREATE TABLE tick_data ( 
	symbol text,
	date timestamp,
	value double,
	PRIMARY KEY (symbol, date)
) WITH CLUSTERING ORDER BY (date DESC);

CREATE TABLE tick_data.last_tick_data (
    symbol text PRIMARY KEY,
    date timestamp,
    value double
)

truncate tick_data;
