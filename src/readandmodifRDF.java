
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

public class readandmodifRDF {

    public static void main(String[] args) throws IOException {
        //fichier rdf
        String source = "/home/xairon/RDF_Jena/human.rdf";
        //creation du model
        Model m = FileManager.get().loadModel( source, "RDF/XML" );
        //print du fichier rdf
        //m.write(System.out);
        String namespace = "http://www.inria.fr/2007/09/11/humans.rdfs-instances#";
        //élément à trouver (ressource)
        Resource mark = m.getResource( namespace + "Harry" );

        //*Ajoute d'une propriété âge setup à 16
        Property myproperty = m.getProperty(namespace+"age");
        mark.addProperty(myproperty, "16");
//affichage de toutes les propriétés de la ressource
    for (StmtIterator i = mark.listProperties(); i.hasNext(); ) {
            Statement stmt = i.next();
            System.out.println( "Resource " + stmt.getSubject().getURI() +
                    " has property " + stmt.getPredicate().getURI() +
                    " with value " + stmt.getObject() );
        }
    //création d'un fichier de sortie pour sauvegarder les données ajoutées
        String fileName = "testage.rdf";
        FileWriter out = new FileWriter( fileName );
        try {
            m.write( out, "RDF/XML-ABBREV" );
        }
        finally {
            try {
                out.close();
            }
            catch (IOException closeException) {
                // ignore
            }
        }

    }
}
