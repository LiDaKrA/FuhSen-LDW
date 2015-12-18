package com.jena;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;

import com.hp.hpl.jena.datatypes.RDFDatatype;
import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.QuerySolution;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.rdf.model.Literal;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.util.FileManager;
import com.hp.hpl.jena.vocabulary.VCARD;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.vocabulary.*;


public class testOntology {

	public static void main(String[] args) throws IOException {


		
		// Create model from the file.
		String filename = "K:/9- Master in Computer Sciences/Thesis/2. Eclipse/Workspace/DataWrapper/src/com/jena/common_entities.omn";
	    Model model=ModelFactory.createDefaultModel();
	    model.read(new FileInputStream(filename),null,"TTL");
	    
	    String outputfile = "K:/9- Master in Computer Sciences/Thesis/2. Eclipse/Workspace/DataWrapper/src/com/jena/output.ttl";
	    
		FileWriter output = new FileWriter( outputfile);
		try {
		    model.write(output , "TTL" );
		}
		finally {
			output.close();
		}
		

		
		
		
		
		
		
		
		
		
		
		
		
	}

}
