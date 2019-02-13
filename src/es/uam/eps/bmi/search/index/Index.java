/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.index;

import es.uam.eps.bmi.search.index.freq.FreqVector;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author e341058
 */
public interface Index {
    
    public List<String> getAllTerms() throws IOException ;
            
    public long getTotalFreq(String term) throws IOException;
    
    public FreqVector getDocVector(int id) throws IOException;
    
    public String getDocPath(int docID) throws IOException;
    
    public long getTermFreq(String term, int docID) throws IOException;
    
    public long getDocFreq(String term) throws IOException;
    
    
}
