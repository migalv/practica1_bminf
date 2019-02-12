/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.index;

import es.uam.eps.bmi.search.index.freq.FreqVector;

/**
 *
 * @author e341058
 */
public interface Index {
    
    
    public String[] getAllTerms();
            
    public String[] getTotalFreq(String term);
    
    public FreqVector getDocVector(int id);
    
    
}
