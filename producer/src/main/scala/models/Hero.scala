package models

import com.sksamuel.avro4s.{AvroDoc, AvroProp, AvroSchema, RecordFormat}
import org.apache.avro.Schema

@AvroDoc("Model of Hero")
@AvroProp("FakeServiceFiled", "app-1")
@AvroProp("Version", "2")
case class Hero
(
  @AvroDoc("The name of the hero") @AvroProp("CustomFieldForName", "Test") name: String,
  @AvroDoc("The age of the hero") age: Int,
  @AvroDoc("The town where the hero is living") town: String
)

object Hero {

  implicit val schema: Schema = AvroSchema[Hero]
  implicit val recordFormat: RecordFormat[Hero] = RecordFormat[Hero]
}

