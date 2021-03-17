import models.{Hero, Town}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}
import org.apache.kafka.common.header.Header
import org.apache.kafka.common.header.internals.RecordHeader

import java.util.{Properties, UUID}

object Main extends App {

  println("Hello, I'm the producer ! :) ")

  val props = new Properties();
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer")
  props.put("schema.registry.url", "http://127.0.0.1:8081")


  def getHeaders: Iterable[Header] = List(
    new RecordHeader("serviceName", "producer".getBytes),
  )

  val producer = new KafkaProducer[String, AnyRef](props)

  val heroTopic = "hero-topic"
  val keyForHero = UUID.randomUUID().toString

  val hero = Hero("Batman", 25, "Gotham city")
  val producerRecordHero = new ProducerRecord[String, AnyRef](
    heroTopic,
    keyForHero,
    Hero.recordFormat.to(hero),
  )

  val townTopic = "town-topic"
  val keyForTown = UUID.randomUUID().toString

  val town = Town("Metropolis")
  val producerRecordTown = new ProducerRecord[String, AnyRef](
    townTopic,
    keyForTown,
    Town.recordFormat.to(town),
  )

  val f = producer.send(
    producerRecordHero
  ).get()

  println(s"Offset : ${f.offset()}")

  val f2 = producer.send(
    producerRecordTown
  ).get()

  println(s"Offset : ${f2.offset()}")

}
