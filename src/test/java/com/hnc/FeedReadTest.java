package com.hnc;

import java.net.URL;
import java.util.Iterator;

import org.junit.Test;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class FeedReadTest {

	@Test
	public void testLerFeed() {
		try {
			URL url = new URL( "http://tecnologiaaberta.com.br/feed/" );

			SyndFeedInput input = new SyndFeedInput();
			SyndFeed feed = input.build( new XmlReader( url ) );
			for( Iterator i = feed.getEntries().iterator(); i.hasNext(); ) {
				SyndEntry entry = (SyndEntry) i.next();
				System.out.println( entry.getUri().isEmpty());
				System.out.println( entry.getUri() );
			}
		} catch( Exception e ) {
			e.printStackTrace();
		}

	}

}