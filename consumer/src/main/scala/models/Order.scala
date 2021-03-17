package models

import com.sksamuel.avro4s.{AvroSchema, RecordFormat}
import org.apache.avro.Schema

import java.util.UUID

case class Order(
                  oderId: UUID,
                  itemId: UUID,
                  amount: Double
                )


object Order {

  implicit val schema: Schema = AvroSchema[Order]
  implicit val recordFormat: RecordFormat[Order] = RecordFormat[Order]
}