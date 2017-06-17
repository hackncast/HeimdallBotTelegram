package com.hnc.feed

import com.sun.syndication.feed.synd.SyndEntry
import com.sun.syndication.feed.synd.SyndFeed
import com.sun.syndication.io.SyndFeedInput
import com.sun.syndication.io.XmlReader

/**
 * Created by samuel on 30/09/16.
 */
public class Feed {

    private String sUrl

    public Feed( String sUrl ) {
        this.sUrl = sUrl
        carregaLinks()
    }

    def urls = []
    def titulos = []

    public void carregaLinks() {
        urls = []
        titulos = []
        try {
            URL url = new URL( sUrl );

            SyndFeedInput input = new SyndFeedInput();
            SyndFeed feed = input.build( new XmlReader( url ) );
            for( Iterator i = feed.getEntries().iterator(); i.hasNext(); ) {
                SyndEntry entry = (SyndEntry) i.next();
                if( entry != null && entry.getLink() != null && !entry.getLink().isEmpty() ) {
                    urls.add( entry.getLink() )
                    titulos.add( entry.getTitle() )
                }
            }
        } catch( Exception e ) {
            e.printStackTrace()
        }
    }

    public String getMensagemRandom() {
        int sorte = (int) ( Math.random() * ( urls.size() - 1 ) )
        return "[" + titulos[ sorte ] + "](" + urls[ sorte ] + ")"
    }
}
