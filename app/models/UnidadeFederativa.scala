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

case class UnidadeFederativa(id: Option[Long], paisId:Long, sigla:String, nome:String)

object UnidadesFederativas extends PaginationModel {

	val unidadeFederativa = {
		get[Long]("id") ~ get[Long]("pais_id") ~ get[String]("sigla") ~ get[String]("nome")  map {
			case id ~ pais_id ~ sigla ~ nome => UnidadeFederativa(Some(id), pais_id, sigla, nome) 
		}
	}
	
	def all():List[UnidadeFederativa] = { 
		DB.withConnection { implicit c => 
			SQL("select * from UF").as(unidadeFederativa *);
		}
	}

	def create(unidadeFederativa:UnidadeFederativa):Option[UnidadeFederativa] = {
		var id: Option[Long] = None;

    	DB.withConnection { implicit c =>
    	  	var sql:String = "insert into UF (PAIS_ID, SIGLA, NOME) values ({pais_id}, {sigla}, {nome})"
    		id = SQL(sql).on('pais_id -> unidadeFederativa.paisId, 'sigla -> unidadeFederativa.sigla, 'nome -> unidadeFederativa.nome).executeInsert();
    	}

    	if (id != None) {
    		Some(UnidadeFederativa(id, unidadeFederativa.paisId, unidadeFederativa.sigla, unidadeFederativa.nome));
    	} else {
    		None;
    	}
	}
	
	def delete(id:Long) {
		DB.withConnection { implicit c =>
			SQL("delete from UF where ID = {id}").on('id -> id).executeUpdate()
		}
	}
	
	def update(id:Long, unidadeFederativa:UnidadeFederativa) = {
		DB.withConnection { implicit c =>
			SQL("update UF set PAIS_ID = {pais_id}, SIGLA = {sigla}, NOME = {nome} where ID = {id}").
      					on('pais_id -> unidadeFederativa.paisId, 'sigla -> unidadeFederativa.sigla, 'nome -> unidadeFederativa.nome, 'id -> id).executeUpdate();
		}
	}	
	
	def findById(id:Long):Option[UnidadeFederativa] = {
		DB.withConnection { implicit c =>
    		val result: Option[UnidadeFederativa] = SQL("select * from UF where ID = {id}").on('id -> id).singleOpt(unidadeFederativa);
			result;
		}
	}
	
	//TODO
	def search(pageNumber:Int, pageSize:Int):Page[UnidadeFederativa] = {
		var sql:String = "select * from UF where NOME like {nome}";
	
		val map:Map[String, Any] = Map("a" -> 1, "b" -> 2, "c" -> "A");
		val convertedMap = map.map(x => (x._1 -> anorm.toParameterValue(x._2))).toSeq;
		
		val parameters: scala.collection.immutable.Seq[(String, ParameterValue[Any])] = scala.collection.immutable.Seq(convertedMap: _*); 
		val page:Page[UnidadeFederativa] = listByParameters[UnidadeFederativa](sql, parameters, unidadeFederativa, pageNumber, pageSize);
		page;
	}

}
