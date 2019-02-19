/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.test;

import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author sergio
 */
public class TermStats {
    
    public static void main (String args[]) throws IOException {
        LuceneIndex index = new LuceneIndex("index/docs");
        //LuceneIndex index = new LuceneIndex("index/urls");

        List<String> allTerms = new ArrayList<>(index.getAllTerms());
        Map <String, Integer> termMap = new HashMap<>();

        for(String term: allTerms){
            termMap.put(term, (int)index.getTotalFreq(term));
        }
        
        try (BufferedWriter file = Files.newBufferedWriter(Paths.get("./termfreq.txt"))) {
            termMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).forEach((t)->{try {
                String str=t.getKey()+"\t"+t.getValue()+"\n" ;
                file.write(str);
            } catch (IOException ex) {
                Logger.getLogger(TermStats.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            });
        }

        Map <String, Integer> termDocMap = new HashMap<>();

        for(String term: allTerms){
            termDocMap.put(term, (int)index.getDocFreq(term));
        }
        
        try (BufferedWriter fileDoc = Files.newBufferedWriter(Paths.get("./termdocfreq.txt"))) {
            termDocMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).forEach((d)->{try {
                String str=d.getKey()+"\t"+d.getValue()+"\n" ;
                fileDoc.write(str);
            } catch (IOException ex) {
                Logger.getLogger(TermStats.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            });
        }

        
    }

}

