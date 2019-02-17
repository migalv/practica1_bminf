/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.vsm;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import java.io.IOException;

/**
 *
 * @author sergio
 */
public class VSMEngine extends AbstractEngine{
    
    public VSMEngine(Index idx) {
        super(idx);
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException {
        return null;
    }

    
}
