## PGH Bike Share Data.

Create a Neo4j graph database based on the Pittsburgh Bike Share data.  

### Requirements

Assumes you have installed Neo4j release 3.2.3 and Java 1.8.  Make sure environment variable NEO4J_HOME is set.

### Usage

run `./GetData.sh` to download the last 9 quarters worth of data from (PGH Bike Share)[http://pghbikeshare.org].

run `./CleanData.sh` to prepare data for Neo4j.

run `./neo4jLoad.sh` to create bikegraph.db.  If NEO4J_HOME is not set, you will most likely have to update this file to point at your Neo4j install.
Once the process is complete drop bikegraph.db into $NEO4J_HOME/data/database and change the conf file accordingly.

### Note

This process uses neo4j-import.  Please note that this has been deprecated and it is recommend to use neo4j-admin import.
