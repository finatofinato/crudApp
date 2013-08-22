package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.mvc.Action
import play.api.mvc.Controller
import play.data.validation.Validation
import models.Fornecedor
import anorm.Pk
import anorm.NotAssigned
import play.api.mvc.Flash
import play.mvc.Http
import models.Teste

object TesteApplication extends Controller {

  val testeForm = Form(
    mapping(
    	"id" -> optional(longNumber),
        "nome" -> text(maxLength = 10))
        (Teste.apply)(Teste.unapply))
        
  val home = Redirect(routes.TesteApplication.testes);

  def index = Action {
	  home;
  }

  def testes = Action { implicit request =>
	  Ok(views.html.teste.listTeste(testeForm, Teste.all()));
  }
  
  def modalListTeste = Action { implicit request =>
	  Ok(views.html.teste.modalListTeste(testeForm, Teste.all));
  }

}