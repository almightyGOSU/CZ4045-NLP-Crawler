package facebook4jtesting;

import util.JSONHelper;

public class SimpleBackendTester {

	public static void main (String [] args) {
		
		// Apple - 3, Banana - 3, Durian - 1, Orange - 1, Watermelon - 1
		JSONHelper.postJSONContent("www.abc.com/123", "BANANA BANANA APPLE ORANGE BANANA");
		JSONHelper.postJSONContent("www.abc.com/124", "APPLE APPLE WATERMELON DURIAN");
	}
}
