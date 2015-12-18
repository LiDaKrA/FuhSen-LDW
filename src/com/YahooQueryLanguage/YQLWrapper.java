package com.YahooQueryLanguage;

import java.io.*;
import java.net.URLEncoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;
//import org.apache.jena.atlas.json.JsonObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.api.client.json.Json;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.jena.*;



public class YQLWrapper {

	public static String googleNextPage="";
	public static String googlePreviousPage="";

	
	public String queryGooglePlusPeoplebyYQL(String Profile,String numResults,String pageToken,String queryString) throws Exception {
		
		
	    StringWriter output = new StringWriter();
		Model model = ModelFactory.createDefaultModel();
		
		Profile= URLEncoder.encode(Profile, "UTF-8");
		String Endpoint = "http://query.yahooapis.com/v1/public/yql?q=";
		String URLTable = "USE%20%22http://www.datatables.org/google/google.plus.people.search.xml%22%3B";
		String Table = "google.plus.people.search";
		String APPkey= "AIzaSyAA1t79U3C5ZmSJtXCETCUDpvx6m-3sJPQ";
		String Format= "&format=json";
		String Query = "SELECT%20*%20FROM%20"+Table+"%20WHERE%20key%3D%22"+APPkey+"%22%20AND%20query%3D%22"+Profile+"%22%20and%20maxResults%20%3D%22"+numResults+"%22%20";

		if(pageToken!=null){
			Query = Query+ "and%20pageToken%3D%22"+pageToken+ "%3D%22";
			
		}
		String request = Endpoint + URLTable + Query+Format;
		String response="";
		String  prevToken ="";
		String NextPageToken= "";
		googleNextPage	="";
		googlePreviousPage="";
		
		
		String BodyResponse = httpGetCall(request);


	               try {
	                   JSONObject jsonObject =new JSONObject(BodyResponse);
	                   
	                   
	                   
	                   if(jsonObject.getJSONObject("query").getJSONObject("results").getJSONObject("json").has("nextPageToken"))
	                	   NextPageToken = jsonObject.getJSONObject("query").getJSONObject("results").getJSONObject("json").get("nextPageToken").toString();

	                   
	                   	if(jsonObject.getJSONObject("query").getJSONObject("results").getJSONObject("json").get("items") instanceof JSONObject ){

	                   		JSONObject Person = (JSONObject) jsonObject.getJSONObject("query").getJSONObject("results").getJSONObject("json").get("items");
	                        String idPerson = Person.get("id").toString();
	                        System.out.println(idPerson);
	                        response += queryGooglePlusPersonbyYQL(idPerson);
	                   
	                   	}else if(jsonObject.getJSONObject("query").getJSONObject("results").getJSONObject("json").get("items") instanceof JSONArray ){
	                   		
	                        JSONArray jsonPeople = (JSONArray) jsonObject.getJSONObject("query").getJSONObject("results").getJSONObject("json").get("items");

	                        for(int i = 0; i < jsonPeople.length(); i++)
	                        {
	                              JSONObject Person = jsonPeople.getJSONObject(i);
	                              String idPerson = Person.get("id").toString();
	                              System.out.println(idPerson);
	                              
	                              model.add(queryGooglePlusPersonbyYQL(idPerson));
	                        }

	                        model.write(output, "JSON-LD");
	                        response += output.toString();
	                        
	                        
	                   	}
	                                      
	               }
	               catch (JSONException e) {
	                  
	                   e.printStackTrace();
	               }

	        if(NextPageToken!=""){
	        	
	        	if( queryString.indexOf("pageToken=")!= -1)
	        	{
	            	prevToken =queryString.substring(queryString.indexOf("pageToken=")+"pageToken=".length());
	            	String prevQueryString= "search.html?"+ queryString;
	            	String nextQueryString = "search.html?"+ queryString.replace(prevToken, NextPageToken);
	            	googleNextPage= "<a href=\""+nextQueryString+"\">Next page</a>";
	            	googlePreviousPage= "<a href=\""+prevQueryString+"\">Previous page</a>";
	            	
	            	
	        	}else{
	        		
	        		queryString = "search.html?"+ queryString+"&pageToken="+NextPageToken;
	                googleNextPage= "<a href=\""+queryString+"\">Next page</a>";        	
	                googlePreviousPage="";
	        	}
	            
	        }else{
	        	googleNextPage	="";
	        	googlePreviousPage="";
	        }

		return response;
	}
		
