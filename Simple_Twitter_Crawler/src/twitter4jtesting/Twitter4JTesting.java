package twitter4jtesting;

import com.GodSu.Library.Class.Twitter.CStreamListener;
import com.GodSu.Util.TwitterConst;

import java.util.Date;

import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 */
@SuppressWarnings("unused")
public class Twitter4JTesting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("P9sUs46f03m4xAJZzxt7Q");
        cb.setOAuthConsumerSecret("Czw2g7QoURXz5WoLE8AbMounBiVAONktlQP1YleEy0");
        cb.setOAuthAccessToken("182798423-gFyyrktqZmcrTu916G7zTkNX3pnTsB8Zc4s22dOc");
        cb.setOAuthAccessTokenSecret("ITKJnDENspq5dVZIQMjTtG4NOj2ioT0QE2DkE9ZZA");

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        CStreamListener objStreamListener = new CStreamListener();

        FilterQuery fq = new FilterQuery();

        String [] keywords = TwitterConst.KEYWORDS;

        fq.track(keywords);

        twitterStream.addListener(objStreamListener);
        twitterStream.filter(fq);
    }
}
