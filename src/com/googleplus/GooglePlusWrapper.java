package com.googleplus;

import com.google.api.client.http.javanet.NetHttpTransport;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.PlusRequestInitializer;
import com.google.api.services.plus.model.PeopleFeed;
import com.google.api.services.plus.model.Person;
import com.google.api.services.plus.model.Person.PlacesLived;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.sparql.vocabulary.FOAF;
import java.io.IOException;
import java.io.StringWriter;
import java.util.List;


public class GooglePlusWrapper {

	private static final String API_KEY = "AIzaSyAA1t79U3C5ZmSJtXCETCUDpvx6m-3sJPQ";

	public String SuspectName;
	public String ProfileId;
	public String FunctionGooglePlus;
	public String PeopleCount;
	
		
	public String queryPeopleProfile(){
		
	    StringWriter output = new StringWriter();
    	Model model = ModelFactory.createDefaultModel();
		JsonFactory jsonFactory = new JacksonFactory();
	    NetHttpTransport HttpTransport = new NetHttpTransport();
	    Plus plus = new Plus.Builder(HttpTransport, jsonFactory, null).setApplicationName("Google plus API")
	        .setGoogleClientRequestInitializer(new PlusRequestInitializer(API_KEY)).build();

	    Plus.People.Search searchPeople;
	    List<Person> people = null;
		try {
			searchPeople = plus.people().search(SuspectName);
		     searchPeople.setMaxResults(50L);
		      PeopleFeed peopleFeed = searchPeople.execute();
		      people = peopleFeed.getItems();
		      
		      String currentLocation= "";
		      int NumPlaces         =0; 
		      List<PlacesLived> PlacesLived;
		      //List<Organizations> Organizations;
		      
			        for (Person profile : people) {
			          
			        String UserId =profile.getId();
			        String LivedPlaces="";
			        
			        
			        Person User     = plus.people().get(UserId).execute();
			        PlacesLived     = User.getPlacesLived();
			    //    Organizations   = User.getOrganizations();
			        
			        if(PlacesLived != null){
			          NumPlaces   =   PlacesLived.size();
			          for(int i=0;i<NumPlaces;i++){
			            PlacesLived Place =PlacesLived.get(i);
			            if(Place.getPrimary() != null&& Place.getPrimary()==true){
			                  currentLocation= Place.getValue();
			            }else
			            {
			              LivedPlaces+=Place.getValue()+",";
			            }
			          
			          
			          }
			        }
			      
			        Resource ResourcePerson	= 
				        model.createResource(User.getUrl())
				           .addProperty(FOAF.accountName, User.getDisplayName())
				           .addProperty(FOAF.img,User.getImage().getUrl());
				           
			        if(currentLocation!=""){
				        ResourcePerson.addProperty(FOAF.based_near,currentLocation);
			        }
			        if(LivedPlaces!=""){
				        ResourcePerson.addProperty(FOAF.based_near,LivedPlaces);
			        }
			        if(User.getBirthday() != null){
			          ResourcePerson.addProperty(FOAF.birthday,User.getBirthday());
			        }
			        if(User.getNickname() != null){
			          ResourcePerson.addProperty(FOAF.nick,User.getNickname());
			        }
			        if(User.getOccupation() != null){
			          ResourcePerson.addProperty(FOAF.title,User.getOccupation());
			        }
//			        if(Organizations != null){
//			              int NumOrganizations = Organizations.size();
//			              for(int j=0;j<NumOrganizations;j++){
//			                ResourcePerson.addProperty((Property)FOAF.Organization,
//			                		model.createResource()
//			                				.addProperty(FOAF.title,Organizations.get(j).getType())
//			                				.addProperty(FOAF.name,Organizations.get(j).getName())
//			                );
//			              }
//			        }

			        }
			  
		      model.write(output, "TURTLE");
		      
		} catch (IOException e) {
		
			e.printStackTrace();
		}

	      return output.toString();
		
	}
	


}
	
	

