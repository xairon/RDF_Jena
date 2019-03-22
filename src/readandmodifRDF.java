import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

public class readandmodifRDF {

    public static void Add(String fichier,String Namespace,String target,String ressourcename,String ressourcevalue,String filename) throws IOException {
        //fichier rdf
        String source = fichier;
        //creation du model
        Model m = FileManager.get().loadModel( source, "RDF/XML" );
        //print du fichier rdf
        //m.write(System.out);
        String namespace = Namespace;
        //élément à trouver (ressource)
        Resource res = m.getResource( namespace + target );

        //*Ajoute d'une propriété âge setup à 16
        Property myproperty = m.getProperty(namespace+ressourcename);
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
    public static void showinfo(String fichier, String Namespace,String ressource) throws IOException {
        //fichier rdf
        String source = fichier;
        //creation du model
        Model m = FileManager.get().loadModel( source, "RDF/XML" );
        //print du fichier rdf
        //m.write(System.out);
        String namespace = Namespace;
        Resource res = m.getResource( namespace + ressource );
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
        String namespace = "http://www.inria.fr/2007/09/11/humans.rdfs-instances#";
        showinfo(source,namespace,"Mark");
        Add(source,namespace,"Mark","age","16",save);
        showinfo(save,namespace,"Mark");

    }
}