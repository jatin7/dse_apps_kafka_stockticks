---
date: 2017-09-07T22:35:05-04:00
title: Kafka Producer
menu:
  main:
      parent: Kafka to DSE
      identifier: Kafka Producer
      weight: 201
---

## Description

The DSE Kafka Procucer is a Java application that reads a file called `exchangedata.csv` in the `resources/csv` directory and uses the ticker symbols and initial prices to start generating random ticker data to post to Kafka.

The application will generate a new tick per symbol every thirty seconds and post it to the queue. Once the application is kicked off it will continue posting random ticks to the queue indefinitely.
