package com.facebook;

import org.springframework.social.connect.Connection;

import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.Page;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ListIterator;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;

public class FacebookWrapper {

	static private String apiKey	="666632290107411";
	static private String secretKey	="c95e52cd906c1ca3642ce421c1087280";
	
	//	static private String redirectUri ="http://localhost:8080/DataWrapper/facebook/oauth.html";
	static private String redirectUri ="http://linkeddatawrapper-dataextraction.rhcloud.com/ldw/facebook/oauth.html";
	
	public String Query(String oauthcode,String query,String type) throws IOException{
		
		
		FacebookConnectionFactory connectionFactory=new FacebookConnectionFactory(apiKey,secretKey);
        OAuth2Operations oauthOperations=connectionFactory.getOAuthOperations();		  


		AccessGrant accessGrant=oauthOperations.exchangeForAccess(oauthcode,redirectUri,null);
		Connection<Facebook> connection=connectionFactory.createConnection(accessGrant);
		Facebook facebook=connection.getApi();
		Model model = ModelFactory.createDefaultModel();
		StringWriter output = new StringWriter();
		if(type.equals("page")){
			PagedList<Page> PageResult = facebook.pageOperations().search(query);
			ListIterator<Page> PageResultIterator =PageResult.listIterator();
			
			while( PageResultIterator.hasNext() ) {
				 
				
				Page Page = PageResultIterator.next();
				  Page  = facebook.pageOperations().getPage(Page.getId());
				  
				  String about	= Page.getAbout();
				  String category = Page.getCategory();
				  String coverurl =null;
				  	if(Page.getCover()!=null){
					  coverurl=Page.getCover().getSource();
    				}
				  String id = Page.getId();
				  String link = Page.getLink();
				  String name = Page.getName();
				  String website = Page.getWebsite();
				  
				  if(link==null)
					  link="http://www.facebook.com/"+id;
				  Resource ResourcePage	= 
					        model.createResource(link);
					        
					if(about!=null){
						ResourcePage.addProperty(FOAF.topic, about);
					}
					if(category!=null){
						ResourcePage.addProperty(FOAF.theme, category);
					}
					if(coverurl!=null){
						ResourcePage.addProperty(FOAF.img, coverurl);
					}
					if(id!=null){
						ResourcePage.addProperty(FOAF.openid, id);
					}
					if(name!=null){
						ResourcePage.addProperty(FOAF.name, name);
					}
					if(website!=null){
						ResourcePage.addProperty(FOAF.page, website);
					}
					 
			}
			
			  model.write(output, "JSON-LD");

		
		}else if(type.equals("user")){
			
			System.out.println("In development...");
		}
		
		
		return  output.toString();
	}
	
public String oauth2(String query,String type) throws IOException{
		
		

		FacebookConnectionFactory connectionFactory=new FacebookConnectionFactory(apiKey,secretKey);
        OAuth2Operations oauthOperations=connectionFactory.getOAuthOperations();
		OAuth2Parameters params=new OAuth2Parameters();
		params.setRedirectUri(redirectUri);
		params.setScope("user_about_me,user_birthday,user_likes,user_status,public_profile,user_friends,email");
		String authorizeUrl=oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE,params);
		authorizeUrl += "&state="+query+"%type="+type;
		
		return authorizeUrl;
	}

	
	
	
}
