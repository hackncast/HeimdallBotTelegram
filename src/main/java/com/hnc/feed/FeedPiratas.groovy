package com.hnc.feed;

public class FeedPiratas extends Feed {

	private static FeedPiratas myself;

	private FeedPiratas() {
		super( "https://piratasdainternet.github.io/feeds/all.atom.xml" );
		myself = this;
		carregaLinks();
	}

	public static FeedPiratas getInstance() {
		if( myself == null ) {
			myself = new FeedPiratas();
		}
		return myself;
	}

}
