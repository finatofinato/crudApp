package models

import anorm._
import anorm.SqlParser._
import java.text.SimpleDateFormat
import java.util.Date
import scala.Some
import play.api.db.DB
import play.api.db._
import play.api.Play.current
import scala.collection.immutable.Seq

case class Page[A](items: Seq[A], page: Int, offset: Long, total: Long) {
  lazy val prev = Option(page - 1).filter(_ >= 0)
  lazy val next = Option(page + 1).filter(_ => (offset + items.size) < total)
}

trait PaginationModel {

	/*
	 * T = tipo de retorno (Fornecedor)
	 */
	//https://markatta.com/codemonkey/blog/2012/08/10/unparsing-with-anorm-in-play-framework-2/
	//http://stackoverflow.com/questions/15904145/why-the-scala-to-expand-a-seq-into-variable-length-argument-list-does-not-wo
	//http://stackoverflow.com/questions/15591479/dynamic-sql-parameters-with-anorm-and-scala-play-framework
	def listByParameters[T](sql:String, parameters:Seq[(Any, ParameterValue[_])], rowParser:anorm.RowParser[T], page:Int = 0, pageSize:Int = 2): Page[T] = {

	  val offest = pageSize * page;
		
		DB.withConnection { implicit connection =>
		  val result = SQL(sql).on(parameters:_*).as(rowParser *); //bem possivel ter q alterar os parameters
		
		  val totalRows = SQL(
		    " select count(*) from ("+ sql + ") "
		      ).on(parameters:_*).as(scalar[Long].single);
		
		      Page(result, page, offest, totalRows);
		}
	}
}