package controllers

import play.api.libs.json._
import play.api.mvc.{Action, AnyContent, BaseController, ControllerComponents}
import repos.TaskRepository

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class TaskController @Inject() (repo: TaskRepository, val controllerComponents: ControllerComponents)(implicit
    ec: ExecutionContext
) extends BaseController {

  def getAll: Action[AnyContent] = Action.async { repo.getAll.map(tasks => Ok(Json.toJson(tasks))) }

}
