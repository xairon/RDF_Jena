import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

public class readandmodifRDF {

    private static String namespaceProperty = "http://www.inria.fr/2007/09/11/humans.rdfs#";
    private static String namespaceInstance = "http://www.inria.fr/2007/09/11/humans.rdfs-instances#";

    public static void Add(String fichier,String target,String ressourcename,String ressourcevalue,String filename) throws IOException {
        //fichier rdf
        String source = fichier;
        //creation du model
        Model m = FileManager.get().loadModel( source, "RDF/XML" );
        //print du fichier rdf
        //m.write(System.out);

        //élément à trouver (ressource)
        Resource res = m.getResource( namespaceInstance + target );

        //*Ajoute d'une propriété âge setup à 16
        Property myproperty = m.getProperty(namespaceProperty+ressourcename);
        System.out.println(myproperty);
        res.removeAll(myproperty);
        res.addProperty(myproperty, ressourcevalue);
        //création d'un fichier de sortie pour sauvegarder les données ajoutées
        String fileName = filename;
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
    public static void showinfo(String fichier,String ressource) throws IOException {
        //fichier rdf
        String source = fichier;
        //creation du model
        Model m = FileManager.get().loadModel( source, "RDF/XML" );
        //print du fichier rdf
        //m.write(System.out);

        Resource res = m.getResource( namespaceInstance + ressource );
//affichage de toutes les propriétés de la ressource
        for (StmtIterator i = res.listProperties(); i.hasNext(); ) {
            Statement stmt = i.next();
            System.out.println( "Resource " + stmt.getSubject().getLocalName()/*getURI()*/ +
                    " has property " + stmt.getPredicate().getLocalName() +
                    " with value " + stmt.getObject() );
        }

    }
    public static void main(String[] args) throws IOException {

        String source = "human.rdf";
        String save = "out.rdf";

        showinfo(source,"Mark");
        Add(source,"Mark","age","16",save);
        showinfo(save,"Mark");

    }
}