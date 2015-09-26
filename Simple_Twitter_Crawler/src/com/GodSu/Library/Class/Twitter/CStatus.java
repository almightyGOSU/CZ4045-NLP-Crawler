package com.GodSu.Library.Class.Twitter;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import twitter4j.Status;
import twitter4j.User;

public class CStatus {
	
	private static String TWITTER_STATUS_SEARCH = "https://twitter.com/statuses/";

	public static void printTweetDetails(Status pObjStatus) {

		if (pObjStatus.isRetweet()) {return;}

		/*StringBuilder objSB = new StringBuilder();

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

		System.out.println(objSB);*/
		
		postJSONContent(TWITTER_STATUS_SEARCH + pObjStatus.getId(),
				pObjStatus.getText());
	}
	
	public static void postJSONContent(String url, String content) {
		
		if("".equals(content))
			return;
		
		content = content.replaceAll("é", "e");
		content = content.replaceAll
	    		  ("(https?://)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([/\\w \\.-]*)*/?","");
		content = content.replaceAll
	    		  ("\\$?\\d+((:|\\.|,)[0-9]+)?([a-zA-Z]+)?", "");
		
		try {
			String baseUrl = "http://nlp.bcdy.tk/source";
			URL object = new URL(baseUrl);
	
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestMethod("POST");
			con.setConnectTimeout(5000);
	
			JSONObject postContent = new JSONObject();
	
			postContent.put("Source", "Social");
			postContent.put("URL", url);
			postContent.put("Content", content);
			
			String finalString = postContent.toString();
			finalString = finalString.replaceAll("\\P{Print}", "");
	
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(finalString);
			wr.flush();
	
			int HttpResult = con.getResponseCode(); 
			if(HttpResult == HttpURLConnection.HTTP_OK ||
					HttpResult == HttpURLConnection.HTTP_CREATED){
	
				System.out.println("GOOD! " + finalString);
				
			} else {
				
			    System.out.println("NOT GOOD! " + finalString);
			}
		}
		catch(SocketTimeoutException e) {
			System.out.println("Socket Timeout!");
		}
		catch(IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
