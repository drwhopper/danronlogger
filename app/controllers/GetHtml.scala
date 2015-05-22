package controllers

import play.api._
import play.api.mvc._
import models.LogHtml
import scala.io.Source
import models.VillageService

object GetHtml extends Controller {

  def index(id: Int) = Action {
    val zz = LogHtml.readLog(id)
    Ok(views.html.index(zz.toString))
  }

}