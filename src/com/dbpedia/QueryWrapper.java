package dbpedia;

import java.util.HashMap;
import java.util.Map;

import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.rdf.model.Literal;

public class QueryWrapper {
	
	//To delete
	public String Description;
	public String ReportedDate;
	public String Place;
	public String SuspectPerson;
	public String Object;
	public String CaseId;
	public String SuspectId;
	public String SuspectNameProfile;
	public String FunctionFuseki;

	private String dbpediaEndPoint = "http://dbpedia.org/sparql";
	 
	public HashMap<String, DbpediaPerson> searchPerson(String keyWord) {
		
		HashMap<String, DbpediaPerson> resultsMap = new HashMap<String, DbpediaPerson>(); 
		//java.util.List<String> resultList = new java.util.ArrayList<String>();
		 
		QueryExecution qexec = QueryExecutionFactory.sparqlService(dbpediaEndPoint,
				"PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#> "
				+ "	PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "
				+ "	PREFIX type: <http://dbpedia.org/class/yago/> "
				+ "	PREFIX prop: <http://dbpedia.org/property/> "
				+ "	PREFIX foaf: <http://xmlns.com/foaf/0.1/> "
				+ "	PREFIX dbpprop: <http://dbpedia.org/property/> "
				+ "	PREFIX dbpedia-owl: <http://dbpedia.org/ontology/> "
				+ " SELECT * WHERE { "
				+ " 	?person a dbpedia-owl:Person . "
				+ "		?person foaf:name ?name . "
				+ "		?person dbpedia-owl:birthDate ?birthDate . "
				+ "		?person dbpprop:birthPlace ?birthPlace . "
				+ "		?person dbpedia-owl:thumbnail ?thumbnail . "
				+ "		?person dbpprop:shortDescription ?shortDescription . "
				+ "		OPTIONAL { 	?person dbpedia-owl:abstract ?abstract . FILTER ( lang (?abstract) = 'en') . } "
				+ "		FILTER ( lang (?name) = 'en' && regex(?name , '"+keyWord+"', 'i') ) . "
				+ " } ");

		ResultSet results = qexec.execSelect();
		
		System.out.println("Executing searchPerson with keyword: "+keyWord+" hasNext: "+results.hasNext());
		
		while(results.hasNext()){
			 QuerySolution row = results.nextSolution();
			 
			 DbpediaPerson dbpPerson = new DbpediaPerson();
			 dbpPerson.ressourceUri = row.get("person").toString();
			 dbpPerson.name = row.getLiteral("name").getString();
			 dbpPerson.birthDate = row.getLiteral("birthDate").getString();
			 
			 try{
				 dbpPerson.thumbnail = row.get("thumbnail").toString();
			 }catch(Exception ex)
			 {
				 System.out.println("Exception getting thumbnail: "+ex.getMessage());
				 dbpPerson.thumbnail = "";
			 }
			 
			 try{
				 dbpPerson.shortDescription = row.getLiteral("shortDescription").getString();
			 }catch(Exception ex)
			 {
				 System.out.println("Exception getting thumbnail: "+ex.getMessage());
				 dbpPerson.shortDescription = "";
			 }
			 
			 try{
				 dbpPerson.birthPlace = row.get("birthPlace").toString();
			 }catch(Exception ex)
			 {
				 System.out.println("Exception getting birthplace: "+ex.getMessage());
				 dbpPerson.birthPlace = "";
			 }
			 
			 if (row.getLiteral("abstract") != null)
				 dbpPerson.dbpAbstract = row.getLiteral("abstract").getString();
			 else
				 dbpPerson.dbpAbstract = "";
			 
			//Review in next iteration - using name as key
			if (!resultsMap.containsKey(dbpPerson.name.toLowerCase()))
				resultsMap.put(dbpPerson.name.toLowerCase(), dbpPerson);
			 
		}
		
		return resultsMap;
	 }

}
