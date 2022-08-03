package model

import play.api.libs.json._

import java.sql.Timestamp

case class Task(id: Option[Int], title: String, description: String, maybeDateDone: Option[Timestamp])

object Task {

  implicit val timestampFormat: Format[Timestamp] = new Format[Timestamp] {
    def writes(t: Timestamp): JsValue             = Json.toJson(t.getTime)
    def reads(json: JsValue): JsResult[Timestamp] = Json.fromJson[Long](json).map(new Timestamp(_))
  }

  implicit val taskFormat: OFormat[Task] = Json.format[Task]

  def tupled: ((Option[Int], String, String, Option[Timestamp])) => Task = (apply _).tupled

}
