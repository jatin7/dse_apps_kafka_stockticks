use datastax_tickdata_demo; 

truncate tick_data;
drop table tick_data;

truncate last_tick_data;
drop table last_tick_data;

drop keyspace datastax_tickdata_demo;
