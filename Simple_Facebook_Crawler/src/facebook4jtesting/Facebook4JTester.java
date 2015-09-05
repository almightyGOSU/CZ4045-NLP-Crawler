package facebook4jtesting;

import java.io.File;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Page;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;

/** This is an empty class **/
/** You just got smoked! **/

public class Facebook4JTester {

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
		
		try {
			
			// Make a directory in case it goes crazy..
			// WARNING: You have been warned!!
			new File("Results").mkdirs();
			
			StringBuilder sb = null;
			String lineSep = System.getProperty("line.separator");
			
			String searchKey = "Singapore Food";
			
			ResponseList<Page> pageResults = facebook.searchPages(searchKey);
			System.out.println("# of Returned Pages: " + pageResults.size());
			
			int validPages = 0;
			
			for(Page page : pageResults) {
				
				String pageID = page.getId();
				String pageName = page.getName();
				
				sb = new StringBuilder();
				
				sb.append("Search Term: ").append(searchKey).append(lineSep);
				sb.append("Page ID: ").append(pageID).append(lineSep);
				sb.append("Page Name: ").append(pageName).append(lineSep).append(lineSep);
				
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
					
					sb.append("Post ID: ").append(post.getId()).append(lineSep);
					sb.append("Post Date/Time: ").append(post.getCreatedTime().toString());
					sb.append(lineSep);
					sb.append("Post Message:").append(lineSep).append(postMessage);
					sb.append(lineSep).append(lineSep);
				}
				
				// Replace all rubbish in page name
				pageName = pageName.replaceAll("(\\W)+", "_");
				pageName = pageName.replaceAll("(^(_)+|(_)+$)", "");
				
				// Write to file
				String fileName = "Results\\" + pageID + "_" + pageName + ".txt";
				FileIOHelper.writeToFile(sb.toString(), fileName);
				
				++validPages;
			}
			
			System.out.println("# of Valid Pages (>= 1 Non-null post): " + validPages);
			
		} catch (FacebookException e) {		
			e.printStackTrace();
		} catch (Exception e) {	
			e.printStackTrace();
		}
	}

}
