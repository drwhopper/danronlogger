package controllers

import play.api.mvc.Action
import play.api.mvc.Controller
import services.GetJsonService
import models.RuruReader

object GetJson extends Controller {

  def index = Action {
    RuruReader.getInfo
    Ok(views.html.index(""))
  }
}
