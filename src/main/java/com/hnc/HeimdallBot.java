package com.hnc;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.SendMessage;
import org.telegram.telegrambots.api.objects.InlineQuery;
import org.telegram.telegrambots.api.objects.InlineQueryResult;
import org.telegram.telegrambots.api.objects.InlineQueryResultArticle;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class HeimdallBot extends TelegramLongPollingBot {
	
	private static String botUsername = "heimdall_hnc_bot";
	private static String botToken = ">>>>>>>TOKEN<<<<<<<<<";
	private static final Integer CACHETIME = 86400;

	public static void main( String[] args ) {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot( new HeimdallBot() );
		} catch( TelegramApiException e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getBotUsername() {
		return botUsername;
	}

	public void onUpdateReceived( Update update ) {
		if( update.hasInlineQuery() ) {
			handleIncomingInlineQuery( update.getInlineQuery() );
		} else if( update.hasMessage() && update.getMessage().isUserMessage() ) {
			try {
				sendMessage( getHelpMessage( update.getMessage() ) );
				sendMessage( getMengLog( update.getMessage() ) );
			} catch( TelegramApiException e ) {
				e.printStackTrace();
			}
		}

	}

	private void handleIncomingInlineQuery( InlineQuery inlineQuery ) {
		String query = inlineQuery.getQuery();
		try {
			List<String> results = new ArrayList<String>();
			if( !query.isEmpty() ) {
				query = query.replaceAll( "\\_", "\\\\_" );
				results.add( "Bem vindo, " + query + "\nSinto muito em avisar que sua visão do HnC certamente vai mudar" );
			}

			results.add( "Fazer uma pauta: complicado, mas, se você domina ou gosta do assunto, vai tranquilo.\n\nGravar: é uma loucura foda, mas é a parte mais divertida do processo.\n\nEditar: um cu." );
			results.add( "Como entrou uma galera nova nos últimos dias, vale a pena contar a história de novo. Um cara entrou no grupo e eu o cumprimentei com as mesmas palavras que o @Samuelklein Heimdall usou. O cara falou alguma coisa qualquer e saiu do grupo em seguida (há provas \"printscreengráficas\" disso). Claro que a cambada de filhos de umas put... digo, os nobres integrantes desse maravilhoso grupo começaram a me zoar dizendo que era melhor eu não voltar a fazer isso para não espantar novos participantes. O @Samuelklein Heimdall resolveu, então, transferir sua consciência para um bot dedicado a dar boas vindas aos rookies, com as mesmas palavras, como uma cerimônia de iniciação. E tem funcionado desde então. Sugeri a ele escolher entre os títulos de \"porteiro\", \"São Pedro\" ou \"Heimdall\" e, mesmo com a relação que este último tem com o arco íris, foi a escolha óbvia. E ele precisa brigar o tempo todo para manter esta alcunha. Já distribuí outros \"títulos\" a outros usuários (que só eu uso, com exceção do Heimdall, que se amarrou). Quem sabe você também não recebe um, de acordo com sua participação? Dificilmente vocês me verão no meio de discussões sérias e chatas que sempre rolam por aqui, então posso dizer sem medo: bem vindos a essa loucura. \n\n\n ``` by @rictm```" );
			results.add( "Para melhor ou para pior?" );
			sendAnswerInlineQuery( converteResultsToResponse( inlineQuery, results ) );

		} catch( TelegramApiException e ) {
			e.printStackTrace();
		}
	}

	private static AnswerInlineQuery converteResultsToResponse( InlineQuery inlineQuery, List<String> results ) {
		AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
		answerInlineQuery.setInlineQueryId( inlineQuery.getId() );
		answerInlineQuery.setCacheTime( CACHETIME );
		answerInlineQuery.setResults( convertResults( results ) );
		return answerInlineQuery;
	}
	
	private static List<InlineQueryResult> convertResults( List<String> resultsimport ) {
		List<InlineQueryResult> results = new ArrayList<InlineQueryResult>();

		for( int i = 0; i < resultsimport.size(); i++ ) {
			String result = resultsimport.get( i );
			InlineQueryResultArticle article = new InlineQueryResultArticle();
			article.setDisableWebPagePreview( true );
			article.enableMarkdown( true );
			article.setId( Integer.toString( i ) );
			article.setMessageText( result );
			article.setTitle( "Frases HnC" );
			article.setDescription( result );
			results.add( article );
		}

		return results;
	}
	
	private static SendMessage getHelpMessage( Message message ) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId( message.getChatId().toString() );
		sendMessage.enableMarkdown( true );
		String query = message.getText();
		query = query.replaceAll( "\\_", "\\\\_" );
		sendMessage.setText( "Bem vindo, " + query + "\nSinto muito em avisar que sua visão do HnC certamente vai mudar\n\n\n``` kiss my shiny metal ass```" );
		return sendMessage;
	}
	
	private static SendMessage getMengLog( Message message ) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId( "155301081" );
		sendMessage.enableMarkdown( false );
		sendMessage.setText( "User: " + message.getChatId() + "\n\n\n" + message.getText() );
		return sendMessage;
	}
	
	public String getBotToken() {
		return botToken;
	}

}
