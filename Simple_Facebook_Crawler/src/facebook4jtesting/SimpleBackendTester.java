package facebook4jtesting;

import util.JSONHelper;

public class SimpleBackendTester {

	public static void main (String [] args) {
		
		// Apple - 18, Banana - 18, Durian - 12, Orange - 6, Watermelon - 6
		JSONHelper.postJSONContent(
				"www.abc.com/123", "BANANA BANANA APPLE ORANGE BANANA. "
						+ "BANANA BANANA APPLE ORANGE BANANA. "
						+ "BANANA BANANA APPLE ORANGE BANANA. "
						+ "BANANA BANANA APPLE ORANGE BANANA. "
						+ "BANANA BANANA APPLE ORANGE BANANA. "
						+ "BANANA BANANA APPLE ORANGE BANANA.");
		JSONHelper.postJSONContent(
				"www.abc.com/124", "APPLE APPLE WATERMELON DURIAN DURIAN. "
						+ "APPLE APPLE WATERMELON DURIAN DURIAN. "
						+ "APPLE APPLE WATERMELON DURIAN DURIAN. "
						+ "APPLE APPLE WATERMELON DURIAN DURIAN. "
						+ "APPLE APPLE WATERMELON DURIAN DURIAN. "
						+ "APPLE APPLE WATERMELON DURIAN DURIAN.");
	}
}
