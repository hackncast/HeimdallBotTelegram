package com.hnc

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi



class Main {

	public static void main( String[] args ) {
		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi()
		try {

			print("começa")
			telegramBotsApi.registerBot( new HeimdallBot() )
		} catch( Exception e ) {
			e.printStackTrace()
		}
	}
}
