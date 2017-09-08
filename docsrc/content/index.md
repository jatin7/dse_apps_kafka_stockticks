---
date: 2017-05-19T21:52:39
title: DSE Graph and Streaming Stockticks
type: index
weight: 0
---

This is a guide for how to use the DSE Kafka Stocktips asset.

### Motivation

Streaming data is becoming prevalent as enterprises seek to approach near real-time visibility on their data. Systems like Kafka have emeraged as distributed queues allowing users to publish data to the queue where other users can subscribe. For simple ingests (that do not require massive parallelism or streaming analytics) a Kafka producer can be used to write the data directly into DSE.

DSE Graph allows customers to maintain large amounts of operational (constantly changing) data in a graph. However, not all data belongs in a graph; DSEs multi model capabilities allow enterprises to use the right tool for the job (polyglot persistence) all within one platform.

Once data has been ingested into DSE, it is available for querying in real time with CQL or gremlin (depending on the data type) and manipulated by DSE batch analytics.

### What is included?

This field asset includes a working application for managing investment data including account information, holdings, funds, and stock ticks. The asset includes the following DSE functionality:

* DataStax Java Driver (Kafka Provider)
* Gremlin
* CQL
* DSE Graph Analytics (Graph Frames)
* Batch analytics
* Analytics S3 integration
* DSEFS

### Business Take Aways

By using DSE Graph to maintain relationships between accounts, funds, holdings, and symbols, business users are able to visualize interactions across their client's holdings. By leveraging DSE Analytics, they are able to enhance the graph data with information stored in other mediums, increasing their ability to answer business questions.

### Technical Take Aways

For a technical deep dive, take a look at the following sections:

- DSE Kafka Producer
- Graph Analytics
- Analtics integrations
