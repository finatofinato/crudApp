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
import models.FornecedorDAO

object FornecedoresApplication extends Controller {

  val fornecedorForm = Form(
    mapping(
      "id" -> optional(longNumber),
        "nome" -> nonEmptyText(0, 100),
        "cnpj" -> nonEmptyText(0, 14),
        "cpf" -> nonEmptyText(0, 11),
        "nomeFantasia" -> optional(text(50)),
        "endereco" -> optional(text(50)),
        "complemento" -> optional(text(100)),
        "bairro" -> optional(text(50)),
        "cep" -> optional(text(8)),
        "uf" -> optional(text(2)),
        "cidade" -> optional(text(50)),
        "ie" -> text(15),
        "im" -> text(15),
        "fone" -> optional(text(12)),
        "dataCadastro" -> date("yyyy/MM/dd"))(Fornecedor.apply)(Fornecedor.unapply))
        
  val home = Redirect(routes.FornecedoresApplication.fornecedores);

  def index = Action {
    home;
  }

  def fornecedores = Action {
    Ok(views.html.fornecedor.listFornecedor(FornecedorDAO.all(), fornecedorForm));
  }

  def addNovoFornecedor = Action {
    Ok(views.html.fornecedor.addNovoFornecedor(fornecedorForm))
  }

  def saveNovoFornecedor = Action {
    implicit request =>
      fornecedorForm.bindFromRequest.fold(
        errors => {
          BadRequest(views.html.fornecedor.listFornecedor(FornecedorDAO.all(), errors));
        },
        fornecedor => {
          FornecedorDAO.insert(fornecedor);
          home;
        })
  }

  def deleteFornecedor(id: Long) = Action {
    if (FornecedorDAO.findById(id) != None) {
      FornecedorDAO.delete(id);
    } else {
      NotFound
    }
    home;
  }

  def editFornecedor(id: Long) = Action {
    FornecedorDAO.findById(id).map { fornecedor =>
      Ok(views.html.fornecedor.editFornecedor(fornecedorForm.fill(fornecedor), id)) }.getOrElse(NotFound)
  }
  
  def updateFornecedor(id: Long) = Action {
    implicit request =>
      fornecedorForm.bindFromRequest.fold(
        errors => {
          BadRequest(views.html.fornecedor.listFornecedor(FornecedorDAO.all(), errors));
        },
        fornecedor => {
          FornecedorDAO.update(id, fornecedor);
          home;
        })
  }

}