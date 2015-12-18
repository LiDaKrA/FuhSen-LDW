package ontologies;


import com.hp.hpl.jena.rdf.model.*;
 
public class FUHSEN {
    /** <p>The RDF model that holds the vocabulary terms</p> */
    private static Model model_fuhsen = ModelFactory.createDefaultModel();
    
    /** <p>The namespace of the vocabulary as a string</p> */
    public static final String NS = "http://unibonn.eis.de/fuhsen/common_entities/";
    
    /** <p>The namespace of the vocabulary as a string</p>
     *  @see #NS */
    public static String getURI() {return NS;}
    
    /** <p>The namespace of the vocabulary as a resource</p> */
    public static final Resource NAMESPACE = model_fuhsen.createResource( NS );

    public static final Property address= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/address");
    public static final Property city= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/city");
    public static final Property country= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/country");
    public static final Property occupation= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/occupation");
    public static final Property organization= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/organization");
    public static final Property organizationname= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/organizationname");
    public static final Property organizationtype= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/organizationtype");
    public static final Property organizationprimary= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/organizationprimary");
    public static final Property studiedAt= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/studiedAt");
    public static final Property workedAt= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/workedAt");
    public static final Property nationality= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/nationality");
    public static final Property placesLived= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/placesLived");
    public static final Property placesLivedprimary= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/placesLivedprimary");
    public static final Property livedAt= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/livedAt");
    public static final Property hadPrimarySource= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/hadPrimarySource");
    public static final Property price= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/price");
    public static final Property location= model_fuhsen.createProperty("http://unibonn.eis.de/fuhsen/common_entities/location");
    
    public static final Property getaddress(){ return address;}
    public static final Property getcity(){ return city;}
    public static final Property getcountry(){ return country;}
    public static final Property getoccupation(){ return occupation;}
    public static final Property getorganization(){ return organization;}
    public static final Property getorganizationname(){ return organizationname;};
    public static final Property getorganizationtype (){ return organizationtype;};
    public static final Property getorganizationprimary (){ return organizationprimary;};
    public static final Property getstudiedAt(){ return studiedAt;}
    public static final Property getworkedAt(){ return workedAt;}
    public static final Property getnationality(){ return nationality;}
    public static final Property getplacesLived(){ return placesLived;}
    public static final Property getplacesLivedprimary(){ return placesLivedprimary;}
    public static final Property getlivedAt(){ return livedAt;}
    public static final Property gethadPrimarySource(){ return hadPrimarySource;}
    public static final Property getprice(){ return price;}
    public static final Property getlocation(){ return location;}
    
}
