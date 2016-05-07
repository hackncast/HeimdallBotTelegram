package com.hnc;

import java.util.ArrayList;
import java.util.List;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.api.objects.inlinequery.InlineQuery;
import org.telegram.telegrambots.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardHide;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

import net.java.frej.fuzzy.Fuzzy;

public class HeimdallBot extends TelegramLongPollingBot {

	private static String botUsername = "heimdall_hnc_bot";
	private static String botToken = "<token>";
	private static final Integer CACHETIME = 86400;

	private String[] perguntas = { "quando vai sair o Podcast de java", "qual idade Ricardo", "qual idade gilson", "qual idade magnun", "qual idade jorge", " Faz algo de interressante", "qual o proximo episódio" , " quando vamos ter episódio novo?", "quem é você?"};

	private String[] respostas = { "Uma Dia que sabe, quando o pessoal resolver gravar", "Nasceu a 10mil anos Atras", "Não sei", "Eu não sei","Eu já disse que não sei", "Não", "#daquia3meses", "#daquia3meses", "Um bot muito loko." };

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
				
				if( update.getMessage().getNewChatMember() != null || update.getMessage().getLeftChatMember() != null ) {
					sendMessage( getBemVindo( update.getMessage() ) );
				} else if( update.getMessage().getText() != null && ( update.getMessage().getText().startsWith( "/higthlander_age" ) || update.getMessage().getText().startsWith( "/ricardo_age" ) ) ) {
					String msgEnv = "Ricardo: Processando....";
					sendMessage( getMensagemSolta( update.getMessage(), msgEnv ) );
					Thread.sleep( 10000 );
					sendMessage( getMensagemSolta( update.getMessage(), "Ricardo: " + getZuera() + " Anos" ) );
				} else if( update.getMessage().getText() != null && update.getMessage().getText().startsWith( "/start" ) ) {
					sendMessage( getMensagemSolta( update.getMessage(), "vc queria que este fizesse algo revolucionario vai ser dificil.\n Mas tente me pergutar." ) );
				} else if( update.getMessage().getText() != null && update.getMessage().getText().startsWith( "/idade" ) ) {
					List<String> nomes = new ArrayList<String>();
					nomes.add( "Ricardo" );
					nomes.add( "Magnun" );
					sendMessage( getMensagemSolta( update.getMessage(), nomes, "Click" ) );
					
				} else if( update.getMessage().getText() != null && update.getMessage().getText().startsWith( "/parei" ) ) {
					sendMessage( getMensagemSolta( update.getMessage(), "Não me incomoda" ) );
				} else if( update.getMessage().getText() != null && update.getMessage().getText().startsWith( "/magnun_age" ) ) {
					sendMessage( getMensagemSolta( update.getMessage(), "Magnun: 30 Anos" ) );
				} else if( update.getMessage().getText() != null && update.getMessage().getText().startsWith( "/gilson_age" ) ) {
					sendMessage( getMensagemSolta( update.getMessage(), "Gilson: 26 Anos" ) );
				} else if( update.getMessage().getText() != null && update.getMessage().getText().startsWith( "/jorge_age" ) ) {
					sendMessage( getMensagemSolta( update.getMessage(), "Jorge: 36 Anos" ) );
				} else if( update.getMessage().getText() != null && update.getMessage().getText().startsWith( "/prox_hnc" ) ) {
					sendMessage( getMensagemSolta( update.getMessage(), "daqui a 3 meses" ) );
				} else if( update.getMessage().getText() != null && update.getMessage().getText().startsWith( "@age" ) ) {
					sendMessage( getMensagemSolta( update.getMessage(), "parei, já perdeu a graça" ) );
				} else {

					int valor = -1;
					double percentual = -1;

					if( update.getMessage().getText() != null ) {
						for( int i = 0; i < perguntas.length; i++ ) {
							double per = Fuzzy.similarity( update.getMessage().getText().replaceAll( "@heimdall_hnc_bot", "" ), perguntas[ i ] );
							System.out.println( perguntas[ i ] + " - " + ( per ) + " " + ( per > 80 ) );
							if( per < 0.6 ) {
								if( per > percentual ) {
									valor = i;
									percentual = per;

								}
							}
						}
					}
					if( valor != -1 ) {
						sendMessage( getMensagemSolta( update.getMessage(), respostas[ valor ] ) );
						// } else if( !(
						// update.getMessage().isSuperGroupMessage() ||
						// update.getMessage().isGroupMessage() ) ) {
						// sendMessage( getMensagemSolta( update.getMessage(),
						// "vc queria que este fizesse algo revolucionario vai
						// ser dificil.\n Mas tente me pergutar." ) );
					} else {
						sendMessage( getMensagemSolta( update.getMessage(), "vc é burro cara... vc fala de maneira burra.." ) );
					}
				}
				sendMessage( getMengLog( update.getMessage() ) );
				// } else if( update.hasInlineQuery() ) {
				// listaPesquisa( update.getInlineQuery() );
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
			answerInlineQuery( converteResultsToResponse( inlineQuery, results ) );

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

			InputTextMessageContent inputMessageContent = new InputTextMessageContent();
			inputMessageContent.setDisableWebPagePreview( true );
			inputMessageContent.setMessageText( result );
			// inputMessageContent.setParseMode( parseMode )

			article.setInputMessageContent( inputMessageContent );

			article.setId( Integer.toString( i ) );
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
		// sendMessage.setReplayToMessageId( message.getMessageId() );

		ReplyKeyboardHide replyKeyboardHide = new ReplyKeyboardHide();
		replyKeyboardHide.setSelective( true );
		replyKeyboardHide.setHideKeyboard( true );
		sendMessage.setReplayMarkup( replyKeyboardHide );

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
		InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
		List<List<InlineKeyboardButton>> commands = new ArrayList<List<InlineKeyboardButton>>();
		for( String string : comandos ) {
			List<InlineKeyboardButton> commandRow = new ArrayList<InlineKeyboardButton>();
			InlineKeyboardButton key = new InlineKeyboardButton();
			key.setText( string );
			key.setCallbackData( string );
			commandRow.add( key );
			commands.add( commandRow );
		}

//		replyKeyboardMarkup.setResizeKeyboard( true );
//		replyKeyboardMarkup.setOneTimeKeyboad( true );
		replyKeyboardMarkup.setKeyboard( commands );
//		replyKeyboardMarkup.setSelective( false );
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

		if( message.getNewChatMember() != null ) {
			User newChatParticipant = message.getNewChatMember();
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
			User leftChatParticipant = message.getLeftChatMember();
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

		StringBuilder sb = new StringBuilder();

		sb.append( "ChatId:\n" );
		sb.append( message.getChatId() );

		sb.append( "\nMensagem:\n" );
		sb.append( message.getText() );

		sb.append( "\nMensagem:\n" );
		sb.append( message.toString() );

		sendMessage.setText( sb.toString() );

		return sendMessage;
	}

	public String getBotToken() {
		return botToken;
	}

}
