#!/bin/bash
$HOME/Desktop/neo4j/bin/neo4j-import --into bikegraph.db \
                 --nodes:Station ./data/processed/vertices.csv \
                 --relationships:TRANSITION ./data/processed/edges-markov.csv  \
                 --relationships:TRIP_TO ./data/processed/edges-trips.csv
