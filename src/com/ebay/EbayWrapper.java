package com.ebay;


import java.io.StringWriter;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import com.ebay.services.client.ClientConfig;
import com.ebay.services.client.FindingServiceClientFactory;
import com.ebay.services.finding.*;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.jena.*;


public class EbayWrapper {

    
	public String ResearchObject;
	public Integer numResults;
	public String FunctioneBay;
	public String LocationReported;
	public String DateReported;
	public String ItemsCount;

   public String FindCompletedItems() {
    	
	   StringWriter output = new StringWriter();


    	try {
    		
    		ClientConfig config = new ClientConfig();
    		config.setApplicationId("GeoBlake-2a65-4856-9009-aa5ef2b221c1");
    		FindingServicePortType serviceClient = 
    		FindingServiceClientFactory.getServiceClient(config);
    		FindCompletedItemsRequest request = new FindCompletedItemsRequest();
    		request.setKeywords(ResearchObject);
    		PaginationInput pi = new PaginationInput();
    		pi.setEntriesPerPage(numResults);
    		request.setPaginationInput(pi);
    		
    		request.setSortOrder(SortOrderType.BEST_MATCH);
    		
    		FindCompletedItemsResponse result = serviceClient.findCompletedItems(request);
    		
    		List<SearchItem> items = result.getSearchResult().getItem();
         	
    		
    		String Ontology = "GoodRelations";
         	String API = "EBAY";
         	String EntityName = "ProductOrService";
         	JenaFactory JenaFactory = new JenaFactory();	
    		
         	Model model = ModelFactory.createDefaultModel();

  	      	ItemsCount = Integer.toString(items.size());
    		
             if (items  != null) {
            	 
            	 JSONObject JSONObject = new JSONObject();
            	 
            	 JSONArray JSONArray = new JSONArray();
            	 
                 for (SearchItem item : items) {

                	 JSONObject JSONItem = new JSONObject();
                	 

                 	String ImageItem= item.getGalleryURL();
                 	
                 	if(ImageItem==null){
                 		ImageItem ="Resources/Images/no_product.jpg";
                 	}else{
                 		JSONItem.put("GalleryURL",item.getGalleryURL());
                 	}
                 	
     	       JSONItem.put("Title",item.getTitle());
     	       		JSONItem.put("hadPrimarySource", "EBAY");
     	        
     		        if(item.getCondition()!=null){
     	        	JSONItem.put("ConditionDisplayName",item.getCondition().getConditionDisplayName());
         	         	
     		        }
     	        
     		       if(item.getCountry() != null){
    		        	JSONItem.put("Country",item.getCountry());
    		        }
     		        
     		        
     		        if(item.getLocation() != null){
     		        	JSONItem.put("Location",item.getLocation());
     		        }
               	double Value= item.getSellingStatus().getCurrentPrice().getValue();
               	String Currency=item.getSellingStatus().getCurrentPrice().getCurrencyId();
     	    	JSONItem.put("CurrentPriceValue",item.getSellingStatus().getCurrentPrice().getValue());
     	    	JSONItem.put("CurrentPriceCurrency",item.getSellingStatus().getCurrentPrice().getCurrencyId());
     	    	
     	    	JSONItem.put("CurrentPriceValueCurrency",Value+" "+Currency);
    
             	model.add(JenaFactory.JSONtoModel(JSONItem,Ontology,API,EntityName));
             	model.write(output, "JSON-LD");
                 
                 }
             	
             	
             	
	
             	
  
             }
    		
    	} catch (Exception ex) {
    		ex.printStackTrace();
    	}

    	
    	return output.toString();
    }
    
}
