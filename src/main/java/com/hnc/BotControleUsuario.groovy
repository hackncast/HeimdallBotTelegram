package com.hnc;

import java.util.List;
import java.util.function.Function;

import org.json.JSONArray;

import com.hnc.db.PerguntasDB;
import com.hnc.db.PerguntasTB

import groovy.json.JsonOutput;;

public class BotControleUsuario {

	private static String COMANDO_INICIAR = "/start";

	private static String COMANDO_LISTAR = "/listar";

	private static String COMANDO_INCLUIR = "/incluir";

	private static String COMANDO_CRIAR = "/criarTabela";

	private static String COMANDO_EXCLUIR = "/excluir";

	private Function<String, String> enviarMensagem;

	private String comandosAtual;

	private PerguntasTB perguntasTB;

	private PerguntasDB perguntasDB = new PerguntasDB();

	public BotControleUsuario( Function<String, String> enviarMensagem ) {
		this.enviarMensagem = enviarMensagem;
	}

	public void executa( String texto ) {
		
		if( texto.startsWith( COMANDO_INICIAR ) ) {
			enviarMensagem.apply( "Iniciando processo." );
			comandosAtual = COMANDO_INICIAR;
		} else if( texto.startsWith( COMANDO_INCLUIR ) ) {
			enviarMensagem.apply( "Informe a pergunta" );
			perguntasTB = new PerguntasTB();
			comandosAtual = COMANDO_INCLUIR;
		} else if( texto.startsWith( COMANDO_EXCLUIR ) ) {
			enviarMensagem.apply( "Informe o codigo de exclusão" );
			comandosAtual = COMANDO_EXCLUIR;
		} else if( texto.startsWith( COMANDO_LISTAR ) ) {

			List lista = null;

			try {
				lista = perguntasDB.listar( new PerguntasTB() );
			} catch( Exception e ) {
				e.printStackTrace();
			}
			enviarMensagem.apply( "Lista: \n" + JsonOutput.toJson( lista ).toString() );
		} else if( texto.startsWith( COMANDO_CRIAR ) ) {
			try {
				perguntasDB.criarTabela();
			} catch( Exception e ) {
				e.printStackTrace();
			}
			enviarMensagem.apply( "Tabela Criada" );
		} else {
			if( comandosAtual.equals( COMANDO_INCLUIR ) ) {
				if( perguntasTB.dsPergunta == null ) {
					perguntasTB.dsPergunta = texto;
					enviarMensagem.apply( "Informe a resposta" );
				} else if( perguntasTB.dsResposta == null ) {
					perguntasTB.dsResposta = texto;
					enviarMensagem.apply( "Gostaria de gravar:" );
				} else if( texto.toUpperCase().equals( "SIM" ) ) {
					try {
						perguntasDB.incluir( perguntasTB );
					} catch( Exception e ) {
						e.printStackTrace();
					}

					enviarMensagem.apply( "gravando: " + perguntasTB.getId() );
					comandosAtual = COMANDO_INICIAR;
				}

			}

		}

	}

}
