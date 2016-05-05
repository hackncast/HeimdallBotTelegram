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
import org.telegram.telegrambots.api.objects.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class HeimdallBot extends TelegramLongPollingBot {

	private static String botUsername = "heimdall_hnc_bot";
	private static String botToken = "TK";
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
		try {
			if( update.hasMessage() ) {
				if( update.getMessage().getNewChatParticipant() != null || update.getMessage().getLeftChatParticipant() != null ) {
					sendMessage( getBemVindo( update.getMessage() ) );
				} else if( update.getMessage().getText() != null && update.getMessage().getText().startsWith( "/prox_hnc" ) ) {
					sendMessage( getMensagemSolta( update.getMessage(), "daqui a 3 meses" ) );
				} else if( update.getMessage().getText() != null && update.getMessage().getText().startsWith( "/idade_da_galera" ) ) {
					List<String> nomes = new ArrayList<String>();
					nomes.add( "@age Gilson" );
					nomes.add( "@age Jorge" );
					nomes.add( "@age Magnun" );
					nomes.add( "@age Ricardo" );

					sendMessage( getMensagemSolta( update.getMessage(), nomes, "Qual integrante vê quer saber:\n" ) );
				} else if( update.getMessage().getText() != null && update.getMessage().getText().startsWith( "@age" ) ) {
					String msg = update.getMessage().getText();

					String msgEnv = "Este nome nó Xiste";

					if( msg.endsWith( "Gilson" ) ) {
						msgEnv = "Gilson: 26 Anos";
					} else if( msg.endsWith( "Jorge" ) ) {
						msgEnv = "Jorge: 36 Anos";
					} else if( msg.endsWith( "Magnun" ) ) {
						msgEnv = "Magnun: 30 Anos";
					} else if( msg.endsWith( "Ricardo" ) ) {

						msgEnv = "Ricardo: Processando....";
					}

					sendMessage( getMensagemSolta( update.getMessage(), msgEnv ) );

					if( msg.endsWith( "Ricardo" ) ) {

						Thread.sleep( 10000 );
						sendMessage( getMensagemSolta( update.getMessage(), "Ricardo: " + getZuera() + " Anos" ) );
					}

				} else {
					if( !( update.getMessage().isSuperGroupMessage() || update.getMessage().isGroupMessage() ) ) {
						sendMessage( getHelpMessage( update.getMessage() ) );
					}
				}
				sendMessage( getMengLog( update.getMessage() ) );
			} else if( update.hasInlineQuery() ) {
				listaPesquisa( update.getInlineQuery() );
			}

		} catch( Exception e ) {
			e.printStackTrace();
		}
	}

	private String getZuera() {

		String[] msgs = { "segmentation fault", "java.lang.NullPointerException at", "caught this error: ValueError('represents a hidden bug, do not catch this',)", "uncaught #{e} exception while handling connection: #{e.message}", "ReferenceError: idadeRicardo is not defined", "Exception in thread \"idadeRicardo\" java.lang.StackOverflowError at java.io.PrintStream.write(PrintStream.java:480)", "Traceback (most recent call last): File \"<stdin>\", line 42, in <module>NumberIsTooBig: Sorry the number you're trying to calculate is to big" };

		return msgs[ (int) ( Math.random() * ( msgs.length - 1 ) ) ];
	}

	private void listaPesquisa( InlineQuery inlineQuery ) {
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

	private static SendMessage getMensagemSolta( Message message, String msg ) {

		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId( message.getChatId().toString() );
		sendMessage.enableMarkdown( true );
		sendMessage.setReplayToMessageId( message.getMessageId() );

		String query = message.getText();
		try {
			query = query.replaceAll( "\\_", "\\\\_" );
		} catch( Exception e ) {
			e.printStackTrace();
		}
		sendMessage.setText( msg );
		return sendMessage;
	}

	private static SendMessage getMensagemSolta( Message message, List<String> comandos, String msg ) {

		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId( message.getChatId().toString() );
		sendMessage.enableMarkdown( true );
		ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
		List<List<String>> commands = new ArrayList<List<String>>();
		for( String string : comandos ) {
			List<String> commandRow = new ArrayList<String>();
			commandRow.add( string );
			commands.add( commandRow );
		}

		replyKeyboardMarkup.setResizeKeyboard( true );
		replyKeyboardMarkup.setOneTimeKeyboad( true );
		replyKeyboardMarkup.setKeyboard( commands );
		replyKeyboardMarkup.setSelective( false );
		sendMessage.setReplayMarkup( replyKeyboardMarkup );
		sendMessage.setReplayToMessageId( message.getMessageId() );

		String query = message.getText();
		try {
			query = query.replaceAll( "\\_", "\\\\_" );
		} catch( Exception e ) {
			e.printStackTrace();
		}
		sendMessage.setText( msg );
		return sendMessage;
	}

	private static SendMessage getHelpMessage( Message message ) {

		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId( message.getChatId().toString() );
		sendMessage.enableMarkdown( true );

		String query = message.getText();
		try {
			query = query.replaceAll( "\\_", "\\\\_" );
		} catch( Exception e ) {
			e.printStackTrace();
		}
		sendMessage.setText( "Bem vindo, " + query + "\nSinto muito em avisar que sua visão do HnC certamente vai mudar\n\n\n``` kiss my shiny metal ass```" );
		return sendMessage;
	}

	private static SendMessage getBemVindo( Message message ) {

		SendMessage sendMessage = new SendMessage();
		sendMessage.setChatId( message.getChatId().toString() );
		sendMessage.enableMarkdown( true );

		if( message.getNewChatParticipant() != null ) {
			User newChatParticipant = message.getNewChatParticipant();
			String query = newChatParticipant.getUserName();

			if( query != null ) {
				query = "@" + query;
			} else {
				query = newChatParticipant.getFirstName() + ( newChatParticipant.getLastName() != null ? "_" + newChatParticipant.getLastName() : "" );
			}

			try {
				query = query.replaceAll( "\\_", "\\\\_" );
			} catch( Exception e ) {
				e.printStackTrace();
			}

			sendMessage.setText( "Bem vindo, " + query + "\nSinto muito em avisar que sua visão do HnC certamente vai mudar" );
		} else {
			User leftChatParticipant = message.getLeftChatParticipant();
			String query = leftChatParticipant.getUserName();
			if( query == null ) {
				query = leftChatParticipant.getFirstName() + ( leftChatParticipant.getLastName() != null ? "_" + leftChatParticipant.getLastName() : "" );
			}

			try {
				query = query.replaceAll( "\\_", "\\\\_" );
			} catch( Exception e ) {
				e.printStackTrace();
			}

			sendMessage.setText( query + " não aguentou a presão!" );
		}

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
