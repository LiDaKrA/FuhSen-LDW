package com.controllers.ldw;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.YahooQueryLanguage.*;
//import com.googleplus.GooglePlusWrapper;


@Controller
@RequestMapping(value ="/googleplus")
public class GooglePlus {
	
    @RequestMapping(value = {"/search"})
	public ModelAndView search(	 @RequestParam("query") String query
								,@RequestParam("numResults") String numResults
								,@RequestParam(value = "pageToken"
								, required = false) String pageToken
								,HttpServletRequest request) throws Exception 
    	{
   	 	
    	String results= "Profiles from : "+query;

  //*********** Use of API GooglePlus ************  	
/*   GooglePlusWrapper GooglePlusWrapper = new GooglePlusWrapper();
   	 GooglePlusWrapper.SuspectName =query;
   	 String response = GooglePlusWrapper.queryPeopleProfile();
*/   	 //*********************************************
  	
    //*********** Use of Yahoo Query language ************  	
    	
		YQLWrapper YQLobject = new YQLWrapper();

		String response = YQLobject.queryGooglePlusPeoplebyYQL(query,numResults,pageToken,request.getQueryString());
    	
    	
    	
    	 Map<String, Object> Map = new HashMap<>();
    	 Map.put("response", response);
    	 Map.put("results", results);
    	 Map.put("nextpage", YQLWrapper.googleNextPage);
    	 Map.put("previouspage", YQLWrapper.googlePreviousPage);
    	 
    	 
    	 return new ModelAndView("googleplus/search", Map);
    	}
}
