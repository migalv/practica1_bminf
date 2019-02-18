/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.vsm;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.Index;
<<<<<<< HEAD
import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
=======
>>>>>>> f3cc5da8d2288a1e9f1316813a4adafc8391d21a
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.impl.ScoreDocImpl;
import es.uam.eps.bmi.search.ranking.impl.SearchRankingImpl;
import java.io.IOException;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.util.ArrayList;
import java.util.List;
import static org.apache.lucene.search.similarities.SimilarityBase.log2;

/**
 *
 * @author sergio
 */
public class VSMEngine extends AbstractEngine{
    public List<ScoreDocImpl> scoreDocs;
    String[] queryTerms;
    
<<<<<<< HEAD
    public VSMEngine(Index idx) throws IOException {
=======
    public VSMEngine(Index idx) {
>>>>>>> f3cc5da8d2288a1e9f1316813a4adafc8391d21a
        super(idx);
    }

    @Override
<<<<<<< HEAD
    public SearchRanking search(String query, int cutoff) throws IOException{
        scoreDocs = new ArrayList<>();
        int numDocs = ((LuceneIndex)index).getIndex().numDocs();
        
        queryTerms =  query.toLowerCase().split(" ");
        
        for(int i=0; i< numDocs; i++){
            float score = getScore(i);
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
=======
    public SearchRanking search(String query, int cutoff) throws IOException {
        return null;
    }
>>>>>>> f3cc5da8d2288a1e9f1316813a4adafc8391d21a

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


    private float getScore(int docID) throws IOException {
        float score=0;
        float modulo = 0;
        float sumaParcial=0;
        
        for(String queryTerm : queryTerms){
            long termFreq=index.getTermFreq(queryTerm, docID);
            long docFreq= index.getDocFreq(queryTerm);
            float tfResult=tf(termFreq);
            float idfResult=idf(docFreq);
            sumaParcial=(float) (tfResult*idfResult);
            
            score+= sumaParcial;
            modulo+= (pow(sumaParcial,2)); 
        }
        
        modulo= (float)sqrt(modulo);
        
        //controlar el 0 del modulo
        return score/modulo;
    }
}
