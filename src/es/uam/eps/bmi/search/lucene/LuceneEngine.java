/**
 * 
 * Fichero LuceneEngine.java que incluye la busqueda de Lucene
 * 
 * 
 * @version 1.0
 * 
 * Created on 05/02/2019  
 */
package es.uam.eps.bmi.search.lucene;

import es.uam.eps.bmi.search.AbstractEngine;
import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.lucene.LuceneRanking;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;


/**
 * Clase que implementa el buscador del motor de busqueda de Lucene
 *
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 * 
 */
public class LuceneEngine extends AbstractEngine{
    QueryParser parser;
    IndexSearcher searcher;
    
    /**
     * Constructor de LuceneEngine
     * 
     * @param indexPath el path de donde se encuentra el indice
     * 
     * @throws IOException 
     */
    public LuceneEngine(String indexPath) throws IOException {
        super(new LuceneIndex(indexPath));
        searcher = new IndexSearcher(((LuceneIndex) this.index).getIndex());
        parser = new QueryParser("content", new StandardAnalyzer());
        
    }

    /**
     * Funcion de busqueda dada una query
     * 
     * @param query terminos a buscar en el indice
     * 
     * @param cutoff maximo de archivos a mostrar
     * 
     * @return el resultado de la query tras buscar en el indice
     * 
     * @throws IOException 
     */
    @Override
    public SearchRanking search(String query, int cutoff) throws IOException{
        Query q=null;
        //parseamos la query y buscamos 
        try {
            q = parser.parse(query);
        } catch (ParseException ex) {
            Logger.getLogger(LuceneEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        ScoreDoc result[] = searcher.search(q, cutoff).scoreDocs;
        
        return new LuceneRanking(this.index,result);
    }
}