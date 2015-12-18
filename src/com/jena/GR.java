package com.jena;


import com.hp.hpl.jena.rdf.model.*;
 
public class GR {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model model = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://purl.org/goodrelations/v1";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = model.createResource( NS );

    public static final Resource rest = model.createResource( "http://www.w3.org/1999/02/22-rdf-syntax-ns#rest" );
    public static final Resource first = model.createResource( "http://www.w3.org/1999/02/22-rdf-syntax-ns#first" );
    public static final Resource unionOf = model.createResource( "http://www.w3.org/2002/07/owl#unionOf" );
    public static final Resource type = model.createResource( "http://www.w3.org/1999/02/22-rdf-syntax-ns#type" );
    public static final Property label = model.createProperty( "http://www.w3.org/2000/01/rdf-schema#label" );
    public static final Resource isDefinedBy = model.createResource( "http://www.w3.org/2000/01/rdf-schema#isDefinedBy" );
    public static final Property comment = model.createProperty( "http://www.w3.org/2000/01/rdf-schema#comment" );
    public static final Resource domain = model.createResource( "http://www.w3.org/2000/01/rdf-schema#domain" );
    public static final Resource inverseOf = model.createResource( "http://www.w3.org/2002/07/owl#inverseOf" );
    public static final Resource range = model.createResource( "http://www.w3.org/2000/01/rdf-schema#range" );
    public static final Resource disjointWith = model.createResource( "http://www.w3.org/2002/07/owl#disjointWith" );
    public static final Resource subClassOf = model.createResource( "http://www.w3.org/2000/01/rdf-schema#subClassOf" );
    public static final Property displayPosition = model.createProperty( "http://purl.org/goodrelations/v1#displayPosition" );
    public static final Resource hasPrevious = model.createResource( "http://purl.org/goodrelations/v1#hasPrevious" );
    public static final Resource hasNext = model.createResource( "http://purl.org/goodrelations/v1#hasNext" );
    public static final Property deprecated = model.createProperty( "http://www.w3.org/2002/07/owl#deprecated" );
    public static final Resource subPropertyOf = model.createResource( "http://www.w3.org/2000/01/rdf-schema#subPropertyOf" );
    public static final Resource equivalentClass = model.createResource( "http://www.w3.org/2002/07/owl#equivalentClass" );
    public static final Property category = model.createProperty( "http://purl.org/goodrelations/v1#category" );
    public static final Property description = model.createProperty( "http://purl.org/goodrelations/v1#description" );
    public static final Resource equivalentProperty = model.createResource( "http://www.w3.org/2002/07/owl#equivalentProperty" );
    public static final Resource homepage = model.createResource( "http://xmlns.com/foaf/0.1/homepage" );
    public static final Property subject = model.createProperty( "http://purl.org/dc/elements/1.1/subject" );
    public static final Property contributor = model.createProperty( "http://purl.org/dc/elements/1.1/contributor" );
    public static final Property creator = model.createProperty( "http://purl.org/dc/elements/1.1/creator" );
    public static final Resource license = model.createResource( "http://purl.org/dc/terms/license" );
    public static final Property rights = model.createProperty( "http://purl.org/dc/elements/1.1/rights" );
    public static final Property title = model.createProperty( "http://purl.org/dc/elements/1.1/title" );
    public static final Property versionInfo = model.createProperty( "http://www.w3.org/2002/07/owl#versionInfo" );    
}
