/**
 * 
 * Fichero SearchRankingDocExt.java.
 * 
 * 
 * @version 1.0
 * 
 * Created on 11/02/2019  
 */
package es.uam.eps.bmi.search.ranking;

import org.apache.lucene.document.Document;

/**
 * Clase que extiende de SearchRankingDoc para una implementacion mas propia.
 * 
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 * 
 */
public class SearchRankingDocExt extends SearchRankingDoc{
    Document doc;
    double score;
    
    /**
     * Constructor de SearchRankingDocExt
     * 
     * @param doc el documento de la busqueda
     * 
     * @param score la puntuacion obtenida tras una busqueda en el documento
     * 
     */
    public SearchRankingDocExt(Document doc, Double score){
        this.doc=doc;
        this.score=score;
    }  
    
    /**
     * Devuelve el path donde se encuentra el documento
     * 
     * @return el path del documento
     */
    @Override
    public String getPath(){
        return this.doc.getField("path").stringValue();
    }
    
    /**
     * Devuelve la puntuacion obtenida del documento
     * 
     * @return La puntuacion obtenida del documento
     */
    @Override
    public double getScore(){
        return this.score;
    }
}