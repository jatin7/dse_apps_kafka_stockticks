---
date: 2017-09-07T22:35:05-04:00
title: Kafka Consumer
menu:
  main:
      parent: Kafka to DSE
      identifier: Kafka Consumer
      weight: 202
---

## Dependencies

The Kafka Consumer is built using the datastax java driver to write to DSE as it reads from the queue.

## Description

The DSE Kafka Consumer is a Java application that reads off the Kafka queue andwrites it to C*.

For simple applications without much business logic and without aggregation requirements, a Kafka producer like this one is a good option.

For more advanced streaming analytics appilcations, DSE Streaming Analytics is the right approach to process data off a queue.
