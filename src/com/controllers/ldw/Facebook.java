package com.controllers.ldw;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.HttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.facebook.*;

@Controller
@RequestMapping(value ="/facebook")
public class Facebook {
	
    @RequestMapping(value = {"/search"})
	public ModelAndView search(	 @RequestParam("query") String query
								,@RequestParam("type") String type
								,@RequestParam(value = "code", required = false) String oauthcode
								,HttpServletRequest request) throws Exception 
    	{

		FacebookWrapper FacebookWrapper  = new FacebookWrapper();
		String response= "";
		
    	if(oauthcode==null){
        	String authorizeUrl= FacebookWrapper.oauth2(query,type);
        	 return new ModelAndView("redirect:" + authorizeUrl);
    	}else{
    		response = FacebookWrapper.Query(oauthcode,query,type);
    		
    	}

    	
    	
    	 Map<String, Object> Map = new HashMap<>();
    	 Map.put("response", response);
    	 
    	 
    	 return new ModelAndView("facebook/search", Map);
    	}
    
    
    
    
    
    @RequestMapping(value = {"/oauth"})
	public ModelAndView oauth2(	 HttpServletRequest request) throws Exception 
    	{
    	 return new ModelAndView("facebook/oauth");
    	}
    
    
    
}
