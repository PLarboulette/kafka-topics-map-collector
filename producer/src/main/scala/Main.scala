import models.{Order, User}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import java.util.{Properties, UUID}

object Main extends App {

  println("Hello, I'm the producer ! :) ")

  val props = new Properties();
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer")
  props.put("schema.registry.url", "http://127.0.0.1:8081")

  val producer = new KafkaProducer[String, AnyRef](props)

  val topic = "topic-order"
  val order = Order(UUID.randomUUID(), UUID.randomUUID(), 30)
  val producerRecord = new ProducerRecord[String, AnyRef](
    topic,
    Order.recordFormat.to(order)
  )

  val f = producer.send(
    producerRecord
  ).get()

  val topicUser = "topic-user"
  val user = User(
    UUID.randomUUID(),
    "Gandalf"
  )
  val producerRecordUser = new ProducerRecord[String, AnyRef](
    topicUser,
    User.recordFormat.to(user)
  )

  val f2 = producer.send(
    producerRecordUser
  ).get()

}
