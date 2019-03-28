import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.util.FileManager;

import java.io.IOException;
import java.io.InputStream;

public class RDFS {

        public static void main(String[] args) throws IOException {
            // Create a model and load the data
            Model model = ModelFactory.createDefaultModel();
            Model schema = ModelFactory.createOntologyModel();
            InputStream in = FileManager.get().open("human.rdf");
            InputStream in2 = FileManager.get().open("human.rdfs");

            if (in != null)
                model.read(in, "");
            if (in2 != null)
                schema.read(in2, "");
            Reasoner reasoner = ReasonerRegistry.getRDFSReasoner();
            reasoner = reasoner.bindSchema(schema);
            InfModel infmodel = ModelFactory.createInfModel(reasoner, model);
            for (StmtIterator i = infmodel.listStatements(); i.hasNext(); ) {
                Statement stmt = i.next();
                System.out.println("Resource " + stmt.getSubject().getLocalName()/*getURI()*/ +
                        " has property " + stmt.getPredicate().getLocalName() +
                        " with value " + stmt.getObject());
            }

            System.out.println("////////////////////////////////////");

            String test = "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
                    "prefix prop:<http://www.inria.fr/2007/09/11/humans.rdfs#>\n"+
                    "select ?y  where {\n"+
                    "?y rdf:type prop:Male\n"+
                    "}";

            Query query = QueryFactory.create(test);
            QueryExecution qExe = QueryExecutionFactory.create(query, model);
            ResultSet result = qExe.execSelect();
            ResultSetFormatter.out(System.out, result, query) ;

            Query query2 = QueryFactory.create(test);
            QueryExecution qExe2 = QueryExecutionFactory.create(query2, infmodel);
            ResultSet result2 = qExe2.execSelect();
            ResultSetFormatter.out(System.out, result2, query2) ;

        }
    }
