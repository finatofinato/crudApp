package models

import anorm._
import anorm.SqlParser._
import java.text.SimpleDateFormat
import java.util.Date
import scala.Some
import play.api.db.DB
import play.api.db._
import play.api.Play.current

case class Person(id: Option[Long], name: String, bornDate: Date)

object Person {

  val person = {
    get[Long]("id") ~ get[String]("name") ~ get[Date]("born_date") map {
      case id ~ name ~ born_date => Person(Some(id), name, born_date)
    }
  }

  def all(): List[Person] = DB.withConnection {
    implicit c => SQL("select * from person").as(person *);
  }

  def create(name: String, bornDate: Date) = {
    var id: Option[Long] = None;

    DB.withConnection { implicit c =>
      id = SQL("insert into person (name, born_date) values ({name}, {born_date})").on('name -> name, 'born_date -> bornDate).executeInsert();
    }

    if (id != None) {
      Person(id, name, bornDate);
    } else {
      None;
    }
  }

  def delete(id: Long) {
    DB.withConnection { implicit c =>
      SQL("delete from person where id = {id}").on('id -> id).executeUpdate()
    }
  }

  def update(person: Person) = {
    DB.withConnection { implicit c =>
      SQL("update person set name = {name}, born_date = {born_date} where id = {id}").
      					on('name -> person.name, 'born_date -> person.bornDate, 'id -> person.id).executeUpdate();
    }
  }

  def findByPk(id: Long): Option[Person] = DB.withConnection { implicit c =>
    val result: Option[Person] = SQL("select * from person where id = {id}").on("id" -> id).singleOpt(person);
    //asSimple[Person](person).on("id" -> id);
    result;
  }

}