/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.vsm;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.impl.ScoreDocImpl;
import es.uam.eps.bmi.search.ranking.impl.SearchRankingImpl;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.lucene.search.similarities.SimilarityBase.log2;

/**
 *
 * @author sergio
 */
public class VSMEngine extends AbstractEngine{
    public List<ScoreDocImpl> scoreDocs;
    String[] queryTerms;
    
    public VSMEngine(Index idx) throws IOException {
        super(idx);
    }

    @Override
    public SearchRanking search(String query, int cutoff) throws IOException, FileNotFoundException{
        scoreDocs = new ArrayList<>();
        int numDocs = ((LuceneIndex)index).getIndex().numDocs();
        
        queryTerms =  query.toLowerCase().split(" ");
        
        for(int i=0; i< numDocs; i++){
            float score = 0;
            try {
                score = getScore(i);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(VSMEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(score>0){
                scoreDocs.add(new ScoreDocImpl(i,score));
            }
        }
        
        int max= (scoreDocs.size()<cutoff)? scoreDocs.size() : cutoff;
        ScoreDocImpl scoreFinal[]= new ScoreDocImpl[max];
        scoreDocs.sort((s1,s2)-> Float.compare(s2.score, s1.score));
      
        for(int i=0; i< max;i++){
            scoreFinal[i]=scoreDocs.get(i);
        }

        return new SearchRankingImpl(this.index, scoreFinal);
    }
    
    private float tf(double freq){
        if(freq > 0){
            return (float) (1+log2(freq));
        }
        else{
            return 0;
        }
    }
    
    private float idf(double freq){ 
        int numDocs=((LuceneIndex) index).getIndex().numDocs();
        return  (float) (log2(1+numDocs/(freq+1)));
    }


    private float getScore(int docID) throws IOException, FileNotFoundException, ClassNotFoundException {
        float score=0;
        float sumaParcial=0;
        HashMap<Integer, Float> modulos;

        modulos = ((LuceneIndex)index).readModulo();
        
        float moduloParcial= modulos.get(docID);
        
        for(String queryTerm : queryTerms){
            long termFreq=index.getTermFreq(queryTerm, docID);
            long docFreq= index.getDocFreq(queryTerm);
            float tfResult=tf(termFreq);
            float idfResult=idf(docFreq);
            sumaParcial=(float) (tfResult*idfResult);
            
            score+= sumaParcial;
        }
        
        return (moduloParcial>0)? (score/moduloParcial): score;
    }
}
