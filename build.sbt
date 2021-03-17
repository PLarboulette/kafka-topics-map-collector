
name := "kafka-topics-map-collector"

version := "0.1"

scalaVersion := "2.13.4"

val commonResolvers = Seq(
  Resolver.jcenterRepo,
  "confluent" at "https://packages.confluent.io/maven/"
)

val commonLibs = Seq(
  "org.apache.kafka" % "kafka-clients" % "2.6.0",
  "com.sksamuel.avro4s" %% "avro4s-core" % "4.0.4",
  "io.confluent" % "kafka-avro-serializer" % "6.0.1"
)

lazy val consumer = project
  .in(file("consumer"))
  .settings(
    name := "consumer",
    libraryDependencies ++= commonLibs,
    resolvers ++= commonResolvers
  )

lazy val producer = project
  .in(file("producer"))
  .settings(
    name := "producer",
    libraryDependencies ++= commonLibs,
    resolvers ++= commonResolvers
  )

lazy val collector = project
  .in(file("collector"))
  .settings(
    name := "collector",
    libraryDependencies ++= commonLibs,
    resolvers ++= commonResolvers
  )
