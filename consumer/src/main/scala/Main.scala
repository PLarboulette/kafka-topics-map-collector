import models.{Order, User}
import org.apache.avro.generic.GenericData
import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}

import java.time.Duration
import java.time.temporal.ChronoUnit
import java.util
import java.util.Properties
import scala.collection.JavaConversions._

object Main extends App{

  def getProps = {
    val baseProps = new Properties()
    baseProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    baseProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer")
    baseProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroDeserializer")
    baseProps.put("schema.registry.url", "http://localhost:8081")
    baseProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest")
    baseProps
  }

  println("Hello, I'm the consumer ! :)")

  // Configure order topic
  val orderTopic = "topic-order"
  val propsOrder = getProps
  propsOrder.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group-id-v1:topic-order")
  val consumerOrder = new KafkaConsumer[String, AnyRef](propsOrder)
  consumerOrder.subscribe(util.Arrays.asList(orderTopic))

  // Configure User topic
  val userTopic = "topic-user"
  val propsUser = getProps
  propsUser.put(ConsumerConfig.GROUP_ID_CONFIG, "consumer-group-id-v1:topic-user")
  val consumerUser = new KafkaConsumer[String, AnyRef](propsUser)
  consumerUser.subscribe(util.Arrays.asList(userTopic))

  // Yeah, it’s ugly, but it’s a PoC, and PoCs never go into production anyway
  try while ( {
    true
  }) {
    val recordsOrder = consumerOrder.poll(Duration.of(5, ChronoUnit.SECONDS))
    val recordsUser = consumerUser.poll(Duration.of(5, ChronoUnit.SECONDS))

    for (record <- recordsOrder) {
      val genericData = record.value().asInstanceOf[GenericData.Record]
      val fakeServiceFiled = genericData.getSchema.getObjectProp("producer").toString
      val order = Order.recordFormat.from(genericData)
      println(order.oderId)
    }

    for (record <- recordsUser) {
      val genericData = record.value().asInstanceOf[GenericData.Record]
      val fakeServiceFiled = genericData.getSchema.getObjectProp("producer").toString
      val user = User.recordFormat.from(genericData)
      println(user.userId)
    }

  }
  finally {
    consumerOrder.close()
    consumerUser.close()
  }
}
