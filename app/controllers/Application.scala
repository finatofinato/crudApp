package controllers

import models.Person
import play.api.data._
import play.api.data.Forms._
import play.api.mvc.Action
import play.api.mvc.Controller
import play.api.data.format.Formats._
import play.data.validation.Validation

object Application extends Controller {

  val personForm = Form(
    mapping(
      "id" -> optional(longNumber),
      "name" -> nonEmptyText(0, 10),
      "bornDate" -> date("dd/MM/yyyy"))(Person.apply)(Person.unapply))

  val home = Redirect(routes.Application.persons);

  def index = Action {
    home;
  }

  def persons = Action { implicit request =>
    Ok(views.html.index(Person.all(), personForm));
  }

  def addNewPerson = Action { implicit request =>
    Ok(views.html.addNewPerson(personForm))
  }

  def saveNewPerson = Action { implicit request =>
      personForm.bindFromRequest.fold(
        errors => {
          BadRequest(views.html.index(Person.all(), errors));
        },
        person => {
          Person.create(person.name, person.bornDate);
          home;
        })
  }

  def deletePerson(id: Long) = Action { implicit request =>
    if (Person.findByPk(id) != None) {
      Person.delete(id);
    } else {
      println("###############-> Person.delete - NAO ENCONTROU ID: " + id);
    }
    home;
  }

  def editPerson(id: Long) = Action { implicit request =>
    Person.findByPk(id).map { person =>
      Ok(views.html.editPerson(personForm.fill(person), id)) }.getOrElse(NotFound)
  }
  
  def updatePerson(id: Long) = Action { implicit request =>
      personForm.bindFromRequest.fold(
        errors => {
          println("###############-> erroUpdatePerson \n" + errors);
          BadRequest(views.html.index(Person.all(), errors));
        },
        person => {
          println("###############-> update ok " + id);
          Person.update(Person(Some(id), person.name, person.bornDate));
          home;
        })
  }

}