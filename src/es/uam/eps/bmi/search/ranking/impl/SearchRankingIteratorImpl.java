/**
 * 
 * Fichero SearchRankingIteratorImpl.java.
 * 
 * 
 * @version 1.0
 * 
 * Created on 11/02/2019  
 */
package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import es.uam.eps.bmi.search.ranking.SearchRankingIterator;

/**
 * Clase SearchRankingIteratorImpl que implementa SearchRankingIterator.
 *
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 * 
 */
public class SearchRankingIteratorImpl  implements SearchRankingIterator {
    ScoreDocImpl results[];
    Index index;
    int n = 0;

    /**
     * Constructor de SearchRankingIteratorImpl.
     * 
     * @param idx el indice
     * @param r lista de ScoreDocs del indice
     */
    public SearchRankingIteratorImpl (Index idx, ScoreDocImpl r[]) {
        index = idx;
        results = r;
    }
    
    /**
     * Constructor vacio de SearchRankingIteratorImpl.
     */
    public SearchRankingIteratorImpl () {
        index = null;
        results = new ScoreDocImpl[0];
    }
    
    /**
     * Devuelve si quedan mas elementos en el iterador
     * 
     * @return  true si quedan elementos o false en caso contrario
     */
    @Override
    public boolean hasNext() {
        return n < results.length;
    }

    /**
     * Devuelve el proximo elemento de ScoreDoc
     * 
     * @return el elemento siguiente de ScoreDocImpl
     */
    @Override
    public SearchRankingDoc next() {
        return new SearchRankingDocImpl(index, results[n++]);
    }
    
}
