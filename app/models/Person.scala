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
    println("@@@@@@@@@@@@@@@ -> entrou all ");
    implicit c => SQL("select * from person").as(person *);
  }

  def create(name: String, bornDate: Date) = {
    var id: Option[Long] = None;

    println("@@@@@@@@@@@@@@@ -> entrou create ");
    DB.withConnection { implicit c =>
      id = SQL("insert into person (name, born_date) values ({name}, {born_date})").on('name -> name, 'born_date -> bornDate).executeInsert();
    }
    println("@@@@@@@@@@@@@@@ -> saiu create e gerou id = " + id);
    
    if (id != None) {
      Person(id, name, bornDate);
    } else {
      None;
    }
  }

  def delete(id: Long) {
    println("@@@@@@@@@@@@@@@ -> entrou delete id = " + id);
    DB.withConnection { implicit c =>
      SQL("delete from person where id = {id}").on('id -> id).executeUpdate()
    }
    println("@@@@@@@@@@@@@@@ -> saiu delete id = " + id);
  }

  def findByPk(id: Long): Option[Person] = DB.withConnection { implicit c =>
    println("@@@@@@@@@@@@@@@ -> entrou findByPk id = " + id);
    val result:Option[Person] = SQL("select * from person where id = {id}").on("id" -> id).as(person *).headOption
    result;
  }

}