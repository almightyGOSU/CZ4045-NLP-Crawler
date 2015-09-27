/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package basic;

import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import util.JSONHelper;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Yasser Ganjisaffar
 */
public class BasicCrawler extends WebCrawler {

	private static final Pattern IMAGE_EXTENSIONS = Pattern
			.compile(".*\\.(bmp|gif|jpg|png)$");

	/**
	 * You should implement this function to specify whether the given url
	 * should be crawled or not (based on your crawling logic).
	 */
	@Override
	public boolean shouldVisit(Page referringPage, WebURL url) {
		
		String href = url.getURL().toLowerCase();
		
		// Ignore the url if it has an extension that matches our defined set of
		// image extensions.
		if (IMAGE_EXTENSIONS.matcher(href).matches()) {
			return false;
		}
		
		// return href.startsWith("http://www.ladyironchef.com/");
		
		return href.startsWith("http://www.misstamchiak.com/");
	}

	/**
	 * This function is called when a page is fetched and ready to be processed
	 * by your program.
	 */
	@Override
	public void visit(Page page) {

		final String url = page.getWebURL().getURL();
		String path = page.getWebURL().getPath();
		
		/*int docid = page.getWebURL().getDocid();
		String domain = page.getWebURL().getDomain();
		String subDomain = page.getWebURL().getSubDomain();
		String parentUrl = page.getWebURL().getParentUrl();
		String anchor = page.getWebURL().getAnchor();*/

		
		// misstamchiak.com exclusion
		if (path.contains("/category/") || path.contains("/tag/")
				|| path.contains("/page/") || path.equals("/"))
			return;
		 

		/*if (path.contains("/restaurants-index/") || path.contains("/contact/")
				|| path.contains("/about/") || path.contains("/advertising/")
				|| path.contains("/category/") || path.equals("/delicious/"))
			return;*/

		/*System.out.println("DocID: " + docid);
		System.out.println("URL: " + url);
		System.out.println("Domain: " + domain);
		System.out.println("Path: " + path);
		System.out.println("SubDomain: " + subDomain);
		System.out.println("ParentURL: " + parentUrl);
		System.out.println("Anchor: " + anchor);*/

		/*PrintWriter writer = null;
		File dir = new File(domain);
		dir.mkdir();*/

		/*logger.debug("Docid: {}", docid);
		logger.info("URL: {}", url);
		logger.debug("Domain: '{}'", domain);
		logger.debug("Sub-domain: '{}'", subDomain);
		logger.debug("Path: '{}'", path);
		logger.debug("Parent page: {}", parentUrl);
		logger.debug("Anchor text: {}", anchor);*/

		if (page.getParseData() instanceof HtmlParseData) {
			
			HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
			//String text = htmlParseData.getText();
			String html = htmlParseData.getHtml();

			Document doc = Jsoup.parse(html);
			// doc.getElementById("comments").remove();

			
			// misstamchiak.com content class
			Element content = doc.select("div.par.post-content").first();
			
			/*// For ladyironchef content class
			Element content = doc.select("div.entry-content").first();*/
			
			final String textParsed = content.text();

			if (textParsed != null && !(textParsed.isEmpty())) {
				/*
				 * try { writer = new PrintWriter(domain + "/" + docid + ".txt",
				 * "UTF-8"); } catch (FileNotFoundException e1) { // TODO
				 * Auto-generated catch block e1.printStackTrace(); } catch
				 * (UnsupportedEncodingException e1) { // TODO Auto-generated
				 * catch block e1.printStackTrace(); }
				 * 
				 * writer.println("DocID: " + docid + "\r\n");
				 * writer.println("URL: " + url + "\r\n");
				 * writer.println("Domain: " + domain + "\r\n");
				 * writer.println("Path: " + path + "\r\n");
				 * writer.println("SubDomain: " + subDomain + "\r\n");
				 * writer.println("ParentURL: " + parentUrl + "\r\n");
				 * writer.println("Anchor: " + anchor + "\r\n");
				 * writer.println(textParsed); writer.close();
				 */
				
				JSONHelper.postJSONContent(url, textParsed);
			}

			/*Set<WebURL> links = htmlParseData.getOutgoingUrls();*/

			/*logger.debug("Text length: {}", text.length());
			logger.debug("Html length: {}", html.length());
			logger.debug("Number of outgoing links: {}", links.size());*/
		}

		/*Header[] responseHeaders = page.getFetchResponseHeaders();
		if (responseHeaders != null) {
			logger.debug("Response headers:");
			for (Header header : responseHeaders) {
				logger.debug("\t{}: {}", header.getName(), header.getValue());
			}
		}

		logger.debug("=============");*/
	}

	/*public String cleanAwayUnicode(String content) {
		Pattern p = Pattern.compile("\\\\u(\\p{XDigit}{4})");
		Matcher m = p.matcher(content);
		StringBuffer buf = new StringBuffer(content.length());
		while (m.find()) {
			String ch = String.valueOf((char) Integer.parseInt(m.group(1), 16));
			m.appendReplacement(buf, Matcher.quoteReplacement(ch));
		}
		m.appendTail(buf);
		return buf.toString();
	}*/
}
