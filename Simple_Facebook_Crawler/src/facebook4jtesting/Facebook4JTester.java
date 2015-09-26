package facebook4jtesting;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Page;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;
import facebook4j.internal.org.json.JSONException;
import facebook4j.internal.org.json.JSONObject;

/** This is an empty class **/
/** You just got smoked! **/

public class Facebook4JTester {
	
	private static int _goodCount = 0;
	private static int _badCount = 0;

	public static void main(String[] args) {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
				.setOAuthAppId("1428624607431156")
				.setOAuthAppSecret("9de584c1319b8cb6335c09f7d04d5153")
				.setOAuthAccessToken(
						"CAAUTU5YZBSfQBAIUixEZBa3PQLJo7"
								+ "ikwXja7YVfVZBYyHd03JN2x10b335wJQTVMZBxV8KpT"
								+ "j4lHWM9zuM3xrESYwdpXmkwS0KmXKdYxwMm1LZBCQrK"
								+ "vdx7T6ohP3rFdJVu2ui6zSiLk3A0ZCQqe74PbcyER80"
								+ "cfxGlWeFWyN6i6B4yzI0ZBXPl62IVkOJkzGoxnBblZA"
								+ "iejFrszbLaLf8Eq")
				.setOAuthPermissions("email, publish_stream, read_stream");
		
		FacebookFactory ff = new FacebookFactory(cb.build());
		Facebook facebook = ff.getInstance();
		
		ExecutorService executor = Executors.newFixedThreadPool(15);
		
		try {
			
			// Make a directory in case it goes crazy..
			// WARNING: You have been warned!!
			new File("Results").mkdirs();
			
			/*StringBuilder sb = null;
			String lineSep = System.getProperty("line.separator");*/
			
			// Change searchKey to find a different keyword
			String searchKey = "SingaporeFood";
			
			ResponseList<Page> pageResults = facebook.searchPages(searchKey);
			System.out.println("# of Returned Pages: " + pageResults.size());
			
			int validPages = 0;
			
			for(Page page : pageResults) {
				
				String pageID = page.getId();
				String pageName = page.getName();
				
				// Replace all rubbish in page name
				pageName = pageName.replaceAll("(\\W)+", "_");
				pageName = pageName.replaceAll("(^(_)+|(_)+$)", "");
				
				/*sb = new StringBuilder();
				
				sb.append("Search Term: ").append(searchKey).append(lineSep);
				sb.append("Page ID: ").append(pageID).append(lineSep);
				sb.append("Page Name: ").append(pageName).append(lineSep).append(lineSep);*/
				
				// Set limit to 100 (Maximum allowed by Facebook)
				Reading reading = new Reading();
				reading.limit(100);
				
				ResponseList<Post> pagePostResults = facebook.getFeed(pageID, reading);
				
				// Some page return no posts
				if(pagePostResults.isEmpty())
					continue;
				
				for(Post post : pagePostResults) {
					
					String postMessage = post.getMessage();
					
					// Some post has no message
					if(postMessage == null)
						continue;
					
					final String finalMessage = postMessage;
					
					/*sb.append("Post ID: ").append(post.getId()).append(lineSep);
					sb.append("Post Date/Time: ").append(post.getCreatedTime().toString());
					sb.append(lineSep);
					sb.append("Post Message:").append(lineSep).append(postMessage);
					sb.append(lineSep).append(lineSep);*/
					
					executor.execute(new Thread() {
						public void run() {
							
							postJSONContent(
									"www.facebook.com/" + post.getId(),
									finalMessage);
							}
						});
				}
				
				/*// Write to file
				String fileName = "Results\\" + pageID + "_" + pageName + ".txt";
				FileIOHelper.writeToFile(sb.toString(), fileName);*/
				
				++validPages;
			}
			
			System.out.println("# of Valid Pages (>= 1 Non-null post): " + validPages);
			
		} catch (FacebookException e) {		
			e.printStackTrace();
		} catch (Exception e) {	
			e.printStackTrace();
		}
		
		executor.shutdown();
		
		System.out.println("Good count: " + _goodCount);
		System.out.println("Bad count: " + _badCount);
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
				_goodCount++;
				
			} else {
				
			    System.out.println("NOT GOOD! " + finalString);
			    _badCount++;
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
