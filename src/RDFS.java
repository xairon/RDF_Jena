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

        }
    }
