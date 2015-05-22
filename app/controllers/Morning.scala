package controllers

import play.api._
import play.api.mvc._
import models.LogHtml

object Morning extends Controller {

  def about = Action {
    Ok(views.html.about())
  }

}