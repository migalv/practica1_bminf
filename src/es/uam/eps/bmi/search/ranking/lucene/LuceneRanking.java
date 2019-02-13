package es.uam.eps.bmi.search.ranking.lucene;

import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.util.Iterator;
import org.apache.lucene.search.ScoreDoc;

public class LuceneRanking implements SearchRanking {
    ScoreDoc score[];

    public LuceneRanking(ScoreDoc score[]){
        this.score=score;
    }

    public int size(){
        return score.length;
    }

    @Override
    public Iterator<SearchRankingDoc> iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}