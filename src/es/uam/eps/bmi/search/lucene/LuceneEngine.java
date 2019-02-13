package es.uam.eps.bmi.search.lucene;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.lucene.LuceneRanking;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

public class LuceneEngine extends AbstractEngine{
    QueryParser parser;
    IndexSearcher searcher;
    
    public LuceneEngine(String indexPath) throws IOException {
        super(new LuceneIndex(indexPath));
        searcher = new IndexSearcher();
        parser = new QueryParser("content", new StandardAnalyzer());
        
        
    }

    public SearchRanking search(String query, int cutoff) throws IOException{
        Query q=null;
        try {
            q = parser.parse(query);
        } catch (ParseException ex) {
            Logger.getLogger(LuceneEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        ScoreDoc result[] = searcher.search(q, cutoff).scoreDocs;
        
        return new LuceneRanking(result);
    }
}