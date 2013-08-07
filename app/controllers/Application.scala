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
      "bornDate" -> date("yyyy-MM-dd")
      )(Person.apply)(Person.unapply))

  val home = Redirect(routes.Application.persons);
      
  def index = Action {
    home;
  }

  def persons = Action {
    Ok(views.html.index(Person.all(), personForm));
  }
  
  def addNewPerson = Action {
    Ok(views.html.addNewPerson(personForm))
  }
  
  def saveNewPerson = Action {
    implicit request =>
      personForm.bindFromRequest.fold(
        errors => {
          println("###############-> erroNewPerson \n" + errors);
          BadRequest(views.html.index(Person.all(), errors));
          },
        person => {
          println("###############-> criou " + person.id);
          Person.create(person.name, person.bornDate);
          home;
        })
  }

  def deletePerson(id: Long) = Action {
    println("###############-> Person.delete - entrou " + id);
    if (Person.findByPk(id) != None) {
    	println("###############-> Person.delete - ENCONTROU ID " + id);
    	Person.delete(id);
    } else {
    	println("###############-> Person.delete - NAO ENCONTROU ID: " + id);
    }
    home;
  }

}