package models

import com.sksamuel.avro4s.{AvroProp, AvroSchema, RecordFormat}
import org.apache.avro.Schema

import java.util.UUID

@AvroProp("producer", "producer-user")
case class User(
                userId: UUID,
                name: String
                )

object User {

  implicit val schema: Schema = AvroSchema[User]
  implicit val recordFormat: RecordFormat[User] = RecordFormat[User]
}




