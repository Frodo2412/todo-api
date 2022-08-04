package controllers

import model.TaskForm
import play.api.libs.json._
import play.api.mvc._
import repos.TaskRepository

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class TaskController @Inject() (repo: TaskRepository, val controllerComponents: ControllerComponents)(implicit
    ec: ExecutionContext
) extends BaseController {

  private def parseRequest(request: Request[AnyContent]) = request.body.asJson.map { json =>
    TaskForm((json \ "title").as[String], (json \ "description").as[String])
  }

  def getAll: Action[AnyContent] = Action.async { repo.getAll.map(tasks => Ok(Json.toJson(tasks))) }

  def find(id: Int): Action[AnyContent] = Action.async {
    repo.find(id).map {
      case Some(value) => Ok(Json.toJson(value))
      case None        => NotFound
    }
  }

  def delete(id: Int): Action[AnyContent] = Action.async { repo.delete(id).map(_ => Ok) }

  def create(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    parseRequest(request)
      .map { repo.create(_).map { task => Ok(Json.toJson(task)) } }
      .getOrElse { Future.successful(BadRequest("Expecting JSON data")) }
  }

  def update(id: Int): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    parseRequest(request)
      .map {
        repo.update(id, _).map { affectedRows =>
          if (affectedRows == 0) Ok(s"$affectedRows tasks updated") else Ok("No tasks updated")
        }
      }
      .getOrElse { Future.successful(BadRequest("Expecting JSON data")) }
  }

}
