#!/bin/bash

curl -s http://pghbikeshare.org/wp-content/uploads/2017/01/HealthyRideTripData2015Q2.zip > data/HealthyRideTripData2015Q2.zip
curl -s http://pghbikeshare.org/wp-content/uploads/2017/01/HealthyRideTripData2015Q3.zip > data/HealthyRideTripData2015Q3.zip
curl -s http://pghbikeshare.org/wp-content/uploads/2017/01/HealthyRideTripData2015Q4.zip > data/HealthyRideTripData2015Q4.zip
curl -s http://pghbikeshare.org/wp-content/uploads/2017/01/HealthyRideTripData2016Q1.zip > data/HealthyRideTripData2016Q1.zip
curl -s http://pghbikeshare.org/wp-content/uploads/2017/01/HealthyRideTripData2016Q2.zip > data/HealthyRideTripData2016Q2.zip
curl -s http://pghbikeshare.org/wp-content/uploads/2017/01/HealthyRideTripData2016Q3.zip > data/HealthyRideTripData2016Q3.zip
curl -s http://pghbikeshare.org/wp-content/uploads/2017/01/HealthyRideTripData2016Q4.zip > data/HealthyRideTripData2016Q4.zip
curl -s http://pghbikeshare.org/wp-content/uploads/2017/04/HealthyRideTripData2017Q1.zip > data/HealthyRideTripData2017Q1.zip
curl -s http://pghbikeshare.org/wp-content/uploads/2017/07/HealthyRideTripData2017Q2.zip > data/HealthyRideTripData2017Q2.zip

unzip -o -d data data/HealthyRideTripData2015Q2.zip && rm data/HealthyRideTripData2015Q2.zip
unzip -o -d data data/HealthyRideTripData2015Q3.zip && rm data/HealthyRideTripData2015Q3.zip
unzip -o -d data data/HealthyRideTripData2015Q4.zip && rm data/HealthyRideTripData2015Q4.zip
unzip -o -d data data/HealthyRideTripData2016Q1.zip && rm data/HealthyRideTripData2016Q1.zip
unzip -o -d data data/HealthyRideTripData2016Q2.zip && rm data/HealthyRideTripData2016Q2.zip
unzip -o -d data data/HealthyRideTripData2016Q3.zip && rm data/HealthyRideTripData2016Q3.zip
unzip -o -d data data/HealthyRideTripData2016Q4.zip && rm data/HealthyRideTripData2016Q4.zip
unzip -o -d data data/HealthyRideTripData2017Q1.zip && rm data/HealthyRideTripData2017Q1.zip
unzip -o -d data data/HealthyRideTripData2017Q2.zip && rm data/HealthyRideTripData2017Q2.zip
