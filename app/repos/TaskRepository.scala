package repos

import model.{Task, TaskForm}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import java.sql.Timestamp
import javax.inject.{Inject, Singleton}
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class TaskRepository @Inject() (dbConfigProvider: DatabaseConfigProvider)(implicit ec: ExecutionContext) {

  protected val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  class TaskTable(tag: Tag) extends Table[Task](tag, "TASKS") {

    def id            = column[Int]("id", O.PrimaryKey, O.AutoInc)
    def title         = column[String]("title")
    def description   = column[String]("description")
    def maybeDateDone = column[Option[Timestamp]]("dateCompleted")

    def * = (id.?, title, description, maybeDateDone) <> (Task.tupled, Task.unapply)

  }

  val tasks = TableQuery[TaskTable]

  def getAll: Future[Seq[Task]]           = db.run(tasks.result)
  def find(id: Int): Future[Option[Task]] = db.run(tasks.filter(_.id === id).result.headOption)

  def create(form: TaskForm): Future[Task] =
    db.run(tasks returning tasks.map(_.id) into ((x, id) => x.copy(id = Some(id))) += new Task(form))

  def delete(id: Int): Future[Int] = db.run(tasks.filter(_.id === id).delete)

  def update(id: Int, form: TaskForm): Future[Int] = db.run((for {
    t <- tasks if t.id === id
  } yield (t.title, t.description)).update(form.title, form.description))

}
