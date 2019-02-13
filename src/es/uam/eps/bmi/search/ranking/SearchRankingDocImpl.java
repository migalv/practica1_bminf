package es.uam.eps.bmi.search.ranking;

import org.apache.lucene.document.Document;


public class SearchRankingDocImpl extends SearchRankingDoc{
    Document doc;
    double score;
    
    public SearchRankingDocImpl(Document doc, Double score){
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