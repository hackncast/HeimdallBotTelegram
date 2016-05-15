package com.hnc;

import java.util.HashMap;
import java.util.Map;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardHide;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;

public class BotControleSQL extends TelegramLongPollingBot {

	private static String botUsername = Configuracao.NOME_BD_BOT;
	private static String botToken = Configuracao.TOKEN_DB_BOT;

	private Map<Integer, BotControleUsuario> lista = new HashMap<>();

	public String getBotUsername() {
		return botUsername;
	}

	public void onUpdateReceived( Update update ) {
		Message message = update.getMessage();
		if( message != null && message.getFrom() != null && message.getFrom().getId() != null ) {
			BotControleUsuario botControleUsuario;
			if( lista.containsKey( message.getFrom().getId() ) ) {
				botControleUsuario = lista.get( message.getFrom().getId() );
			} else {
				botControleUsuario = new BotControleUsuario( ( s ) -> {
					try {
						sendMessage( getMensagemSolta( message, s ) );
					} catch( Exception e ) {
						e.printStackTrace();
					}
					return "";
				} );
				lista.put( message.getFrom().getId(), botControleUsuario );

			}

			if( message.getText() != null ) {
				botControleUsuario.executa( message.getText() );
			}

		}

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

	public String getBotToken() {
		return botToken;
	}

}
