/**
 * 
 * Fichero Index.java que incluye la interfaz de Index.
 * 
 * 
 * @version 1.0
 * 
 * Created on 08/02/2019  
 */
package es.uam.eps.bmi.search.index;

import es.uam.eps.bmi.search.index.freq.FreqVector;
import java.io.IOException;
import java.util.List;

/**
 * Interfaz principal del motor de busqueda. En ella se encuentran funciones 
 * que se usaran en la mayoria de las clases.
 *
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 * 
 */
public interface Index {
    
    /**
     * 
     * Devuelve una lista con todos los terminos del indice.
     * 
     * @return una lista con todos los terminos de del indice en cadena de 
     * caracteres
     * 
     * @throws IOException 
     */
    public List<String> getAllTerms() throws IOException ;
            
    /**
     * Devuelve la frecuencia de un termino en todo el indice
     * 
     * @param term el termino del que queremos saber su frecuencia
     * 
     * @return la frecuencia del termino en el indice
     * 
     * @throws IOException 
     */
    public long getTotalFreq(String term) throws IOException;
    
    /**
     * 
     * Devuelve un FreqVector (el vector de un documento) dado el ID de un documento
     * 
     * @param id el identificador de un documento
     * 
     * @return un FreqVector (el vector de un documento)
     * 
     * @throws IOException 
     */
    public FreqVector getDocVector(int id) throws IOException;
    
    /**
     * Devuelve el path de un documento
     * 
     * @param docID el ID del documento
     * 
     * @return el path de donde se encuentra el documento
     * 
     * @throws IOException 
     */
    public String getDocPath(int docID) throws IOException;
    
    /**
     * Dado un termino y un documento, devuelve la frecuencia de ese termino
     * en el documento 
     * 
     * @param term el termino
     * 
     * @param docID el identificador del documento
     * 
     * @return la frecuencia del termino en el documento
     * 
     * @throws IOException 
     */
    public long getTermFreq(String term, int docID) throws IOException;
    
    /**
     * Devuelve el numero de documentos que contienen el termino term
     * 
     * @param term el termino 
     * 
     * @return el numero de documentos
     * 
     * @throws IOException 
     */
    public long getDocFreq(String term) throws IOException;
    
    
}
