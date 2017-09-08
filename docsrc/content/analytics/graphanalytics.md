---
date: 2017-09-07T22:35:05-04:00
title: Graph Analytics
menu:
  main:
      parent: Analytics
      identifier: Graph Analytics
      weight: 301
---

## Description

To enable users to mix graph data and c* data, we can utilize DSE Graph Analytics in the form of Spark Graph Frames.

## Create a temporary table 

In order to perform a SparkSQL join against graph, we must first define a tempoarary table based on a graph frame.

### Connect to the graph

    val g = spark.dseGraph("s")

### Find and manipulate the desired DataFrame

We use the graph frame query syntax to `find` the data that we need. In this case note we are selecting accounts, the funds they own, the holdings in those funds, and finally the securities in those hodlings. Then we proceed to manipulate the resulting DataFrame by selecting only the columns we are intersted in. In this case those are the symbol off the security vertex, the name off the account vertex, adn the quantity off the holding vertex.


    val symbolsByAccount =  g.find("(account)-[owns]->(fund); (fund)-[holds]->(holding); (holding)-[has]->(sec)").filter("account.`~label` = 'account' and owns.`~label` = 'owns' and holds.`~label` = 'holds' and has.`~label` = 'asset' and sec.`~label` = 'security'").select($"sec.symbol", $"account.name", $"holding.quantity

We are now able to register the DF as a temporary table, accessible by SparkSQL.

    symbolsByAccount.registerTempTable("accounts")

## Join

Now we can proceed to join the graph `accounts` table with the tabular stocktick data stored in cassandra:

    spark.sql("select name, sum (value*quantity) as val from datastax_tickdata_demo.last_tick_data t join accounts a on t.symbol = a.symbol group by name").show

We have only walked through a simple scenario here. See the Build and Run section for the full demo.
