/**
 * 
 * Fichero TextResultDocRenderer.java.
 * 
 * 
 * @version 1.0
 * 
 * Created on 14/02/2019  
 */
package es.uam.eps.bmi.search.ui;

import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase TextResultDocRenderer que extiende ResultsRenderer
 *
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 * 
 */
public class TextResultDocRenderer extends ResultsRenderer{
    SearchRankingDoc result;
        
    /**
     * Constructor de TextResultDocRenderer
     *
     * @param result el resultado de la busqueda 
     */
    public TextResultDocRenderer(SearchRankingDoc result) {
        this.result=result;
    }

    /**
     * Convierte a una String el resultado
     * 
     * @return devuelve en un String la puntuacion y el path de un documento
     */
    @Override
    public String toString() {
        try {
            return result.getScore() + " " + result.getPath();
        } catch (IOException ex) {
            Logger.getLogger(TextResultDocRenderer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "";
    }
    
}
