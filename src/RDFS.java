import org.apache.jena.query.*;
import org.apache.jena.rdf.model.*;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.rulesys.GenericRuleReasoner;
import org.apache.jena.reasoner.rulesys.Rule;
import org.apache.jena.util.FileManager;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.vocabulary.RDF;

import java.io.IOException;
import java.io.InputStream;

public class RDFS {
   public static void printStatements(Model m, Resource s,Property p, Resource o){
       for (StmtIterator i=m.listStatements(s,p,o);
            i.hasNext();){Statement stmt = i.nextStatement();
            System.out.println(" - " + PrintUtil.print(stmt));
       }
   }
        public static void main(String[] args) throws IOException {
            String SOURCE = "http://www.inria.fr/2007/09/11/humans.rdfs";
            String NS = SOURCE + "#";
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

            String listclasses = "    PREFIX owl: <http://www.w3.org/2002/07/owl#>\n" +
                    "    PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>\n" +
                    "     \n" +
                    "     \n" +
                    "    SELECT DISTINCT ?subject ?label ?supertype\n" +
                    "    WHERE {\n" +
                    "        { ?subject a owl:Class . } UNION { ?individual a ?subject . } .\n" +
                    "        OPTIONAL { ?subject rdfs:subClassOf ?supertype } .\n" +
                    "        OPTIONAL { ?subject rdfs:label ?label }\n" +
                    "    } ORDER BY ?subject";
            String ancestor =   "prefix rdfs:<http://www.inria.fr/2007/09/11/humans.rdfs#>\n"+
                    "select ?y ?a where {\n"+
                    "?x rdfs:hasAncestor ?a.\n"+
                    "?x rdfs:name ?y \n" +
                    "}";
            InfModel inf = ModelFactory.createInfModel(reasoner, model);
            //Resource Person = inf.getResource("http://www.inria.fr/2007/09/11/humans.rdfs#Lecturer");




            Query query = QueryFactory.create(ancestor);

            QueryExecution qe = QueryExecutionFactory.create(query, inf);
            ResultSet results = qe.execSelect();

            ResultSetFormatter.out(System.out,results , query);
            qe.close();
           // printStatements(inf, Person, RDF.type, null);


            System.out.println("////////////////////////////////////");

            String test = "prefix rdf:<http://www.w3.org/1999/02/22-rdf-syntax-ns#>\n"+
                    "prefix prop:<http://www.inria.fr/2007/09/11/humans.rdfs#>\n"+
                    "select ?y  where {\n"+
                    "?y rdf:type prop:Male\n"+
                    "}";

            Query query1 = QueryFactory.create(test);
            QueryExecution qExe = QueryExecutionFactory.create(query1, model);
            ResultSet result = qExe.execSelect();
            ResultSetFormatter.out(System.out, result, query1) ;

            Query query2 = QueryFactory.create(test);
            QueryExecution qExe2 = QueryExecutionFactory.create(query2, inf);
            ResultSet result2 = qExe2.execSelect();
            ResultSetFormatter.out(System.out, result2, query2) ;

        }
    }
