import org.apache.jena.query.*;
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


        // Run the SPARQL query and get some results
        String getItemsLists = "select * where {\n" +
                "  ?x ?p ?y\n" +
                "}";

        Query query = QueryFactory.create(getItemsLists);
        QueryExecution qExe = QueryExecutionFactory.create(query, model);
        ResultSet result = qExe.execSelect();
        ResultSetFormatter.out(System.out, result, query) ;
    }
}
