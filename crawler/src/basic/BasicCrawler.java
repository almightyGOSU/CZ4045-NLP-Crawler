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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

/**
 * @author Yasser Ganjisaffar
 */
public class BasicCrawler extends WebCrawler {

  private static final Pattern IMAGE_EXTENSIONS = Pattern.compile(".*\\.(bmp|gif|jpg|png)$");
  
  private static int goodCount = 0;
  /**
   * You should implement this function to specify whether the given url
   * should be crawled or not (based on your crawling logic).
   */
  @Override
  public boolean shouldVisit(Page referringPage, WebURL url) {
    String href = url.getURL().toLowerCase();
    // Ignore the url if it has an extension that matches our defined set of image extensions.
    if (IMAGE_EXTENSIONS.matcher(href).matches()) {
      return false;
    }

    // Only accept the url if it is in the "www.ics.uci.edu" domain and protocol is "http".
    return href.startsWith("http://www.ladyironchef.com/");
  }

  /**
   * This function is called when a page is fetched and ready to be processed
   * by your program.
   */
  @Override
  public void visit(Page page) {
      
	int docid = page.getWebURL().getDocid();
    String url = page.getWebURL().getURL();
    String domain = page.getWebURL().getDomain();
    String path = page.getWebURL().getPath();
    String subDomain = page.getWebURL().getSubDomain();
    String parentUrl = page.getWebURL().getParentUrl();
    String anchor = page.getWebURL().getAnchor();
    
    /*
     * misstamchiak.com exclusion
     * if (path.contains("/category/") || path.contains("/tag/") 
    		|| path.contains("/page/") || path.equals("/"))
    	return;
     */
    
    if (path.contains("/restaurants-index/") || path.contains("/contact/") 
    		|| path.contains("/about/") || path.contains("/advertising/") 
    		|| path.contains("/category/") || path.equals("/delicious/"))
  	  return;
    
    System.out.println("DocID: " + docid);
    System.out.println("URL: " + url);
    System.out.println("Domain: " + domain);
    System.out.println("Path: " + path);
    System.out.println("SubDomain: " + subDomain);
    System.out.println("ParentURL: " + parentUrl);
    System.out.println("Anchor: " + anchor);
    
    PrintWriter writer = null;
    File dir = new File (domain);
    dir.mkdir();
    
    logger.debug("Docid: {}", docid);
    logger.info("URL: {}", url);
    logger.debug("Domain: '{}'", domain);
    logger.debug("Sub-domain: '{}'", subDomain);
    logger.debug("Path: '{}'", path);
    logger.debug("Parent page: {}", parentUrl);
    logger.debug("Anchor text: {}", anchor);

    if (page.getParseData() instanceof HtmlParseData) {
      HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
      String text = htmlParseData.getText();
      String html = htmlParseData.getHtml();
     
      Document doc = Jsoup.parse(html);
      //doc.getElementById("comments").remove();
      
      /*
       * misstamchiak.com content class
      Element content = doc.select("div.par.post-content").first();
      */
      Element content = doc.select("div.entry-content").first();
      
      String textParsed = content.text();
      
      textParsed = textParsed.replaceAll
    		  ("(https?://)?([\\da-z\\.-]+)\\.([a-z\\.]{2,6})([/\\w \\.-]*)*/?","");
      textParsed = textParsed.replaceAll
    		  ("\\$?\\d+((:|\\.|,)[0-9]+)?([a-zA-Z]+)?", "");
      
      /*textParsed = textParsed.replaceAll
    		  ("(//)?(www|WWW)\\.[a-zA-Z0-9]+\\.[a-zA-Z/]+", "");*/
      
      textParsed = textParsed.replaceAll("é","e");
      
      //textParsed = textParsed.replaceAll("\\b\\-\\-+\\b", "");
      
      if (textParsed != null && !(textParsed.isEmpty())){
    	  /*
    	  try {
    			writer = new PrintWriter(domain + "/" + docid + ".txt", "UTF-8");
    		} catch (FileNotFoundException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		} catch (UnsupportedEncodingException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
    	  	
    	  	writer.println("DocID: " + docid + "\r\n");
    	    writer.println("URL: " + url + "\r\n");
    	    writer.println("Domain: " + domain + "\r\n");
    	    writer.println("Path: " + path + "\r\n");
    	    writer.println("SubDomain: " + subDomain + "\r\n");
    	    writer.println("ParentURL: " + parentUrl + "\r\n");
    	    writer.println("Anchor: " + anchor + "\r\n");
    	    writer.println(textParsed);
    	    writer.close();
    	    */
    	    jsonPost(url, textParsed);
      }
    	  
      Set<WebURL> links = htmlParseData.getOutgoingUrls();

      logger.debug("Text length: {}", text.length());
      logger.debug("Html length: {}", html.length());
      logger.debug("Number of outgoing links: {}", links.size());
    }

    Header[] responseHeaders = page.getFetchResponseHeaders();
    if (responseHeaders != null) {
      logger.debug("Response headers:");
      for (Header header : responseHeaders) {
        logger.debug("\t{}: {}", header.getName(), header.getValue());
      }
    }

    
    logger.debug("=============");
  }
  
  public void jsonPost (String url, String content) 
  {
	  try {
		  String baseurl = "http://nlp.bcdy.tk/source";
		  URL object = new URL(baseurl);

		  HttpURLConnection con = (HttpURLConnection) object.openConnection();
		  con.setDoOutput(true);
		  con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		  con.setRequestMethod("POST");
		  
		  JSONObject postContent = new JSONObject();

		  postContent.put("URL", url);
		  postContent.put("Source", "Web");
		  postContent.put("Content", content);
		  
		  String jsonString = (postContent.toString());
		  jsonString = jsonString.replaceAll("\\P{Print}", "");

		  OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
		  System.out.println(jsonString);
		  wr.write(jsonString);
		  wr.flush();
		  int HttpResult = con.getResponseCode(); 
		  if(HttpResult == HttpURLConnection.HTTP_OK || 
				  HttpResult == HttpURLConnection.HTTP_CREATED){
			  
			  goodCount++;
		  	System.out.println("GOOD!: " + goodCount);
		  } else {
		      System.out.println("NOT GOOD: " + HttpResult); 
		  }
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}  
  }
  
  public String cleanAwayUnicode (String content) 
  {
	  Pattern p = Pattern.compile("\\\\u(\\p{XDigit}{4})");
	  Matcher m = p.matcher(content);
	  StringBuffer buf = new StringBuffer(content.length());
	  while (m.find()) {
	    String ch = String.valueOf((char) Integer.parseInt(m.group(1), 16));
	    m.appendReplacement(buf, Matcher.quoteReplacement(ch));
	  }
	  m.appendTail(buf);
	  return buf.toString();
	  
  }
}
