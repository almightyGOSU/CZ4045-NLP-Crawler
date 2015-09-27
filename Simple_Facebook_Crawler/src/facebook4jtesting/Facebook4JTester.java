package facebook4jtesting;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.FbConst;
import util.JSONHelper;

import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Page;
import facebook4j.Post;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.conf.ConfigurationBuilder;

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
		
		// Using thread pool to post JSON data to back-end server
		ExecutorService executor = Executors.newFixedThreadPool(15);
		
		try {
			
			for(String searchKey : FbConst.KEYWORDS) {
			
				ResponseList<Page> pageResults = facebook.searchPages(searchKey);
				System.out.printf("# of Returned Pages for %s: %d%n",
						searchKey, pageResults.size());
				
				int validPages = 0;
				
				for(Page page : pageResults) {
					
					String pageID = page.getId();
					String pageName = page.getName();
					
					boolean bIgnorePage = false;
					for(String ignoreStr : FbConst.IGNORE_LIST) {
						if(pageName.toLowerCase().contains(ignoreStr)) {
							bIgnorePage = true;
							break;
						}
					}
					
					if(bIgnorePage)
						continue;
					
					// Replace all rubbish in page name
					pageName = pageName.replaceAll("(\\W)+", "_");
					pageName = pageName.replaceAll("(^(_)+|(_)+$)", "");
					
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
						
						executor.execute(new Thread() {
							public void run() {
								
								JSONHelper.postJSONContent(
										"www.facebook.com/" + post.getId(),
										finalMessage);
								}
							});
					}
					
					++validPages;
				}
				
				System.out.println("Search key: " + searchKey);
				System.out.println("# of Valid Pages (>= 1 Non-null post): " + validPages);
				
				System.out.println("Good count: " + JSONHelper.goodCount);
				System.out.println("Bad count: " + JSONHelper.badCount);
				
				JSONHelper.goodCount = 0;
				JSONHelper.badCount = 0;
			}
			
		} catch (FacebookException e) {		
			e.printStackTrace();
		} catch (Exception e) {	
			e.printStackTrace();
		}
		
		executor.shutdown();
	}

}
