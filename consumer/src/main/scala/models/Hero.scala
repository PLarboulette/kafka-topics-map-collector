package models

import com.sksamuel.avro4s.{AvroDoc, AvroProp, AvroSchema, RecordFormat}
import org.apache.avro.Schema

case class Hero
(
  name: String
)

object Hero {

  implicit val schema: Schema = AvroSchema[Hero]
  implicit val recordFormat: RecordFormat[Hero] = RecordFormat[Hero]
}

