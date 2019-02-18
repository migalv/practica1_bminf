package es.uam.eps.bmi.search.ranking;

import org.apache.lucene.document.Document;


public class SearchRankingDocExt extends SearchRankingDoc{
    Document doc;
    double score;
    
    public SearchRankingDocExt(Document doc, Double score){
        this.doc=doc;
        this.score=score;
    }  
    
    @Override
    public String getPath(){
        return this.doc.getField("path").stringValue();
    }
    
    @Override
    public double getScore(){
        return this.score;
    }
}