package models

import com.sksamuel.avro4s.{AvroProp, AvroSchema, RecordFormat}
import org.apache.avro.Schema

import java.util.UUID

@AvroProp("producer", "producer-order")
case class Order(
                oderId: UUID,
                itemId: UUID,
                amount: Double
                )

object Order {

  implicit val schema: Schema = AvroSchema[Order]
  implicit val recordFormat: RecordFormat[Order] = RecordFormat[Order]
}
