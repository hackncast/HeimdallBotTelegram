package com.hnc;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class FeedHackNCast {

	private static FeedHackNCast myself;

	private FeedHackNCast() {
		myself = this;
		carregaLinks();
	}

	private ArrayList<String> urls = new ArrayList<>();
	private ArrayList<String> titulos = new ArrayList<>();

	public void carregaLinks() {
		urls = new ArrayList<>();
		titulos = new ArrayList<>();
		try {
			URL url = new URL( "http://feeds.feedburner.com/hackncast" );

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build( new XmlReader( url ) );
			for( Iterator i = feed.getEntries().iterator(); i.hasNext(); ) {
				SyndEntry entry = (SyndEntry) i.next();
				if( entry != null && entry.getUri() != null && !entry.getUri().isEmpty() ) {
					urls.add( entry.getUri() );
					titulos.add( entry.getTitle() );
				}
			}
		} catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<String> getTitulos() {
		return titulos;
	}

	public ArrayList<String> getUrls() {
		return urls;
	}

	public static FeedHackNCast getInstance() {
		if( myself == null ) {
			myself = new FeedHackNCast();
		}
		return myself;
	}

}