	public Model queryGooglePlusPersonbyYQL(String id) throws Exception {

		String Endpoint = "http://query.yahooapis.com/v1/public/yql?q=";
		String URLTable = "USE%20%22http://www.datatables.org/google/google.plus.people.xml%22%3B";
		String Table = "google.plus.people";
		String APPkey= "AIzaSyAA1t79U3C5ZmSJtXCETCUDpvx6m-3sJPQ"; // change to static 
		String Query = "SELECT%20*%20FROM%20"+Table+"%20WHERE%20key%3D%22"+APPkey+"%22%20AND%20userId%3D%22"+id+"%22&format=json";
		String request = Endpoint + URLTable + Query;


		JenaFactory JenaFactory = new JenaFactory();		
		Model model = ModelFactory.createDefaultModel();

		
		
		String BodyResponse = httpGetCall(request);

	        	
       try {
           JSONObject jsonObject =new JSONObject(BodyResponse);
           
           JSONObject jsonPerson = jsonObject.getJSONObject("query").getJSONObject("results").getJSONObject("json");
           
           String Ontology = "FOAF";
           String API = "GOOGLEPLUS";
           String EntityName = "Person";
           
           jsonPerson.put("hadPrimarySource","GOOGLE+");
           model.add(JenaFactory.JSONtoModel(jsonPerson,Ontology,API,EntityName));
        
           
        	
           }
           catch (JSONException e) {
              
               e.printStackTrace();
           }

		return model;
			
		}
	

	
	public String queryAmazonItemsbyYQL(String Item) throws Exception {
		
		
	    StringWriter output = new StringWriter();
		Model model = ModelFactory.createDefaultModel();

		JenaFactory JenaFactory = new JenaFactory();
		
		Item= URLEncoder.encode(Item, "UTF-8");
		String Endpoint = "http://query.yahooapis.com/v1/public/yql?q=";
		String URLTable = "USE%20%22http://www.datatables.org/amazon/amazon.ecs.xml%22%3B";
		String Table = "amazon.ecs";

		String Operation="ItemSearch";
		String SearchIndex="All";
		String Keywords =Item;
		String AWSAccessKeyId ="AKIAIXTSR3IBYL3VCKLQ";
		String secret="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";
		String AssociateTag= "geo020-20";
		
		String wherefields = "%20Operation=%22"+Operation+"%22%20AND%20SearchIndex=%22"+SearchIndex+"%22%20AND%20Keywords=%22"+Keywords+"%22%20AND%20AWSAccessKeyId=%22"+AWSAccessKeyId+"%22%20AND%20secret=%22"+secret+"%22%20AND%20AssociateTag=%22"+AssociateTag+"%22";
		String Format= "&format=json";
		String Query = "SELECT%20*%20FROM%20"+Table+"%20WHERE" + wherefields;

		
		String request = Endpoint + URLTable + Query+Format;
		String response="";

		
		String BodyResponse = httpGetCall(request);

		
		

	               try {
	            	   JSONObject jsonObject =new JSONObject(BodyResponse);

	                   	if(jsonObject.getJSONObject("query").getJSONObject("results").get("Item") instanceof JSONObject ){

	                   		JSONObject Person = (JSONObject) jsonObject.getJSONObject("query").getJSONObject("results").getJSONObject("json").get("items");
	                        String idPerson = Person.get("id").toString();
	                        System.out.println(idPerson);
	                   
	                   	}else if(jsonObject.getJSONObject("query").getJSONObject("results").get("Item") instanceof JSONArray ){
	                   		
	                        JSONArray jsonItems = (JSONArray) jsonObject.getJSONObject("query").getJSONObject("results").get("Item");
	                        
	                        
	                        for(int i = 0; i < jsonItems.length(); i++)
	                        {
	                              JSONObject JsonObjectItem = (JSONObject) jsonItems.getJSONObject(i);

	                              String Ontology = "GoodRelations";
	                              String API = "AMAZON";
	                              String EntityName = "ProductOrService";

	                              model.add(JenaFactory.JSONtoModel(JsonObjectItem,Ontology,API,EntityName));
	                              
	           	               	  
	                            
	                        }

	                        model.write(output, "JSON-LD");
	                        response += output.toString();
	                        
	                   	}
	                     

	            	   
	               }
	               catch (JSONException e) {
	                  
	                   e.printStackTrace();
	               }


		return response;
	}

	
	public String httpGetCall(String Request) throws Exception {
    	
		String file="";
    	String line;		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpGet = new HttpGet(Request);
		CloseableHttpResponse responseHTTP = httpclient.execute(httpGet);
		
		int statusCode = responseHTTP.getStatusLine().getStatusCode();
	    
		if (statusCode != HttpStatus.SC_OK) {
			System.err.println("Method failed: " + responseHTTP.getStatusLine());
	    }else {
            InputStream rstream = null;
            HttpEntity entity = responseHTTP.getEntity();
            rstream = entity.getContent();

        	BufferedReader br = new BufferedReader(new InputStreamReader(rstream));


        	while ((line = br.readLine()) != null) 
        	{	file+=line;
        	}
	            br.close();
	        }
		
		
		return file;
	}
		
	
	
	
}
