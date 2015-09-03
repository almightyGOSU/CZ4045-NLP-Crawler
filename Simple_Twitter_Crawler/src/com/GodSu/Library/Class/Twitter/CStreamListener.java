package com.GodSu.Library.Class.Twitter;

import java.util.Date;
import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;

public class CStreamListener implements StatusListener {

    @Override
    public void onException(Exception arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onScrubGeo(long arg0, long arg1) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatus(Status status) {
        CStatus.printTweetDetails(status);
    }

    @Override
    public void onTrackLimitationNotice(int arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStallWarning(StallWarning sw) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
