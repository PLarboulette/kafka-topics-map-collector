package models

import com.sksamuel.avro4s.{AvroDoc, AvroProp, AvroSchema, RecordFormat}
import org.apache.avro.Schema

case class Town(
               name: String
               )

object Town {

  implicit val schema: Schema = AvroSchema[Town]
  implicit val recordFormat: RecordFormat[Town] = RecordFormat[Town]
}
