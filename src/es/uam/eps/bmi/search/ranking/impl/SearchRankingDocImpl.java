/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.ranking.impl;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.io.IOException;

/**
 *
 * @author sergio
 */
public class SearchRankingDocImpl extends SearchRankingDoc{
    Index index;
    ScoreDocImpl rankedDoc;
    
    public SearchRankingDocImpl (Index idx, ScoreDocImpl r) {
        index = idx;
        rankedDoc = r;
    }
    
    @Override
    public double getScore() {
        return rankedDoc.score;
    }

    public int getDocID() {
        return rankedDoc.doc;
    }

    @Override
    public String getPath() throws IOException {
        return index.getDocPath(rankedDoc.doc);
    } 

}
