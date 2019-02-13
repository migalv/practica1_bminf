package es.uam.eps.bmi.search.ranking.lucene;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.util.Iterator;
import org.apache.lucene.search.ScoreDoc;

public class LuceneRanking implements SearchRanking {
    ScoreDoc score[];
    Index idx;

    public LuceneRanking(Index idx,ScoreDoc score[]){
        this.idx=idx;
        this.score=score;
    }

    public int size(){
        return score.length;
    }

    @Override
    public Iterator<SearchRankingDoc> iterator() {
        return new LuceneRankingIterator(idx,score);
    }
}