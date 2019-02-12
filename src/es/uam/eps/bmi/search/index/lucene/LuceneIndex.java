/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.freq.FreqVector;
import java.nio.file.Paths;

/**
 *
 * @author e341058
 */
public class LuceneIndex implements Index{
    
    public LuceneIndex(String indexPath){
        Directory directory = FSDirectory.open(Paths.get("res/index"));
    }
    


    @Override
    public String[] getAllTerms() {
        
    }

    @Override
    public String[] getTotalFreq(String term) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public FreqVector getDocVector(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
