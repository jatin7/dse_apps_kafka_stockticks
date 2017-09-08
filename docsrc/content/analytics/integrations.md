---
date: 2017-09-07T22:35:05-04:00
title: Analytics Integrations
menu:
  main:
      parent: Analytics
      identifier: Analytics Integrations
      weight: 302
---

DSE Analytics can communicate natively with distributed file systems like HDFS and S3.

For the purposes of this asset we demonstrate S3 integration.

## Configuration

The following Spark configuration provides the S3 endpoint and credentials for Spark to communicate with the S3 appliance via the s3a protocol: 

```
sc.hadoopConfiguration.set("fs.s3a.endpoint", "host:port");
sc.hadoopConfiguration.set("fs.s3a.access.key", "key")
sc.hadoopConfiguration.set("fs.s3a.secret.key", "secret")
```

## Read from S3


    val s3df = spark.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").load("s3a://statestreet/full_list")

## Create Temporary Table

    spark.read.format("com.databricks.spark.csv").option("header", "true").option("inferSchema", "true").load("s3a://statestreet/full_list").registerTempTable("history")

## Join

    spark.sql("select name, sum (close*quantity) as value from history t join funds a on t.symbol = a.symbol where t.date = '2017/08/14'  group by name").show
