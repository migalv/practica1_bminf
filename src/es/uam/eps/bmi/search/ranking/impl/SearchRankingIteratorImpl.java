/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import es.uam.eps.bmi.search.ranking.SearchRankingIterator;
/**
 *
 * @author sergio
 */
public class SearchRankingIteratorImpl  implements SearchRankingIterator {
    ScoreDocImpl results[];
    Index index;
    int n = 0;

    public SearchRankingIteratorImpl (Index idx, ScoreDocImpl r[]) {
        index = idx;
        results = r;
    }
    
    // Empty result list
    public SearchRankingIteratorImpl () {
        index = null;
        results = new ScoreDocImpl[0];
    }
    
    @Override
    public boolean hasNext() {
        return n < results.length;
    }

    @Override
    public SearchRankingDoc next() {
        return new SearchRankingDocImpl(index, results[n++]);
    }
    
}
