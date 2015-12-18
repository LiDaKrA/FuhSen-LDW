package com.jena;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import com.hp.hpl.jena.util.FileManager;
import ontologies.*;


public class JenaFactory {

	
	public static Model OntologyModel;
	private static String ldwhost= "http://linkeddatawrapper-dataextraction.rhcloud.com/resources/1.0/ldw/";
	private static String ldwOntology= "http://linkeddatawrapper-dataextraction.rhcloud.com/ontology/1.0/ldw/";
	private static FUHSEN FUHSEN = new FUHSEN();
	private static GR GOODRELATIONS = new GR();
	private static FOAF FOAF  = new FOAF();
	
	
	public JenaFactory() throws InvocationTargetException, NoSuchMethodException{
		try {

			OntologyModel =CreateOntology();
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		
	}
	

	public static void main(String[] args) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		OntologyModel = CreateOntology();
		
			String  API ="GOOGLEPLUS";
			String 	Ontology = "FUHSEN";		
			String 	APIProperty = "placesLived.value";
			
		
			String Property = GetOntologyProperty(OntologyModel,API,APIProperty,"Person");
			
			
			System.out.println(Property);
		
	}

	public static String GetOntologyProperty(Model OntologyModel,String property1,String property2,String property3) {
		

		String[] propertyArray = {property1,property2,property3};

			
		
		 String Property = sparql_query_Ontologyproperty(OntologyModel,propertyArray);
 
		 
		 //Remove trace #API
		if(Property != null)
			Property = Property.replace("#"+property1, "");
		 
		return Property;
		
	}
	
