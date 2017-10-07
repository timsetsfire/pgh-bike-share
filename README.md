## PGH Bike Share Data.

Create a Neo4j graph database based on the Pittsburgh Bike Share data.  

### Requirements

Assumes you have installed Neo4j release 3.2.3 and Java 1.8

### Usage

run `./GetData.sh` to download the last 9 quarters worth of data from (PGH Bike Share)[http://pghbikeshare.org].

run `./CleanData.sh` to prepare data for Neo4j.

run `./neo4jLoad.sh` to create bikeGraph.db.

### Note

This process uses neo4j-import.  Please note that this has been deprecated and it is recommend to use neo4j-admin import.
