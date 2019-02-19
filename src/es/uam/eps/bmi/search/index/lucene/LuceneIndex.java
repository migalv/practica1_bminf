/**
 * 
 * Fichero LuceneIndex.java que implementa la interfaz de Index y maneja
 * el indice de Lucene.
 * 
 * 
 * @version 1.0
 * 
 * Created on 09/02/2019  
 */
package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.freq.FreqVector;
import es.uam.eps.bmi.search.index.freq.lucene.LuceneFreqVector;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.index.Terms;



/**
 * 
 * Implementa la interfaz de Index y provee las funciones necesarias para 
 * manejar un indice de Lucene.
 * 
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 */
public class LuceneIndex implements Index{
    IndexReader index;
    Directory directory;
    List<String> termList;
    private Map<Integer, Float> modulos= new HashMap<>();
    String indexPath;
    
    
    /**
     * Constructor de LuceneIndex
     * 
     * @param indexPath el path del indice a crear
     * 
     * @throws IOException 
     */
    public LuceneIndex(String indexPath) throws IOException{
        directory = FSDirectory.open(Paths.get(indexPath));
        index = DirectoryReader.open(directory);
        this.indexPath=indexPath;
        
        termList = new ArrayList<>();
        TermsEnum terms;
        
        terms = MultiFields.getFields(index).terms("content").iterator();
        
        while (terms.next() != null){
            termList.add(terms.term().utf8ToString());
        }
        
    }
    
    /**
     * Devuelve todos los terminos del indice
     * 
     * @return todos los terminos del indice
     * 
     * @throws IOException 
     */
    @Override
    public List<String> getAllTerms() throws IOException {
        return termList;
    }

    /**
     * Devuelve la frecuencia de un termino en el indice
     * 
     * @param term el termino
     * 
     * @return la frecuencia del termino
     * 
     * @throws IOException 
     */
    @Override
    public long getTotalFreq(String term) throws IOException{
        Term termInstance= new Term("content",term);
        
        return index.totalTermFreq(termInstance);
    }

    /**
     * Devuelve el vector de un documento
     * 
     * @param id el ID del documento
     * 
     * @returnel vector de dicho documento
     * 
     * @throws IOException 
     */
    @Override
    public FreqVector getDocVector(int id) throws IOException{
        Terms docVector = index.getTermVector(id, "content"); 
        
        return new LuceneFreqVector(docVector);
    }
    
    /**
     * 
     * Devuelve el indice actual
     * 
     * @return el indice
     */
    public IndexReader getIndex(){ return this.index; }
    
    /**
     * Devuelve el directorio donde se encuentra el indice
     * 
     * @return el directorio donde se encuentra el indice
     *
     */
    public Directory getDirectory(){ return this.directory; }

    /**
     * 
     * Devuelve el path donde se encuentra un documento
     * 
     * @param docID el ID del documento
     * 
     * @return el path donde se encuentra
     * 
     * @throws IOException 
     */
    @Override
    public String getDocPath(int docID) throws IOException { 
        return index.document(docID).get("path"); 
    }

    /**
     * 
     * Devuelve la frecuencia de un termino en un documento
     * 
     * @param term el termino
     * 
     * @param docID el ID del documento
     * 
     * @return la frecuencia del termino
     * 
     * @throws IOException 
     */
    @Override
    public long getTermFreq(String term, int docID) throws IOException {
        
        return this.getDocVector(docID).getFreq(term);
    }

    /**
     * Devuelve el numero de documentos donde esta presente el termino term
     * 
     * @param term el termino
     * 
     * @return el numero de documentos
     * 
     * @throws IOException 
     */
    @Override
    public long getDocFreq(String term) throws IOException {
        return index.docFreq(new Term("content",term));
    }
    
    /**
     * Devuelve un HashMap con los modulos de los documentos
     * 
     * @return un HashMap con los modulos de los documentos del indice
     * 
     * @throws FileNotFoundException
     * @throws IOException
     * @throws ClassNotFoundException 
     */
    public HashMap readModulo() throws FileNotFoundException, IOException, ClassNotFoundException{
        
        //si ya se ha creado el hashmap lo devolvemos
        if(this.modulos.size() > 0){
            return (HashMap) this.modulos;
        }
        
        //leemos el fichero e insertamos por cada documento su respectivo modulo calculado
        try (FileInputStream fileIn = new FileInputStream(Paths.get(indexPath+File.separator+"modulos.txt").toString()); ObjectInputStream in = new ObjectInputStream(fileIn)) {
            modulos = (HashMap) in.readObject();
        }
        
        return (HashMap) this.modulos;
    }    
}
