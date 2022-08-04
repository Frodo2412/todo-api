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

  def getAll: Action[AnyContent] = Action.async { repo.getAll.map(tasks => Ok(Json.toJson(tasks))) }

  def create(): Action[AnyContent] = Action.async { implicit request: Request[AnyContent] =>
    request.body.asJson
      .map { json =>
        val form = TaskForm((json \ "title").as[String], (json \ "description").as[String])
        repo.create(form).map { task => Ok(Json.toJson(task)) }
      }
      .getOrElse {
        Future.successful(BadRequest("Expecting JSON data"))
      }
  }

  def delete(id: Int): Action[AnyContent] = Action.async { repo.delete(id).map(_ => Ok) }

}
