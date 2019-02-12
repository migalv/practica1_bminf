package es.uam.eps.bmi.search.ranking;

public interface SearchRankingDoc implements Comparable<SearchRankingDoc> {

    public double getScore();

    public int getDocID();

    public String getPath();
}