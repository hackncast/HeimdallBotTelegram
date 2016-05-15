package com.hnc.db;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class PerguntasDB {

	private EntityManager entityManager;

	public PerguntasDB() {
		try {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory( "hjpa" );
			entityManager = factory.createEntityManager();
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

	public void criarTabela() throws ClassNotFoundException, IOException, SQLException {

		// try (ConexaoSQlite conexaoSQlite = ConexaoSQlite.getConexao()) {
		// StringBuilder sb = new StringBuilder();
		// sb.append( "CREATE TABLE perguntas( \n" );
		// sb.append( "id INTEGER PRIMARY KEY, \n" );
		// sb.append( "ds_pergunta varchar(300), \n" );
		// sb.append( "ds_resposta varchar(300), \n" );
		// sb.append( "nr_percentual int \n" );
		// sb.append( ");" );
		// conexaoSQlite.executeUpdate( sb.toString() );
		// }
	}

	public void incluir( PerguntasTB perguntasTB ) throws ClassNotFoundException, IOException, SQLException {

		entityManager.getTransaction().begin();

		entityManager.persist( perguntasTB );

		entityManager.getTransaction().commit();

		// int max = 1;
		//
		// try (ConexaoSQlite conexaoSQlite = ConexaoSQlite.getConexao()) {
		// ResultSet rs = conexaoSQlite.executeQuery( "select max(id) from
		// perguntas " );
		// if( rs.next() ) {
		// max = rs.getInt( 1 );
		// max++;
		// }
		// }
		// perguntasTB.setId( max );
		// try (ConexaoSQlite conexaoSQlite = ConexaoSQlite.getConexao()) {
		//
		// PreparedStatement pre = conexaoSQlite.prepareStatement( "insert into
		// perguntas ( id, ds_pergunta, ds_resposta, nr_percentual) values
		// (?,?,?,?) " );
		// pre.setInt( 1, perguntasTB.getId() );
		// pre.setString( 2, perguntasTB.getDsPergunta() );
		// pre.setString( 3, perguntasTB.getDsResposta() );
		// pre.setObject( 4, perguntasTB.getNrPercetual() );
		// conexaoSQlite.executeUpdate( pre );
		// }
	}

	public void excluir( PerguntasTB perguntasTB ) throws ClassNotFoundException, IOException, SQLException {
		if( perguntasTB == null || perguntasTB.getId() == null ) {
			return;
		}

		entityManager.getTransaction().begin();

		entityManager.remove( perguntasTB );

		entityManager.getTransaction().commit();

		// try (ConexaoSQlite conexaoSQlite = ConexaoSQlite.getConexao()) {
		//
		// conexaoSQlite.executeUpdate( "delete from pergutas where id = " +
		// perguntasTB.getId() );
		// }
	}

	public PerguntasTB busca( PerguntasTB perguntasTB ) throws ClassNotFoundException, IOException, SQLException {
		// if( perguntasTB == null || perguntasTB.getId() == null ) {
		// return null;
		// }
		//
		// try (ConexaoSQlite conexaoSQlite = ConexaoSQlite.getConexao()) {
		// ResultSet rs = conexaoSQlite.executeQuery( "select id, ds_pergunta,
		// ds_resposta, nr_percentual from perguntas where id = " +
		// perguntasTB.getId() );
		//
		// if( rs.next() ) {
		// return converteResultTabela( rs );
		// }
		// }

		return entityManager.find( PerguntasTB.class, perguntasTB );
	}

	public List<PerguntasTB> listar( PerguntasTB perguntasTB ) throws ClassNotFoundException, IOException, SQLException {
		if( perguntasTB == null ) {
			return null;
		}

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<PerguntasTB> criteria = cb.createQuery(PerguntasTB.class);
		
		Root<PerguntasTB> pergunta = criteria.from(PerguntasTB.class);
		criteria.orderBy( cb.asc( pergunta.get( "id" ) ) );
		
		
		TypedQuery<PerguntasTB> query = entityManager.createQuery( criteria );
		return query.getResultList();

//		return listar( sb.toString() );
	}

	public List<PerguntasTB> listar( String sql ) throws ClassNotFoundException, IOException, SQLException {

		// try (ConexaoSQlite conexaoSQlite = ConexaoSQlite.getConexao()) {
		// ResultSet rs = conexaoSQlite.executeQuery( sql );
		//
		// List<PerguntasTB> perguntasTBs = new ArrayList<>();
		// while( rs.next() ) {
		// perguntasTBs.add( converteResultTabela( rs ) );
		// }
		//
		// if( !perguntasTBs.isEmpty() ) {
		// return perguntasTBs;
		// }
		// }
		Query query = entityManager.createNativeQuery( sql, ArrayList.class );

		System.out.println( query.getResultList() );

		return query.getResultList();
	}

	// public PerguntasTB converteResultTabela( ResultSet rs ) throws
	// SQLException {
	// PerguntasTB perguntas = new PerguntasTB();
	//
	// perguntas.setId( rs.getInt( "id" ) );
	// perguntas.setDsPergunta( rs.getString( "ds_pergunta" ) );
	// perguntas.setDsResposta( rs.getString( "ds_resposta" ) );
	// perguntas.setNrPercetual( rs.getInt( "nr_percentual" ) );
	// return perguntas;
	// }

}
