---
date: 2017-09-07T22:35:05-04:00
title: Diagram
menu:
  main:
      parent: Architecture
      identifier: Diagram
      weight: 402
---

## Architecture Diagram


This section details the architecture demonstrated in this Streaming Graph Stockticks field asset.

### Architecture Diagram

<div title="Architecture Diagram" align="middle">
{{< mermaid >}}
graph LR
P["Producer"]--"Pub"-->A["Kafka"]
A--"Sub"-->C["Consumer"]
C--"DataStax Java Driver"-->D["DSE Cassandra"]
G["DSE Graph"]--"Graph Frames"-->S["DSE Batch Analytics (SparkSQL Join)"]
D--"Data Frames"-->S
H["HDFS/S3"]--"Data Frames"-->S
{{< /mermaid >}}
</div>

