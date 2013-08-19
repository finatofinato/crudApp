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
import models.Fornecedores
import play.api.mvc.Flash
import play.mvc.Http

object FornecedoresApplication extends Controller {

  val fornecedorForm = Form(
    mapping(
    	"id" -> optional(longNumber),
        "nome" -> nonEmptyText(maxLength = 100),
        "cnpj" -> nonEmptyText(maxLength = 14),
        "cpf" -> nonEmptyText(maxLength = 11),
        "nomeFantasia" -> optional(text(maxLength = 50)),
        "endereco" -> optional(text(maxLength = 50)),
        "complemento" -> optional(text(maxLength = 100)),
        "bairro" -> optional(text(maxLength = 50)),
        "cep" -> optional(text(maxLength = 8)),
        "uf" -> optional(text(maxLength = 2)),
        "cidade" -> optional(text(maxLength = 50)),
        "inscricaoEstadual" -> nonEmptyText(maxLength = 15),
        "inscricaoMunicipal" -> nonEmptyText(maxLength = 15),
        "fone" -> optional(text(maxLength = 12)),
        "dataCadastro" -> optional(date("dd/MM/yyyy")),
        "isActive" -> optional(boolean))
        ((id, nome, cnpj, cpf, nomeFantasia, endereco, complemento, bairro, cep, uf, cidade, inscricaoEstadual, inscricaoMunicipal, fone, dataCadastro, isActive) => Fornecedor(id, nome, cnpj, cpf, nomeFantasia, endereco, complemento, bairro, cep, uf, cidade, inscricaoEstadual, inscricaoMunicipal, fone, dataCadastro))
        ((f:Fornecedor) => Some(f.id, f.nome, f.cnpj, f.cpf, f.nomeFantasia, f.endereco, f.complemento, f.bairro, f.cep, f.uf, f.cidade, f.inscricaoEstadual, f.inscricaoMunicipal, f.fone, f.dataCadastro, Some(false)))
        )
        
        
        //(id, nome, cnpj, cpf, nomeFantasia, endereco, complemento, bairro, cep, uf, cidade, inscricaoEstadual, inscricaoMunicipal, fone, _, dataCadastro) => Fornecedor(id, nome, cnpj, cpf, nomeFantasia, endereco, complemento, bairro, cep, uf, cidade, inscricaoEstadual, inscricaoMunicipal, fone, dataCadastro)
        //(f:Fornecedor) => Some(f.id, f.nome, f.cnpj, f.cpf, f.nomeFantasia, f.endereco, f.complemento, f.bairro, f.cep, f.uf, f.cidade, f.inscricaoEstadual, f.inscricaoMunicipal, f.fone, false, f.dataCadastro)
  val ufs:Map[String, String] = Map(("AC" -> "AC"), ("AL" -> "AL"), ("AP" -> "AP"), ("AM" -> "AM"), ("BA" -> "BA"), ("CE" -> "CE"),
									("DF" -> "DF"), ("ES" -> "ES"), ("GO" -> "GO"), ("MA" -> "MA"), ("MT" -> "MT"), ("MS" -> "MS"),
									("MG" -> "MG"), ("PA" -> "PA"), ("PB" -> "PB"), ("PR" -> "PR"), ("PE" -> "PE"), ("PI" -> "PI"),
									("RJ" -> "RJ"), ("RN" -> "RN"), ("RS" -> "RS"), ("RO" -> "RO"), ("RR" -> "RR"), ("SC" -> "SC"),
									("SP" -> "SP"), ("SE" -> "SE"), ("TO" -> "TO"));
  
  
        
  val home = Redirect(routes.FornecedoresApplication.fornecedores);

  def index = Action {
	  home;
  }

  def fornecedores = Action { implicit request =>
	  Ok(views.html.fornecedor.listFornecedor(Fornecedores.all(), fornecedorForm));
  }

  def addNovoFornecedor = Action { implicit request =>
	  Ok(views.html.fornecedor.addNovoFornecedor(fornecedorForm, getUfs))
  }
  
  def getUfs():Map[String, String] = {
    ufs;
  }

  def saveNovoFornecedor = Action {
	  implicit request =>
      	fornecedorForm.bindFromRequest.fold(
      		errors => {
      			//BadRequest(views.html.fornecedor.listFornecedor(Fornecedores.all(), errors)).
      			home.flashing(Flash(fornecedorForm.data) + ("error" -> "Erro ao criar Fornecedor!"));
      		},
      		fornecedor => {
      			val novoFornecedor = Fornecedores.create(fornecedor);
      			if (novoFornecedor.get != None) {
      				Redirect(routes.FornecedoresApplication.editFornecedor(novoFornecedor.get.id.get))
      					.flashing(Flash(fornecedorForm.data) + ("success" -> "Fornecedor criado com sucesso!"));
      			} else {
      			  home.flashing(Flash(fornecedorForm.data) + ("error" -> "Erro apos criar Fornecedor!"));
      			}
      		}
      	)
  }

  def deleteFornecedor(id:Long) = Action { implicit request =>
	  if (Fornecedores.findById(id) != None) {
		  Fornecedores.delete(id);
	  } else {
		  NotFound;
	  }
	  home;
  }

  def editFornecedor(id:Long) = Action { implicit request =>
	  Fornecedores.findById(id).map { fornecedor =>
	  	Ok(views.html.fornecedor.editFornecedor(fornecedorForm.fill(fornecedor), id, getUfs)) }.getOrElse(NotFound)
  }
  
  def updateFornecedor(id:Long) = Action {  implicit request =>
    	val fFornecedor:Form[Fornecedor] = fornecedorForm.bindFromRequest;
      	fFornecedor.fold(
      		errors => {
      		  println(errors);
      		  
      			Redirect(routes.FornecedoresApplication.editFornecedor(id))
      				.flashing(Flash(fornecedorForm.data) + ("error" -> "Erro ao alterar Fornecedor!"));
      			//BadRequest(views.html.fornecedor.listFornecedor(Fornecedores.all(), errors))
      				//.flashing(Flash(fornecedorForm.data) + ("error" -> "Erro ao alterar Fornecedor!"));
      		},
      		fornecedor => { 
      		  
      		  println("===========>" + fFornecedor.data.get("isActive"));
      		  println("===========>" + (if(fFornecedor.data.get("isActive") != None) { fFornecedor.data.get("isActive").get} else {"nao veio"}));
      		  
      		  Fornecedores.update(id, fornecedor);
      			Redirect(routes.FornecedoresApplication.editFornecedor(fornecedor.id.get))
      				.flashing(Flash(fornecedorForm.data) + ("success" -> "Fornecedor atualizado com sucesso!"));
      		}
      	)
  }

}