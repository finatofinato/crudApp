package models

import java.util.Date

import scala.slick.driver.H2Driver.simple._
import scala.slick.driver.H2Driver.simple.Database.threadLocalSession
import scala.slick.lifted.Query
import anorm.SqlParser._
import play.api.Play.current
import play.api.db.DB

case class Fornecedor(id: Option[Long], 
						nome:String, 
						cnpj:String, 
						cpf:String, 
						nomeFantasia:Option[String], 
						endereco:Option[String], 
						complemento:Option[String], 
						bairro:Option[String], 
						cep:Option[String],
						uf:Option[String], 
						cidade:Option[String], 
						inscricaoEstadual:String, 
						inscricaoMunicipal:String, 
						fone:Option[String], 
						dataCadastro:java.util.Date
)

//https://github.com/freekh/play-slick/blob/master/samples/computer-database/app/models/Models.scala
object Fornecedores extends Table[Fornecedor]("FORNECEDOR") {
	implicit val tm = MappedTypeMapper.base[java.util.Date, java.sql.Date](
			x => new java.sql.Date(x.getTime),
			x => new java.util.Date(x.getTime)
			)

	def id = column[Long]("id", O.PrimaryKey, O.AutoInc); 
  	def nome = column[String]("nome"); 
	def cnpj = column[String]("cnpj"); 
	def cpf = column[String]("cpf"); 
	def nomeFantasia = column[String]("nome_fantasia", O.Nullable); 
	def endereco = column[String]("endereco", O.Nullable);
	def complemento = column[String]("complemento", O.Nullable); 
	def bairro = column[String]("bairro", O.Nullable);
	def cep = column[String]("cep", O.Nullable);
	def uf = column[String]("uf", O.Nullable);
	def cidade = column[String]("cidade", O.Nullable);
	def inscricaoEstadual = column[String]("ie"); 
	def inscricaoMunicipal = column[String]("im");
	def fone = column[String]("fone", O.Nullable);
	def dataCadastro = column[java.util.Date]("data_cadastro");
	def * = id.? ~ nome ~ cnpj ~ cpf ~ nomeFantasia.? ~ endereco.? ~ complemento.? ~ bairro.? ~ cep.? ~ uf.? ~ cidade.? ~ inscricaoEstadual ~ inscricaoMunicipal ~ fone.? ~ dataCadastro <> (Fornecedor.apply _, Fornecedor.unapply _); 
	def forInsert = nome ~ cnpj ~ cpf ~ nomeFantasia.? ~ endereco.? ~ complemento.? ~ bairro.? ~ cep.? ~ uf.? ~ cidade.? ~ inscricaoEstadual ~ inscricaoMunicipal ~ fone.? ~ dataCadastro <> ({ t => Fornecedor(None, t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14)}, { (f: Fornecedor) => Some((f.nome, f.cnpj, f.cpf, f.nomeFantasia, f.endereco, f.complemento, f.bairro, f.cep, f.uf, f.cidade, f.inscricaoEstadual, f.inscricaoMunicipal, f.fone, f.dataCadastro))}); 
	def forSelect = id.? ~ nome ~ cnpj ~ cpf ~ nomeFantasia.? ~ endereco.? ~ complemento.? ~ bairro.? ~ cep.? ~ uf.? ~ cidade.? ~ inscricaoEstadual ~ inscricaoMunicipal ~ fone.? ~ dataCadastro <> ({ t => Fornecedor(t._1, t._2, t._3, t._4, t._5, t._6, t._7, t._8, t._9, t._10, t._11, t._12, t._13, t._14, t._15)}, { (f: Fornecedor) => Some((f.id, f.nome, f.cnpj, f.cpf, f.nomeFantasia, f.endereco, f.complemento, f.bairro, f.cep, f.uf, f.cidade, f.inscricaoEstadual, f.inscricaoMunicipal, f.fone, f.dataCadastro))});
	
}

object FornecedorDAO {
  
	lazy val database = Database.forDataSource(DB.getDataSource())
	
	/*lazy val findAllQuery = for (entity <- Fornecedores) yield entity
	def all(): List[Fornecedor] = database.withSession { 
      findAllQuery.list
    }*/
	

	def all():List[Fornecedor] = database.withSession {
		//( for( a <- Fornecedores ) yield a.* ).list
		
		val q = Query(Fornecedores);
		val list = q.list;
		list;
	}
	
	def insert(fornecedor:Fornecedor):Long = {
		val insertedId = Fornecedores.forInsert returning Fornecedores.id insert fornecedor;
		insertedId;
	}
	
	def update(id:Long, fornecedor:Fornecedor) {
	  val query = for { f <- Fornecedores if f.id === id } yield f;
	  query.update(fornecedor);
	}
	
	def delete(id:Long) {
		val query = for { f <- Fornecedores if f.id === id } yield f;
		query.delete;
	}
	
	def findById(id:Long):Option[Fornecedor] = {
		val query = for { 
		  idq <- Parameters[Long] 
		  f <- Fornecedores if f.id is idq
		} yield f;
		
		val f:Option[Fornecedor] = Some(query(id).first());
		f;
	}  
  
  
	
	/*
	//def autoInc = * returning id;

	def insert(fornecedor: Fornecedor):Fornecedor = {
		Database.forDataSource(DB.getDataSource()) withSession {
			Fornecedores.autoInc.insert(fornecedor);
			fornecedor; //retorna??
		}
	}
	
	def update(id:Long, fornecedor: Fornecedor) {
	  Database.forDataSource(DB.getDataSource()) withSession {
		  	val objToUpdate: Fornecedor = fornecedor.copy(Some(id));
			Fornecedores.where(_.id === id).update(objToUpdate);
		}
	}
	
	def delete(id: Long) {
	  Database.forDataSource(DB.getDataSource()) withSession {
		  Fornecedores.where(_.id === id).delete;
	  }
	}
	
	def all2():List[Fornecedor] = {
	  Database.forDataSource(DB.getDataSource()) withSession {
		  Fornecedores.where().li
	  }
	}
	
	def findByPk(id:Long): Option[Fornecedor] = {
	  Database.forDataSource(DB.getDataSource()) withSession {
		  //Fornecedores.f
	    null;
	  }
	}
	*/
}