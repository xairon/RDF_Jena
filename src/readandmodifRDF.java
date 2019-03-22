
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import org.apache.jena.rdf.model.*;
import org.apache.jena.util.FileManager;

public class readandmodifRDF {
    public static void  Save(Model m,String filename) throws IOException {
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
    public static Model Add(String fichier,String Namespace,String target,String ressourcename,String ressourcevalue){
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
        res.addProperty(myproperty, ressourcevalue);
        return m;
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

        String source = "/home/xairon/RDF_Jena/human.rdf";
        String namespace = "http://www.inria.fr/2007/09/11/humans.rdfs-instances#";
        showinfo(source,namespace,"Mark");
    }
}
