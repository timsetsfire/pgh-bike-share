## Files used to build Neo4j bikeGraph.db

* vertices.csv - Station information included Station ID, Station Name, number of racks, latitude and longitude
* edges-markov.csv - Start station, end station, weight (number of edges from start to end), degree - out degree for start station, i.e., number of edges coming out of start station.  This can be used to construct transition matrix
* edges-trips.csv - trip information, including start station id,trip id, bike id, start time (in millis), stop time (in millis), and end station id
