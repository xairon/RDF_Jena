import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.util.FileManager;

import java.io.IOException;
import java.io.InputStream;


public class queryRDF{
    public static void main(String[] args) throws IOException {
        // Create a model and load the data
        Model model = ModelFactory.createDefaultModel();
        InputStream in = FileManager.get().open("human.rdf");
        if (in != null)
            model.read(in, "");
        String rdf = "http://www.inria.fr/2007/09/11/humans.rdfs#";
        Property myitemvalue1 = model.createProperty( rdf + "Person" );
        Property myitemvalue2 = model.createProperty( rdf + "Man" );

        // Run the SPARQL query and get some results
        String getItemsLists = "" +
                "prefix rdf: <http://www.inria.fr/2007/09/11/humans.rdfs>\n" +
                "select ?x ?t where\n"+
                "{\n"+
                "?x rdf:type ?t\n"+
                "}";
        ResultSet results = QueryExecutionFactory.create( getItemsLists, model ).execSelect();

        // For each solution in the result set
        while ( results.hasNext() ) {

        }
    }
}
