package com.controllers.ldw;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.*;


@Controller
@RequestMapping(value ="/ebay")
public class Ebay {
	
    @RequestMapping(value = {"/search"})
	public ModelAndView search(	 @RequestParam("query") String query
								,@RequestParam("numResults") Integer numResults
								,HttpServletRequest request) throws Exception 
    	{
   	 	
    	String results= "Items witht the keyword : "+query;


    	EbayWrapper EbayWrapper  = new EbayWrapper();
    	EbayWrapper.ResearchObject =query;
    	EbayWrapper.numResults =numResults;
    	
    	String response = EbayWrapper.FindCompletedItems();
    	 
    	 Map<String, Object> Map = new HashMap<>();
    	 Map.put("results", results);
    	 Map.put("response",response );
    	 Map.put("nextpage", "");
    	 Map.put("previouspage", "");
    	
    	
    	 return new ModelAndView("ebay/search", Map);
    	}
}