	public static Model CreateOntology() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, InvocationTargetException, NoSuchMethodException{
		
		
/********* Set up by ontology for hybrid search engine*****************/
String APIDefinition = "GOOGLEPLUS";
String RootNode = "Person";
String[][] ConfigurationOntologyGoogleplus = {
												{APIDefinition,"FOAF","birthday","birthday",RootNode},
												{APIDefinition,"FOAF","displayName","name",RootNode},
												{APIDefinition,"FOAF","name","name",RootNode},
												{APIDefinition,"FOAF","givenname","givenname","name"},
												{APIDefinition,"FOAF","familyName","family_name","name"},
												{APIDefinition,"FUHSEN","occupation","occupation",RootNode},
												{APIDefinition,"FUHSEN","organizations","organization",RootNode},
												{APIDefinition,"FUHSEN","name","organizationname","organization"},
												{APIDefinition,"FUHSEN","type","organizationtype","organization"},
												{APIDefinition,"FUHSEN","primary","organizationprimary","organization"},
												{APIDefinition,"FUHSEN","placesLived","placesLived",RootNode},
												{APIDefinition,"FUHSEN","value","livedAt","placesLived"},
												{APIDefinition,"FUHSEN","primary","placesLivedprimary","placesLived"},
												{APIDefinition,"FOAF","emails","mbox",RootNode},
												{APIDefinition,"FOAF","value","mbox","mbox"},
												{APIDefinition,"FOAF","image","Image",RootNode},
												{APIDefinition,"FOAF","url","depiction","Image"},
												{APIDefinition,"FUHSEN","hadPrimarySource","hadPrimarySource",RootNode}};										
										
APIDefinition = "EBAY";		
RootNode = "ProductOrService";
								
	String[][] ConfigurationOntologyeBay =	 {	{APIDefinition,"GOODRELATIONS","items","ProductOrService",RootNode},
												{APIDefinition,"FOAF","GalleryURL","depiction",RootNode},								
												{APIDefinition,"FUHSEN","CurrentPriceValueCurrency","price",RootNode},							
												{APIDefinition,"FUHSEN","Location","location",RootNode},								
												{APIDefinition,"FUHSEN","CityName","city",RootNode},									
												{APIDefinition,"FUHSEN","Country","country",RootNode},								
												{APIDefinition,"GOODRELATIONS","Title","description",RootNode},								
												{APIDefinition,"GOODRELATIONS","ConditionDisplayName","condition",RootNode},								
												{APIDefinition,"GOODRELATIONS","Name","name",RootNode},										
												{APIDefinition,"FUHSEN","hadPrimarySource","hadPrimarySource",RootNode}};	
	
	
								
								
								
APIDefinition = "AMAZON";
RootNode = "ProductOrService";
								
//String[][] ConfigurationOntologyAmazon = 	{	//{APIDefinition,"FOAF","Variations.Item.ImageSets.ImageSet.SmallImage.URL","depiction",RootNode},								
//												{APIDefinition,"FUHSEN","ListPrice","price",RootNode},							
//												{APIDefinition,"GOODRELATIONS","Label","description",RootNode},								
//												{APIDefinition,"GOODRELATIONS","Condition","condition",RootNode},								
//												{APIDefinition,"GOODRELATIONS","Title","name",RootNode},										
//												{APIDefinition,"FUHSEN","AMAZON","hadPrimarySource",RootNode}};
								
String[][] ConfigurationOntologyAmazon = 	{	//{APIDefinition,"FOAF","Variations.Item.ImageSets.ImageSet.SmallImage.URL","depiction",RootNode},								
												{APIDefinition,"GOODRELATIONS","ItemAttributes","ProductOrService",RootNode},
												{APIDefinition,"FUHSEN","ListPrice","price",RootNode},							
												{APIDefinition,"GOODRELATIONS","Label","description",RootNode},								
												{APIDefinition,"GOODRELATIONS","Condition","condition",RootNode},								
												{APIDefinition,"GOODRELATIONS","Title","name",RootNode},										
												{APIDefinition,"FUHSEN","AMAZON","hadPrimarySource",RootNode}};




		Model Model = ModelFactory.createDefaultModel();


		Property API = Model.createProperty(ldwOntology+"API");
		Property Ontology = Model.createProperty(ldwOntology+"Ontology");
		Property APIProperty = Model.createProperty(ldwOntology+"APIProperty");
		Property OntologyProperty = Model.createProperty(ldwOntology+"OntologyProperty");
		Property ResourceNode = Model.createProperty(ldwOntology+"ResourceNode");
		
		
		String[][][] ConfigurationOntologies = {ConfigurationOntologyGoogleplus,ConfigurationOntologyeBay,ConfigurationOntologyAmazon};
		
		for(int k=0 ; k <ConfigurationOntologies.length;k++){
			
			String[][] ConfigurationOntology = ConfigurationOntologies[k];
			
		    for(int j=0 ; j <ConfigurationOntology.length;j++){
	
		    	
				
				String APIValue= ConfigurationOntology[j][0];
				String OntologyValue= ConfigurationOntology[j][1];
				String APIPropertyValue=ConfigurationOntology[j][2];
				String OntologyPropertyValue= ConfigurationOntology[j][3];
				String ResourceNodeValue = ConfigurationOntology[j][4];
				
				String ResourceValue ="";
				if(OntologyValue.equals("FOAF")){
					ResourceValue = FOAF.getClass().getDeclaredField(OntologyPropertyValue).get(0).toString();
				}else if(OntologyValue.equals("GOODRELATIONS")){
					ResourceValue = getOntologyPropertyinClass(GOODRELATIONS,OntologyPropertyValue);
				}else if(OntologyValue.equals("FUHSEN")){
					ResourceValue = getOntologyPropertyinClass(FUHSEN,OntologyPropertyValue);
				}
				
				if(ResourceValue!=""){	
	
						        Model.createResource(ResourceValue+"#"+APIValue)
						           .addProperty(API, APIValue)
						           .addProperty(Ontology,OntologyValue)
						           .addProperty(APIProperty, APIPropertyValue)
						           .addProperty(OntologyProperty,OntologyPropertyValue)
						           .addProperty(ResourceNode,ResourceNodeValue);
				}
		    
		    }
		}

		
//		 Model.write( System.out, "RDF/XML");
        
		 return Model;
	}
	
	
	 public static String getOntologyPropertyinClass(Object object, String property) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		 	Object returnValue = null;
	        String methodName = "get" + property;
            Class ontology = object.getClass();
            Method method = ontology.getMethod(methodName, null);
            returnValue = method.invoke(object, null);
	        return returnValue.toString();
	    
	 }
	
	
	
	
	public static String sparql_query_Ontologyproperty(Model model,String[] propertyArray){
		int Arraysize =propertyArray.length; 
		
		String API = "<"+ldwOntology+"API>";
		String Ontology = "<"+ldwOntology+"Ontology>";
		String APIProperty = "<"+ldwOntology+"APIProperty>";
		String OntologyNode = "<"+ldwOntology+"ResourceNode>";
		

		
		String whereString = "WHERE {";

		whereString +=	 "?OntologyProperty " +API +" \""+propertyArray[0]+"\" . "
						+"?OntologyProperty " +APIProperty +" \""+propertyArray[1]+"\" . "
						+"?OntologyProperty " +OntologyNode +" \""+propertyArray[2]+"\" . ";
					    
	
		
		
		
		
		
		String StringQuery = "SELECT DISTINCT ?OntologyProperty "	+ whereString + "}";
		
		Query query = QueryFactory.create(StringQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query,model);
		String objectResult = null;
		try{
			
			ResultSet results = qexec.execSelect();
			
			if(results.hasNext()){
				QuerySolution slon = results.nextSolution();
				objectResult = slon.get("?OntologyProperty").toString();
				
			}else{
				System.out.println("No configuration found for property.");
			}

		}catch(Exception Ex)
		{
			System.out.println("Error"+Ex.getMessage());
		}finally
		{
			qexec.close();
		}
		
		return objectResult;
		
	}
	

	
	public Model JSONtoModel(JSONObject JSONObject,String Ontology,String API, String EntityName) throws Exception {
		
		Model Model = ModelFactory.createDefaultModel();
		
	    Iterator<?> keys = JSONObject.keys();
	    

	    String UniqueId = UUID.randomUUID().toString();

	    Resource Entity = Model.createResource(ldwhost+API+"/"+EntityName+"/"+UniqueId);
	    while( keys.hasNext() ) {
	        String key = (String)keys.next();
	         if ( JSONObject.get(key) instanceof String ) {
	        	 
	        	 String OntologyProperty = GetOntologyProperty(OntologyModel,API,key,EntityName);
	        	 if(OntologyProperty !=null){
	        		 Property Property = Model.createProperty(OntologyProperty);
	        		 Entity.addProperty(Property, (String) JSONObject.get(key));
		        	  System.out.println(key+" : "+JSONObject.get(key)); 	 
	        	 }
	         	   
	         }else 
	         if ( JSONObject.get(key) instanceof JSONObject ) {

	        	 String OntologyProperty = GetOntologyProperty(OntologyModel,API,key,EntityName);
	        	 if(OntologyProperty !=null){
	        		 Property Property = Model.createProperty(OntologyProperty);
	        		 
	        		 System.out.println(key+" : ");
	        		 UniqueId = UUID.randomUUID().toString();
	        		 
		         	 Resource Resource = JSONObjectToResource((JSONObject) JSONObject.get(key),Model,key,UniqueId,Ontology,API,EntityName);
	        		 if(Resource!=null){
	        			 Entity.addProperty(Property, Resource);
	        		 }
	        	 }
	         }else 
	         if ( JSONObject.get(key) instanceof JSONArray ) {
	         	System.out.println(key+" : ");
	         	
	         	JSONArray JSONArray= (org.json.JSONArray) JSONObject.get(key);
	         	for (int i= 0; i<JSONArray.length();i++){  

	        	 String OntologyProperty = GetOntologyProperty(OntologyModel,API,key,EntityName);
		        	if(OntologyProperty !=null){
		        		UniqueId = UUID.randomUUID().toString();
	        		
		         		Resource Resource = JSONObjectToResource((JSONObject) JSONArray.get(i),Model,key,UniqueId,Ontology,API,EntityName);
		         		if(Resource!=null){
			         		Property Property = Model.createProperty(OntologyProperty);
			         		Entity.addProperty(Property, Resource);
		         		}
		        	}
	         	}
	         	   
	         }
	    }

	return Model;
	
	}
	
	
	public Resource JSONObjectToResource(JSONObject JSONObject,Model Model,String ResourceName,String UniqueId,String Ontology, String API, String EntityName ) throws Exception {

	 	 String OntologyProperty = GetOntologyProperty(OntologyModel,API,ResourceName,EntityName);
	 	 Resource Resource = null;
	 	 if(OntologyProperty !=null){

	 		
	 		String ldwproperty = OntologyProperty.substring(OntologyProperty.lastIndexOf("/")+1);
	 		if (ldwproperty.lastIndexOf("#") != -1 )
 				ldwproperty = ldwproperty.substring(ldwproperty.lastIndexOf("#")+1);
	 		 
		   Resource = Model.createResource(ldwhost+ldwproperty+"/"+UniqueId);
		
		   Iterator<?> Keys = JSONObject.keys();
			while( Keys.hasNext() ) {
				String Key = (String)Keys.next();
    			 if ( JSONObject.get(Key) instanceof String ) {

    		     OntologyProperty = GetOntologyProperty(OntologyModel,API,Key,ldwproperty);	
    				 
    		     if(OntologyProperty !=null){
	   	        	  Property Property =  Model.createProperty(OntologyProperty);
	   	        	  Resource.addProperty(Property, (String) JSONObject.get(Key));
	   	        	  System.out.println(" -> "+Key+" : "+ JSONObject.get(Key));
    		     }	 
    			 
    			 
    			 }
			}
     	}
			
			return Resource;
	}
	
	
	
		
	
	
	

	
	
	
}
