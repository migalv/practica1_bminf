/**
 * 
 * Fichero SearchRankingImpl.java.
 * 
 * 
 * @version 1.0
 * 
 * Created on 11/02/2019  
 */
package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.util.Iterator;

/**
 * Clase SearchRankingImpl que implementa SearchRanking.
 *
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 * 
 */
public class SearchRankingImpl implements SearchRanking {
    Index idx;
    ScoreDocImpl score[];

    /**
     * Constructor de SearchRankingImpl.
     * 
     * @param idx el indice
     * 
     * @param score la lista de ScoreDocs del indice
     */
    public SearchRankingImpl(Index idx,ScoreDocImpl score[]){
        this.idx=idx;
        this.score=score;
    }

    /**
     * Devuelve el numero de documentos del indice
     * 
     * @return el numero de documentos del indice
     */
    @Override
    public int size(){
        return score.length;
    }

    /**
     * Iterador de los documentos del indice
     * 
     * @return todos los ScoreDocs del indice de forma iterada
     */
    @Override
    public Iterator<SearchRankingDoc> iterator() {
        return new SearchRankingIteratorImpl(idx,score);
    }

    
}
