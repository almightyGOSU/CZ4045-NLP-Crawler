package com.GodSu.Library.Class.Twitter;

import com.GodSu.Util.JSONHelper;

import twitter4j.Status;

public class CStatus {
	
	private static String TWITTER_STATUS_SEARCH = "https://twitter.com/statuses/";

	public static void printTweetDetails(Status pObjStatus) {

		if (pObjStatus.isRetweet()) {return;}
		
		String tweetUrl = TWITTER_STATUS_SEARCH + pObjStatus.getId();
		String content = pObjStatus.getText();
		
		JSONHelper.postJSONContent(tweetUrl, content);
	}
}
