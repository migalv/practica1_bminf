/**
 * 
 * Fichero SearchRankingDocImpl.java.
 * 
 * 
 * @version 1.0
 * 
 * Created on 11/02/2019  
 */
package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.io.IOException;

/**
 * Clase SearchRankingDocImpl que tiene un indice y un scoreDoc dado un documento
 *
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 * 
 */
public class SearchRankingDocImpl extends SearchRankingDoc{
    Index index;
    ScoreDocImpl rankedDoc;
    
    /**
     * Constructor de SearchRankingDocImpl
     * 
     * @param idx el indice del documento
     * @param r el scoreDoc del documento
     */
    public SearchRankingDocImpl (Index idx, ScoreDocImpl r) {
        index = idx;
        rankedDoc = r;
    }
    
    /**
     * Getter de la puntuacion del scoreDoc
     * 
     * @return la puntuacion del documento
     */
    @Override
    public double getScore() {
        return rankedDoc.score;
    }

    /**
     * Getter del ID del documento
     * 
     * @return el ID del documento
     */
    public int getDocID() {
        return rankedDoc.doc;
    }

    /**
     * Devuelve el path del documento
     * 
     * @return el path del documento
     * 
     * @throws IOException 
     */
    @Override
    public String getPath() throws IOException {
        return index.getDocPath(rankedDoc.doc);
    } 

}
