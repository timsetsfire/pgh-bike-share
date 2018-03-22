## PGH Bike Share Data.

Create a Neo4j graph database based on the Pittsburgh Bike Share data.  

### Requirements

Assumes you have installed Neo4j release 3.2.3 and Java 1.8.  Make sure environment variable NEO4J_HOME is set to where you install neo4j.

### Usage

From bash or git bash

run `./GetData.sh` to download the last 9 quarters worth of data from [PGH Bike Share](http://pghbikeshare.org).

run `./CleanData.sh` to prepare data for Neo4j.

run `./neo4jLoad.sh` to create bikegraph.db.  If NEO4J_HOME is not set, you will most likely have to update this file to point at your Neo4j install.
Once the process is complete drop bikegraph.db into $NEO4J_HOME/data/database and change the conf file accordingly.

### Note

This process uses neo4j-import.  Please note that this has been deprecated. 

### Notebook examples

* `pgh bike data.ipynb`
* `Spectral Clustering Attempt.ipynb`

`Spectral Clustering.ipynb` makes use of [gmaps](https://github.com/pbugnion/gmaps), which is a plugin for for including interactive Google maps in the IPython Notebook.

To install gmaps, run the following 

`conda install -c conda-forge gmaps`

`jupyter nbextension enable --py --sys-prefix widgetsnbextension`

`jupyter nbextension enable --py --sys-prefix gmaps`

Also - you will need to create an api key for gmaps. It is easy just follow the [instructions](https://console.developers.google.com/flows/enableapi?apiid=maps_backend,geocoding_backend,directions_backend,distance_matrix_backend,elevation_backend&keyType=CLIENT_SIDE&reusekey=true).
The notebook assumes that the api key is available via the environment variable `GOOGLE_API_KEY`.  The `gmap` documenation has clearn instructions. 
