package com.hnc.feed;

public class FeedHackNCast extends Feed {

	private static FeedHackNCast myself;

	private FeedHackNCast() {
		super( "http://feeds.feedburner.com/hackncast" );
		myself = this;
		carregaLinks();
	}

	public static FeedHackNCast getInstance() {
		if( myself == null ) {
			myself = new FeedHackNCast();
		}
		return myself;
	}

}
