package com.hnc;

import org.telegram.telegrambots.TelegramApiException;
import org.telegram.telegrambots.TelegramBotsApi;

import com.hnc.db.PerguntasDB;

public class Main {

	public static void main( String[] args ) {
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
		try {
			telegramBotsApi.registerBot( new HeimdallBot() );
			telegramBotsApi.registerBot( new BotControleSQL() );

		} catch( TelegramApiException e ) {
			e.printStackTrace();
		}

	}

}
