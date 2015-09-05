package com.GodSu.Library.Class.Twitter;

import twitter4j.Status;
import twitter4j.User;

public class CStatus {
	
	private static String TWITTER_STATUS_SEARCH = "https://twitter.com/statuses/";

	public static void printTweetDetails(Status pObjStatus) {

		if (pObjStatus.isRetweet()) {return;}

		StringBuilder objSB = new StringBuilder();

		objSB.append("-------Tweet-------");
		objSB.append(System.getProperty("line.separator"));

		objSB.append("Source: ");
		objSB.append(pObjStatus.getSource());
		objSB.append(System.getProperty("line.separator"));

		objSB.append("ID: ");
		objSB.append(pObjStatus.getId());
		objSB.append(System.getProperty("line.separator"));

		objSB.append("User Name: ");
		objSB.append(pObjStatus.getUser().getName());
		objSB.append(System.getProperty("line.separator"));

		objSB.append("Text: ");
		objSB.append(pObjStatus.getText());
		objSB.append(System.getProperty("line.separator"));
		
		objSB.append("Full link to status: ");
		objSB.append(TWITTER_STATUS_SEARCH).append(pObjStatus.getId());
		objSB.append(System.getProperty("line.separator"));

		System.out.println(objSB);
	}
}
