/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.ranking.impl;

import static org.apache.lucene.search.similarities.SimilarityBase.log2;

/**
 *
 * @author sergio
 */
public class ScoreDocImpl {
    public float score;
    public int doc;

    public ScoreDocImpl(int doc, float score) {
        this.score= score;
        this.doc=doc;
    }
    
    
}
