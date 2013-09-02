package controllers

import anorm.Pk
import models.Icms
import models.UnidadeFederativa
import models.UnidadesFederativas
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import play.api.mvc.Action
import play.api.mvc.Controller
import models.Icmss
import models.Paises
import play.api.mvc.Flash

object IcmsApplication extends Controller {

  val icmsForm = Form(
    mapping(
    	"id" -> optional(longNumber),
        "ufOrigemId" -> longNumber, 
        "ufDestinoId" -> longNumber, 
        "aliquotaInterna" -> of[BigDecimal], 
        "aliquotaInterestadual" -> optional(of[BigDecimal]),
        "baseCalculo" -> of[BigDecimal], 
        "aliquotaSt" -> optional(of[BigDecimal]), 
        "baseCalculoAliquotaSt" -> optional(of[BigDecimal]), 
        "ncmId" -> optional(of[Int]))(Icms.apply)(Icms.unapply))
        
  val home = Redirect(routes.IcmsApplication.icmss);

  def index = Action {
	  home;
  }

  def icmss = Action { implicit request =>
	  Ok(views.html.icms.listIcms(icmsForm, Icmss.all()));
  }

  def addIcms = Action { implicit request =>
	Ok(views.html.icms.addIcms(icmsForm, getUnidadesFederativas, getPaises))
  }
  
  def modalListagemPaises = Action { implicit request =>
	Ok(views.html.icms.modalListagemPaises(icmsForm, getPaises));
  }

  def modalListagemUnidadesFederativasOrigem = Action { implicit request =>
	Ok(views.html.icms.modalListagemUnidadesFederativasOrigem(icmsForm, getUnidadesFederativas));
  }
  
  def modalListagemUnidadesFederativasDestino = Action { implicit request =>
	Ok(views.html.icms.modalListagemUnidadesFederativasDestino(icmsForm, getUnidadesFederativas));
  }
  
  def getUnidadesFederativas:Map[Long, String] = {
    val ufs = UnidadesFederativas.all();
    val mapa = ufs.map(m => m.id.get -> m.sigla).toMap;
    mapa;
  }
  
  def getPaises:Map[Long, String] = {
    val paises = Paises.all();
    val mapa = paises.map(m => m.id.get -> m.descricao).toMap;
    mapa;
  }

  def saveIcms = Action { implicit request =>
      	icmsForm.bindFromRequest.fold(
      		errors => {
      			println(errors);
      			home.flashing(Flash(icmsForm.data) + ("error" -> "Erro ao criar ICMS!"));
      		},
      		icms => {
      			val novoIcms = Icmss.create(icms);
      			if (novoIcms.get != None) {
      				Redirect(routes.IcmsApplication.editIcms(novoIcms.get.id.get))
      					.flashing(Flash(icmsForm.data) + ("success" -> "ICMS criado com sucesso!"));
      			} else {
      			  home.flashing(Flash(icmsForm.data) + ("error" -> "Erro após criar ICMS!"));
      			}
      		}
      	)
  }

  def deleteIcms(id:Long) = Action { implicit request =>
	  if (Icmss.findById(id) != None) {
		  Icmss.delete(id);
	  } else {
		  NotFound;
	  }
	  home;
  }

  def editIcms(id:Long) = Action { implicit request =>
	  Icmss.findById(id).map { icms =>
	  	Ok(views.html.icms.editIcms(icmsForm.fill(icms), id, getUnidadesFederativas, getPaises)) }.getOrElse(NotFound)
  }
  
  def updateIcms(id:Long) = Action {  implicit request =>
    	val iIcms:Form[Icms] = icmsForm.bindFromRequest;
      	iIcms.fold(
      		errors => {
      			Redirect(routes.IcmsApplication.editIcms(id))
      				.flashing(Flash(icmsForm.data) + ("error" -> "Erro ao alterar ICMS!"));
      		},
      		icms => { 
      		  Icmss.update(id, icms);
      			Redirect(routes.IcmsApplication.editIcms(icms.id.get))
      				.flashing(Flash(icmsForm.data) + ("success" -> "ICMS atualizado com sucesso!"));
      		}
      	)
  }
  
  /*def search(pageNumber:Int, pageSize:Int) = Action { implicit request =>
	  Ok(views.html.icms.searchIcms(icmsForm, Icmss.search(pageNumber, pageSize)));
  }*/

}