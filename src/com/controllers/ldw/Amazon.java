package com.controllers.ldw;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.YahooQueryLanguage.YQLWrapper;
import com.ebay.*;


@Controller
@RequestMapping(value ="/amazon")
public class Amazon {
	
    @RequestMapping(value = {"/search"})
	public ModelAndView search(	 @RequestParam("query") String query
								,HttpServletRequest request) throws Exception 
    	{
   	 	
    	String results= "Items with the keyword : "+query;


    	YQLWrapper AmazonWrapper  = new YQLWrapper();
		String response = AmazonWrapper.queryAmazonItemsbyYQL(query);

    	 Map<String, Object> Map = new HashMap<>();
    	 Map.put("results", results);
    	 Map.put("response",response );
    	 Map.put("nextpage", "");
    	 Map.put("previouspage", "");
    	
    	
    	 return new ModelAndView("amazon/search", Map);
    	}
}
