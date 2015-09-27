package util;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONHelper {
	
	public static int goodCount = 0;
	public static int badCount = 0;
	
	public static void postJSONContent(String url, String content) {
		
		if("".equals(content))
			return;
		
		content = content.replaceAll("é", "e");
		content = content.replaceAll
	    		  ("(https?://)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([/\\w \\.-]*)*/?","");
		content = content.replaceAll
	    		  ("\\$?\\d+((:|\\.|,)[0-9]+)?([a-zA-Z]+)?", "");
		content = content.replaceAll("\\P{Print}", "");
		
		try {
			String baseUrl = WebConst.BASE_URL;
			URL object = new URL(baseUrl);
	
			HttpURLConnection con = (HttpURLConnection) object.openConnection();
			con.setDoOutput(true);
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestMethod("POST");
			con.setConnectTimeout(5000);
	
			JSONObject postContent = new JSONObject();
	
			postContent.put("Source", "Web");
			postContent.put("URL", url);
			postContent.put("Content", content);
			
			String finalString = postContent.toString();
	
			OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
			wr.write(finalString);
			wr.flush();
	
			int HttpResult = con.getResponseCode(); 
			if(HttpResult == HttpURLConnection.HTTP_OK ||
					HttpResult == HttpURLConnection.HTTP_CREATED){
	
				System.out.println("GOOD! " + finalString);
				goodCount++;
				
			} else {
				
			    System.out.println("NOT GOOD! HttpResult: " + HttpResult +
			    		"! " + finalString);
			    badCount++;
			}
		}
		catch(SocketTimeoutException e) {
			System.out.println("Socket Timeout!");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
