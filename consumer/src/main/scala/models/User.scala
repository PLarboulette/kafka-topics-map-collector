package models

import com.sksamuel.avro4s.{AvroSchema, RecordFormat}
import org.apache.avro.Schema

import java.util.UUID

case class User(
                 userId: UUID,
                 name: String
               )

object User {

  implicit val schema: Schema = AvroSchema[User]
  implicit val recordFormat: RecordFormat[User] = RecordFormat[User]
}