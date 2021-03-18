import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient
import org.apache.avro.Schema
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.admin.AdminClient
import scala.collection.JavaConverters.asScalaSet
import scala.collection.JavaConverters.asScalaIterator
import java.util.Properties

object Main extends App {

  val props = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")

  val admin = AdminClient.create(props)

  // Get the list of topics. Yes. Amazing.
  println("TOPICS")
  // Print topic-order and topic-user
  asScalaSet(admin.listTopics().names().get()).map(println);

  println("CONSUMER GROUPS")
  val consumerGroups = asScalaIterator( admin.listConsumerGroups().all().get().iterator()).toSet
  // Print consumer-group-id-v1:topic-order (same fot user but with "user" instead of "order")
  // This groupId is defined in the consumer part (See Consumer.scala)
  // From the groupId, we can extract the topic (second part of the string, after the ":" )
  consumerGroups.map(_.groupId()).map(println)

  val registry = new CachedSchemaRegistryClient("http://localhost:8081", 100)

  // A subject is like topic-order-value (it's the topic name with -value)
  asScalaIterator(registry.getAllSubjects.iterator()).toSet.map {
      subject =>
        // For each subject, we have a schema with it's version.
        // For example, if you have dev and prod environments, you can have a subject dev-value with a schema in version 2
        // and a subject prod-value with the same schema but in version 1

        // We get the schema of the subject (the last version). For example, for the dev-value subject, we get the schema in version 2, not 1
        val metadata = registry.getLatestSchemaMetadata(subject)

        // Get all the schema information
        val schema = registry.getSchemaById(metadata.getId)

        // Get the customProp defined in the models of Producer
        val customProp = schema.rawSchema().asInstanceOf[Schema].getObjectProp("producer").toString
        // Print "producer-order" (the name of the producer)
        println(customProp)
  }

  // For one topic, we can have it's name, the consumer groups ids of the consumer which read it,
  // and it's producer thanks to the Custom prop

}
