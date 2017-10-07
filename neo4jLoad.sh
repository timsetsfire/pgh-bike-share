#!/bin/bash
## in windows, you will likely need to add file extension to neo4j-import
## i.e., neo4j-import.bat
$HOME/Desktop/neo4j/bin/neo4j-import --into bikegraph.db \
                 --nodes:Station ./data/processed/vertices.csv \
                 --relationships:TRANSITION ./data/processed/edges-markov.csv  \
                 --relationships:TRIP_TO ./data/processed/edges-trips.csv
