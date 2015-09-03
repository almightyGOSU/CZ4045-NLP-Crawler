/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package twitter4jtesting;

import com.GodSu.Library.Class.Twitter.CStreamListener;
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
public class Twitter4JTesting {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("----Consumer Key-----");
        cb.setOAuthConsumerSecret("---Secret----");
        cb.setOAuthAccessToken("---Token----");
        cb.setOAuthAccessTokenSecret("----Token-----");

        TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

        CStreamListener objStreamListener = new CStreamListener();

        FilterQuery fq = new FilterQuery();

        String keywords[] = {"messi"};

        fq.track(keywords);

        twitterStream.addListener(objStreamListener);
        twitterStream.filter(fq);
    }
}
