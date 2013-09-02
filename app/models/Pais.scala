package models

import anorm._
import anorm.SqlParser._
import java.text.SimpleDateFormat
import java.util.Date
import scala.Some
import play.api.db.DB
import play.api.db._
import play.api.Play.current
import models.Page

case class Pais(id: Option[Long], descricao:String, descricaoNacionalidade:Option[String])

object Paises extends PaginationModel {

	val pais = {
		get[Long]("id") ~ get[String]("descricao") ~ get[Option[String]]("descricaonacionalidade") map {
			case id ~ descricao ~ descricaonacionalidade => Pais(Some(id), descricao, descricaonacionalidade) 
		}
	}
	
	def all():List[Pais] = { 
		DB.withConnection { implicit c => 
			SQL("select * from PAIS").as(pais *);
		}
	}
	

	def create(pais:Pais):Option[Pais] = {
		var id: Option[Long] = None;

    	DB.withConnection { implicit c =>
    	  	var sql:String = "insert into PAIS (DESCRICAO, DESCRICAONACIONALIDADE) values ({descricao}, {descricaoNacionalidade})"
    		id = SQL(sql).on('descricao -> pais.descricao, 'descricaoNacionalidade -> pais.descricaoNacionalidade).executeInsert();
    	}

    	if (id != None) {
    		Some(Pais(id, pais.descricao, pais.descricaoNacionalidade));
    	} else {
    		None;
    	}
	}
	
	def delete(id:Long) {
		DB.withConnection { implicit c =>
			SQL("delete from PAIS where ID = {id}").on('id -> id).executeUpdate()
		}
	}
	
	def update(id:Long, pais:Pais) = {
		DB.withConnection { implicit c =>
			SQL("update PAIS set DESCRICAO = {descricao}, DESCRICAONACIONALIDADE = {descricaoNacionalidade} where ID = {id}").
      					on('descricao -> pais.descricao, 'descricaoNacionalidade -> pais.descricaoNacionalidade, 'id -> id).executeUpdate();
		}
	}	
	
	def findById(id:Long):Option[Pais] = {
		DB.withConnection { implicit c =>
    		val result: Option[Pais] = SQL("select * from PAIS where ID = {id}").on('id -> id).singleOpt(pais);
			result;
		}
	}
	
	//TODO
	def search(pageNumber:Int, pageSize:Int):Page[Pais] = {
		var sql:String = "select * from PAIS where DESCRICAO like {descricao}";
	
	val map:Map[String, Any] = Map("a" -> 1, "b" -> 2, "c" -> "A");
	
	val parameters: scala.collection.immutable.Seq[(Any, ParameterValue[Any])] = scala.collection.immutable.Seq('descricao -> "%finato%");
	val page:Page[Pais] = listByParameters[Pais](sql, parameters, pais, pageNumber, pageSize);
	page;
	}
}
