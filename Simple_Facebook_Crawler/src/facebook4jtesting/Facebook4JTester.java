package facebook4jtesting;

import facebook4j.Event;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.Group;
import facebook4j.Post;
import facebook4j.ResponseList;
import facebook4j.User;
import facebook4j.conf.ConfigurationBuilder;

/** This is an empty class **/

public class Facebook4JTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/** You just got smoked! **/
		
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

			/*ResponseList<Post> postResults = facebook.searchPosts("Food");*/
			
			ResponseList<User> userResults = facebook.searchUsers("Gosu Jin Yao");	
			for(User u : userResults) {
				
				if(u.getName().equalsIgnoreCase("Gosu Jin Yao")) {
				
					System.out.println(u.getId());
					System.out.println(u.getName());
					System.out.println();
				}
			}
			
			/*ResponseList<Event> eventResults = facebook.searchEvents("Food");	
			for(Event e : eventResults) {
				
				System.out.println(e.getId());
				System.out.println(e.getName());
				System.out.println(e.getLocation());
				System.out.println();
			}*/
			
			ResponseList<Group> groupResults = facebook.searchGroups("NTU");
			for(Group g : groupResults) {
				
				String gName = g.getName();
				
				if(gName.contains("Computer Science")) {
				
					System.out.println(g.getId());
					System.out.println(g.getName());
					System.out.println();
				}
			}
			
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
