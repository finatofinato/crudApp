package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.mvc.Action
import play.api.mvc.Controller
import play.data.validation.Validation
import models.UnidadeFederativa
import anorm.Pk
import anorm.NotAssigned
import play.api.mvc.Flash
import play.mvc.Http
import models.UnidadesFederativas
import models.Paises
import models.Pais

object UnidadeFederativaApplication extends Controller {

  val unidadeFederativaForm = Form(
    mapping(
    	"id" -> optional(longNumber),
    	"paisId" -> longNumber,
        "sigla" -> nonEmptyText(maxLength = 10),
        "nome" -> nonEmptyText(maxLength = 40))
        ((id, paisId, sigla, nome) => UnidadeFederativa(id, paisId, sigla, nome))
        ((uf:UnidadeFederativa) => Some(uf.id, uf.paisId, uf.sigla, uf.nome))
        )
        
  val home = Redirect(routes.UnidadeFederativaApplication.unidadesFederativas);

  def index = Action {
	  home;
  }

  def unidadesFederativas = Action { implicit request =>
	  Ok(views.html.unidadefederativa.listUnidadeFederativa(unidadeFederativaForm, UnidadesFederativas.all()));
  }

  def addUnidadeFederativa = Action { implicit request =>
	Ok(views.html.unidadefederativa.addUnidadeFederativa(unidadeFederativaForm, getPaises))
  }
  
  def modalListagemPaises = Action { implicit request =>
	Ok(views.html.unidadefederativa.modalListagemPaises(unidadeFederativaForm, getPaises));
  }
  
  def getPaises:Map[Long, String] = {
    val paises = Paises.all();
    val mapa = paises.map(m => m.id.get -> m.descricao).toMap;
    mapa;
  }

  def saveUnidadeFederativa = Action { implicit request =>
      	unidadeFederativaForm.bindFromRequest.fold(
      		errors => {
      			home.flashing(Flash(unidadeFederativaForm.data) + ("error" -> "Erro ao criar UF!"));
      		},
      		unidadeFederativa => {
      			val novaUnidadeFederativa = UnidadesFederativas.create(unidadeFederativa);
      			if (novaUnidadeFederativa.get != None) {
      				Redirect(routes.UnidadeFederativaApplication.editUnidadeFederativa(novaUnidadeFederativa.get.id.get))
      					.flashing(Flash(unidadeFederativaForm.data) + ("success" -> "UF criada com sucesso!"));
      			} else {
      			  home.flashing(Flash(unidadeFederativaForm.data) + ("error" -> "Erro após criar UF!"));
      			}
      		}
      	)
  }

  def deleteUnidadeFederativa(id:Long) = Action { implicit request =>
	  if (UnidadesFederativas.findById(id) != None) {
		  UnidadesFederativas.delete(id);
	  } else {
		  NotFound;
	  }
	  home;
  }

  def editUnidadeFederativa(id:Long) = Action { implicit request =>
	  UnidadesFederativas.findById(id).map { unidadeFederativa =>
	  	Ok(views.html.unidadefederativa.editUnidadeFederativa(unidadeFederativaForm.fill(unidadeFederativa), id, getPaises)) }.getOrElse(NotFound)
  }
  
  def updateUnidadeFederativa(id:Long) = Action {  implicit request =>
    	val uUnidadeFederativa:Form[UnidadeFederativa] = unidadeFederativaForm.bindFromRequest;
      	uUnidadeFederativa.fold(
      		errors => {
      			Redirect(routes.UnidadeFederativaApplication.editUnidadeFederativa(id))
      				.flashing(Flash(unidadeFederativaForm.data) + ("error" -> "Erro ao alterar Unidade Federativa!"));
      		},
      		unidadeFederativa => { 
      		  UnidadesFederativas.update(id, unidadeFederativa);
      			Redirect(routes.UnidadeFederativaApplication.editUnidadeFederativa(unidadeFederativa.id.get))
      				.flashing(Flash(unidadeFederativaForm.data) + ("success" -> "Unidade Federativa atualizado com sucesso!"));
      		}
      	)
  }
  
  def search(pageNumber:Int, pageSize:Int) = Action { implicit request =>
	  Ok(views.html.unidadefederativa.searchUnidadeFederativa(unidadeFederativaForm, UnidadesFederativas.search(pageNumber, pageSize)));
  }

}