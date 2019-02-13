package es.uam.eps.bmi.search.ranking;

import java.io.IOException;


public abstract class SearchRankingDoc implements Comparable<SearchRankingDoc> {
        
    public int compareTo(SearchRankingDoc o) {
        
        if(this.getScore() > o.getScore()){
            return 1;
        }else if(this.getScore() < o.getScore()){
            return -1;
        }else{
            return 0;
        }
        
    }
    
    public abstract String getPath() throws IOException;
    
    public abstract double getScore();
}