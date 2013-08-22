package models

import anorm._
import anorm.SqlParser._
import java.text.SimpleDateFormat
import java.util.Date
import scala.Some

case class Teste(id: Option[Long], nome: String)

object Teste {

  val teste = {
    get[Long]("id") ~ get[String]("nome") map {
      case id ~ nome => Teste(Some(id), nome)
    }
  }

  def all(): List[Teste] = {
    List[Teste] ( Teste(Some(1), "finato1"), Teste(Some(2), "finato2"), Teste(Some(3), "finato3"), Teste(Some(4), "finato4") )
  }

}