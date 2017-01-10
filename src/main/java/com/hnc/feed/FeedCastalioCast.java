package com.hnc.feed;

public class FeedCastalioCast extends Feed {

    private static FeedCastalioCast myself;

    private FeedCastalioCast() {
        super( "http://feeds.feedburner.com/CastalioPodcastMP3" );
        myself = this;
        carregaLinks();
    }

    public static FeedCastalioCast getInstance() {
        if( myself == null ) {
            myself = new FeedCastalioCast();
        }
        return myself;
    }

}
