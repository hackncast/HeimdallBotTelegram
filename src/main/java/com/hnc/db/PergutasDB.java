package com.hnc.db;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PergutasDB {

	public PergutasDB() {
	}

	public void criarTabela() throws ClassNotFoundException, IOException, SQLException {
		try (ConexaoSQlite conexaoSQlite = ConexaoSQlite.getConexao()) {
			StringBuilder sb = new StringBuilder();
			sb.append( "CREATE TABLE perguntas( \n" );
			sb.append( "id INTEGER AUTOINCREMENT, \n" );
			sb.append( "ds_pergunta varchar(300), \n" );
			sb.append( "ds_resposta varchar(300), \n" );
			sb.append( "nr_percentual int \n" );
			sb.append( ");" );
			conexaoSQlite.executeQuery( sb.toString() );
		}
	}

	public void incluir( PerguntasTB perguntasTB ) throws ClassNotFoundException, IOException, SQLException {
		try (ConexaoSQlite conexaoSQlite = ConexaoSQlite.getConexao()) {
			PreparedStatement pre = conexaoSQlite.prepareStatement( "insert into perguntas (ds_pergunta, ds_resposta, nr_percentual) values (?,?,?) " );
			pre.setString( 1, perguntasTB.getDsPergunta() );
			pre.setString( 2, perguntasTB.getDsResposta() );
			pre.setObject( 3, perguntasTB.getNrPercetual() );
			conexaoSQlite.executeUpdate( pre );
		}
	}

	public void excluir( PerguntasTB perguntasTB ) throws ClassNotFoundException, IOException, SQLException {
		if( perguntasTB == null || perguntasTB.getId() == null ) {
			return;
		}
		try (ConexaoSQlite conexaoSQlite = ConexaoSQlite.getConexao()) {

			conexaoSQlite.executeUpdate( "delete from pergutas where id = " + perguntasTB.getId() );
		}
	}

	public PerguntasTB busca( PerguntasTB perguntasTB ) throws ClassNotFoundException, IOException, SQLException {
		if( perguntasTB == null || perguntasTB.getId() == null ) {
			return null;
		}

		try (ConexaoSQlite conexaoSQlite = ConexaoSQlite.getConexao()) {
			ResultSet rs = conexaoSQlite.executeQuery( "select id, ds_pergunta, ds_resposta, nr_percentual from perguntas where id = " + perguntasTB.getId() );

			if( rs.next() ) {
				return converteResultTabela( rs );
			}
		}

		return null;
	}

	public List<PerguntasTB> listar( PerguntasTB perguntasTB ) throws ClassNotFoundException, IOException, SQLException {
		if( perguntasTB == null ) {
			return null;
		}

		StringBuilder sb = new StringBuilder();

		sb.append( "select id, ds_pergunta, ds_resposta, nr_percentual from perguntas where id is not null \n" );

		if( perguntasTB.getId() != null ) {
			sb.append( " and id = " + perguntasTB.getId()  );
		}
		
		if( perguntasTB.getNrPercetual() != null ) {
			sb.append( " and nr_percentual = " + perguntasTB.getNrPercetual()  );
		}
		
		if( perguntasTB.getDsPergunta() != null ) {
			sb.append( " and ds_pergunta = '" + perguntasTB.getDsPergunta() + "'");
		}
		
		if( perguntasTB.getDsResposta() != null ) {
			sb.append( " and ds_resposta = '" + perguntasTB.getDsResposta() + "'");
		}
		
		return listar( sb.toString() );
	}

	public List<PerguntasTB> listar( String sql ) throws ClassNotFoundException, IOException, SQLException {

		try (ConexaoSQlite conexaoSQlite = ConexaoSQlite.getConexao()) {
			ResultSet rs = conexaoSQlite.executeQuery( sql );

			List<PerguntasTB> perguntasTBs = new ArrayList<>();
			while( rs.next() ) {
				perguntasTBs.add( converteResultTabela( rs ) );
			}

			if( !perguntasTBs.isEmpty() ) {
				return perguntasTBs;
			}
		}

		return null;
	}

	public PerguntasTB converteResultTabela( ResultSet rs ) throws SQLException {
		PerguntasTB perguntas = new PerguntasTB();

		perguntas.setId( rs.getInt( "id" ) );
		perguntas.setDsPergunta( rs.getString( "ds_pergunta" ) );
		perguntas.setDsResposta( rs.getString( "ds_resposta" ) );
		perguntas.setNrPercetual( rs.getInt( "nr_percentual" ) );
		return perguntas;
	}

}
