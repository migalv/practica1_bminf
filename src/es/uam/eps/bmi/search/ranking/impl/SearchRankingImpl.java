/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.util.Iterator;

/**
 *
 * @author sergio
 */
public class SearchRankingImpl implements SearchRanking {
    Index idx;
    ScoreDocImpl score[];

    public SearchRankingImpl(Index idx,ScoreDocImpl score[]){
        this.idx=idx;
        this.score=score;
    }

    @Override
    public int size(){
        return score.length;
    }

    @Override
    public Iterator<SearchRankingDoc> iterator() {
        return new SearchRankingIteratorImpl(idx,score);
    }

    
}
