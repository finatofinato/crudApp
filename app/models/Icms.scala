package models

import anorm._
import anorm.SqlParser._
import java.text.SimpleDateFormat
import java.util.Date
import scala.Some
import play.api.db.DB
import play.api.db._
import play.api.Play.current
import scala.BigDecimal 
import models.Page

case class Icms(id: Option[Long], ufOrigemId:Long, ufDestinoId:Long, aliquotaInterna:BigDecimal, aliquotaInterestadual:Option[BigDecimal], baseCalculo:BigDecimal, aliquotaSt:Option[BigDecimal], baseCalculoAliquotaSt:Option[BigDecimal], ncmId:Option[Int])

object Icmss extends PaginationModel {

	val icms = {
		get[Long]("id") ~ get[Long]("uf_origem_id") ~ get[Long]("uf_destino_id") ~ 
		get[BigDecimal]("aliquotainterna") ~ get[Option[BigDecimal]]("aliquotainterestadual") ~ get[BigDecimal]("basecalculo") ~ 
		get[Option[BigDecimal]]("aliquotast") ~ get[Option[BigDecimal]]("basecalculoaliquotast") ~ get[Option[Int]]("ncm_id") map {
			case id ~ uf_origem_id ~ uf_destino_id ~ aliquotainterna ~ aliquotainterestadual ~ basecalculo ~ aliquotast ~ basecalculoaliquotast ~ ncm_id => 
			  Icms(Some(id), uf_origem_id, uf_destino_id, aliquotainterna, aliquotainterestadual, basecalculo, aliquotast, basecalculoaliquotast, ncm_id)
		}
	}
	
	def all():List[Icms] = { 
		DB.withConnection { implicit c => 
			SQL("select * from ICMS").as(icms *);
		}
	}

	def create(icms:Icms):Option[Icms] = {
		var id: Option[Long] = None;

    	DB.withConnection { implicit c =>
    	  	var sql:String = "insert into ICMS (UF_ORIGEM_ID, UF_DESTINO_ID, ALIQUOTAINTERNA, ALIQUOTAINTERESTADUAL, BASECALCULO, ALIQUOTAST, BASECALCULOALIQUOTAST, NCM_ID) values ";
    	  	sql += "({UF_ORIGEM_ID}, {UF_DESTINO_ID}, {ALIQUOTAINTERNA}, {ALIQUOTAINTERESTADUAL}, {BASECALCULO}, {ALIQUOTAST}, {BASECALCULOALIQUOTAST}, {NCM_ID})";
    		id = SQL(sql).on('UF_ORIGEM_ID -> icms.ufOrigemId, 'UF_DESTINO_ID -> icms.ufDestinoId, 'ALIQUOTAINTERNA -> icms.aliquotaInterna, 'ALIQUOTAINTERESTADUAL -> icms.aliquotaInterestadual, 'BASECALCULO -> icms.baseCalculo, 'ALIQUOTAST -> icms.aliquotaSt, 'BASECALCULOALIQUOTAST -> icms.baseCalculoAliquotaSt, 'NCM_ID -> icms.ncmId).executeInsert();
    	}

    	if (id != None) {
    		Some(Icms(id, icms.ufOrigemId, icms.ufDestinoId, icms.aliquotaInterna, icms.aliquotaInterestadual, icms.baseCalculo, icms.aliquotaSt, icms.baseCalculoAliquotaSt, icms.ncmId));
    	} else {
    		None;
    	}
	}
	
	def delete(id:Long) {
		DB.withConnection { implicit c =>
			SQL("delete from ICMS where ID = {id}").on('id -> id).executeUpdate()
		}
	}
	
	def update(id:Long, icms:Icms) = {
		DB.withConnection { implicit c =>
			SQL("update ICMS set UF_ORIGEM_ID = {UF_ORIGEM_ID}, UF_DESTINO_ID = {UF_DESTINO_ID}, ALIQUOTAINTERNA = {ALIQUOTAINTERNA}, ALIQUOTAINTERESTADUAL = {ALIQUOTAINTERESTADUAL}, BASECALCULO = {BASECALCULO}, ALIQUOTAST = {ALIQUOTAST}, BASECALCULOALIQUOTAST = {BASECALCULOALIQUOTAST}, NCM_ID = {NCM_ID} where ID = {id}").
      					on('UF_ORIGEM_ID -> icms.ufOrigemId, 'UF_DESTINO_ID -> icms.ufDestinoId, 'ALIQUOTAINTERNA -> icms.aliquotaInterna, 'ALIQUOTAINTERESTADUAL -> icms.aliquotaInterestadual, 'BASECALCULO -> icms.baseCalculo, 'ALIQUOTAST -> icms.aliquotaSt, 'BASECALCULOALIQUOTAST -> icms.baseCalculoAliquotaSt, 'NCM_ID -> icms.ncmId, 'ID -> id).executeUpdate();
		}
	}	
	
	def findById(id:Long):Option[Icms] = {
		DB.withConnection { implicit c =>
    		val result: Option[Icms] = SQL("select * from ICMS where ID = {id}").on('id -> id).singleOpt(icms);
			result;
		}
	}
	
	//TODO
	def search(pageNumber:Int, pageSize:Int):Page[Icms] = {
		var sql:String = "select * from ICMS where NOME like {nome}";
	
		val map:Map[String, Any] = Map("a" -> 1, "b" -> 2, "c" -> "A");
		val convertedMap = map.map(x => (x._1 -> anorm.toParameterValue(x._2))).toSeq;
		
		val parameters: scala.collection.immutable.Seq[(String, ParameterValue[Any])] = scala.collection.immutable.Seq(convertedMap: _*); 
		val page:Page[Icms] = listByParameters[Icms](sql, parameters, icms, pageNumber, pageSize);
		page;
	}

}
