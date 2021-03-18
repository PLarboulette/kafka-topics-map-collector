# Kafka topics map collector 

## Context and docs 
 
- You need a Schema Registry. I use here one the of the last version (currently), the 6.0.0. 
- I use the lib Avro4s -> [here](https://github.com/sksamuel/avro4s)
- There are many tutorials of how you can interact with Kafka (with Avro or not)  on GitHub
- Confluent documentation 
- Apache Kafka documentation 

# Why this project ? 
I want in this project build a kind of map of Kafka topics, consumers and producers. 
I try to get data from different sources, like Schema Registry or Kafka native API. 
The goal is to generate a json file containing all the information, in a special format I can use in another project. 
---> https://github.com/PLarboulette/kafka-topics-map

In this project, I use some experiments I launched in other projects. 
--> https://github.com/PLarboulette/kafka-avro-experiments

# How to run 
- `docker-compose up`
- Launch the producer : `sbt "project producer" run`. It will create the topics 
- Launch the consumer : `sbt "project consumer" run`. It will launch the two consumers and read the events 
- Launch the collector : `sbt "project collector" run`. It will get the data from the different sources and construct the dependencies between topics, consumers and producers. 
