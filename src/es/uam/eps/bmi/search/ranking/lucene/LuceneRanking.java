/**
 * 
 * Fichero LuceneRanking.java.
 * 
 * 
 * @version 1.0
 * 
 * Created on 12/02/2019  
 */
package es.uam.eps.bmi.search.ranking.lucene;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.util.Iterator;
import org.apache.lucene.search.ScoreDoc;

/**
 * Clase LuceneRanking que implementa SearcRanking.
 *
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 * 
 */
public class LuceneRanking implements SearchRanking {
    ScoreDoc score[];
    Index idx;

    /**
     * Constructor de LuceneRanking
     * 
     * @param idx el indice
     * @param score lista de ScoreDocs del indice
     */
    public LuceneRanking(Index idx,ScoreDoc score[]){
        this.idx=idx;
        this.score=score;
    }

    /**
     * Devuelve el numero de documentos del indice
     * 
     * @return el numero de documentos del indice
     */
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
        return new LuceneRankingIterator(idx,score);
    }
}