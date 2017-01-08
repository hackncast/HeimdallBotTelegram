package com.hnc.feed;

public class FeedOpenCast extends Feed {

	private static FeedOpenCast myself;

	private FeedOpenCast() {
		super( "http://tecnologiaaberta.com.br/feed" );
		myself = this;
		carregaLinks();
	}

	public static FeedOpenCast getInstance() {
		if( myself == null ) {
			myself = new FeedOpenCast();
		}
		return myself;
	}

}
