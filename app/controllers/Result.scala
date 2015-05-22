package controllers

import play.api._
import play.api.mvc._
import models.VillageService

object Result extends Controller {
  def index = Action {
    Ok(views.html.index(VillageService.findAll.mkString))
  }
}