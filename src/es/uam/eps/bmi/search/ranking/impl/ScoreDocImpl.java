/**
 * 
 * Fichero ScoreDocImpl.java.
 * 
 * 
 * @version 1.0
 * 
 * Created on 11/02/2019  
 */
package es.uam.eps.bmi.search.ranking.impl;

/**
 * Clase ScoreDocImpl que tiene un documento y su respectiva puntuacion.
 *
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 * 
 */
public class ScoreDocImpl {
    public float score;
    public int doc;

    /**
     * Constructor de ScoreDocImpl
     * 
     * @param doc el documento 
     * @param score la puntuacion del documento
     */
    public ScoreDocImpl(int doc, float score) {
        this.score= score;
        this.doc=doc;
    }
    
    
}
