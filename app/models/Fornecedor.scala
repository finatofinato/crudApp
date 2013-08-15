package models

import anorm._
import anorm.SqlParser._
import java.text.SimpleDateFormat
import java.util.Date
import scala.Some
import play.api.db.DB
import play.api.db._
import play.api.Play.current

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
						dataCadastro:Option[java.util.Date])

object Fornecedores {

	val fornecedor = {
		get[Long]("id") ~ get[String]("nome") ~ get[String]("cnpj") ~ get[String]("cpf") ~ get[Option[String]]("nome_fantasia") ~ get[Option[String]]("endereco") ~ 
		get[Option[String]]("complemento") ~ get[Option[String]]("bairro") ~ get[Option[String]]("cep") ~ get[Option[String]]("uf") ~ get[Option[String]]("cidade") ~ 
		get[String]("ie") ~ get[String]("im") ~ get[Option[String]]("fone") ~ get[Option[java.util.Date]]("data_cadastro") map {
		case id ~ nome ~ cnpj ~ cpf ~ nome_fantasia ~ endereco ~ complemento ~ bairro ~ cep ~ uf ~ cidade ~ ie ~ im ~ fone ~ data_cadastro => 
		  Fornecedor(Some(id), nome, cnpj, cpf, nome_fantasia, endereco, complemento, bairro, cep, uf, cidade, ie, im, fone, data_cadastro)
		}
	}

	def all():List[Fornecedor] = { 
		DB.withConnection { implicit c => 
			SQL("select * from fornecedor").as(fornecedor *);
		}
	}

	def create(fornecedor:Fornecedor):Option[Fornecedor] = {
		var id: Option[Long] = None;

    	DB.withConnection { implicit c =>
    	  	var sql:String = "insert into fornecedor (nome, cnpj, cpf, nome_fantasia, endereco, complemento, bairro, cep, uf, cidade, ie, im, fone, data_cadastro) ";
    	  	sql += "values ({nome}, {cnpj}, {cpf}, {nome_fantasia}, {endereco}, {complemento}, {bairro}, {cep}, {uf}, {cidade}, {ie}, {im}, {fone}, {data_cadastro})"
    		id = SQL(sql).on('nome -> fornecedor.nome, 'cnpj -> fornecedor.cnpj, 'cpf -> fornecedor.cpf, 'nome_fantasia -> fornecedor.nomeFantasia, 'endereco -> fornecedor.endereco, 'complemento -> fornecedor.complemento, 'bairro -> fornecedor.bairro, 'cep -> fornecedor.cep, 'uf -> fornecedor.uf, 'cidade -> fornecedor.cidade, 'ie -> fornecedor.inscricaoEstadual, 'im -> fornecedor.inscricaoMunicipal, 'fone -> fornecedor.fone, 'data_cadastro -> new Date).executeInsert();
    	}

    	if (id != None) {
    		Some(Fornecedor(id, fornecedor.nome, fornecedor.cnpj, fornecedor.cpf, fornecedor.nomeFantasia, fornecedor.endereco, fornecedor.complemento, fornecedor.bairro, fornecedor.cep, fornecedor.uf, fornecedor.cidade, fornecedor.inscricaoEstadual, fornecedor.inscricaoMunicipal, fornecedor.fone, fornecedor.dataCadastro));
    	} else {
    		None;
    	}
	}

	def delete(id:Long) {
		DB.withConnection { implicit c =>
			SQL("delete from fornecedor where id = {id}").on('id -> id).executeUpdate()
		}
	}

	def update(id:Long, fornecedor:Fornecedor) = {
		DB.withConnection { implicit c =>
			SQL("update fornecedor set nome = {nome}, cnpj = {cnpj}, cpf = {cpf}, nome_fantasia = {nome_fantasia}, endereco = {endereco}, complemento = {complemento}, bairro = {bairro}, cep = {cep}, uf = {uf}, cidade = {cidade}, ie = {ie}, im = {im}, fone = {fone}, data_cadastro = {data_cadastro} where id = {id}").
      					on('nome -> fornecedor.nome, 'cnpj -> fornecedor.cnpj, 'cpf -> fornecedor.cpf, 'nome_fantasia -> fornecedor.nomeFantasia, 'endereco -> fornecedor.endereco, 'complemento -> fornecedor.complemento, 'bairro -> fornecedor.bairro, 'cep -> fornecedor.cep, 'uf -> fornecedor.uf, 'cidade -> fornecedor.cidade, 'ie -> fornecedor.inscricaoEstadual, 'im -> fornecedor.inscricaoMunicipal, 'fone -> fornecedor.fone, 'data_cadastro -> fornecedor.dataCadastro, 'id -> id).executeUpdate();
		}
	}

	def findById(id:Long): Option[Fornecedor] = {
		DB.withConnection { implicit c =>
    		val result: Option[Fornecedor] = SQL("select * from fornecedor where id = {id}").on("id" -> id).singleOpt(fornecedor);
			result;
		}
	}
}
