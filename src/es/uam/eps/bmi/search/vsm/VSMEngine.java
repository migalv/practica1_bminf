/**
 * 
 * Fichero VSMEngine.java.
 * 
 * 
 * @version 1.0
 * 
 * Created on 14/02/2019  
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
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.lucene.search.similarities.SimilarityBase.log2;

/**
 * Clase VSMEngine que se encarga de nuestro motor de busqueda personal
 * realizado por nosotros.
 *
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 * 
 */
public class VSMEngine extends AbstractEngine{
    public List<ScoreDocImpl> scoreDocs;
    String[] queryTerms;
    
    /**
     * Constructor de VSMEngine
     * 
     * @param idx el indice del buscador
     * 
     * @throws IOException 
     */
    public VSMEngine(Index idx) throws IOException {
        super(idx);
    }

    /**
     * Funcion de busqueda en un indice dada una query
     * 
     * @param query la query a buscar
     * 
     * @param cutoff el numero maximo de resultados a mostrar
     * 
     * @return el resultado obtenido tras la busqueda
     * 
     * @throws IOException
     * @throws FileNotFoundException 
     */
    @Override
    public SearchRanking search(String query, int cutoff) throws IOException, FileNotFoundException{
        scoreDocs = new ArrayList<>();
        int numDocs = ((LuceneIndex)index).getIndex().numDocs();
        
        //dividimos la query en palabras
        queryTerms =  query.toLowerCase().split(" ");
        
        //por cada documento existente en el indice
        //obtenemos su puntuacion
        for(int i=0; i< numDocs; i++){
            float score = 0;
            try {
                score = getScore(i);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(VSMEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
            //lo añadimos a nuestra lista de scoreDocs
            if(score>0){
                scoreDocs.add(new ScoreDocImpl(i,score));
            }
        }
        
        //obtenemos los resultados con mejor puntuacion
        int max= (scoreDocs.size()<cutoff)? scoreDocs.size() : cutoff;
        ScoreDocImpl scoreFinal[]= new ScoreDocImpl[max];
        scoreDocs.sort((s1,s2)-> Float.compare(s2.score, s1.score));
      
        for(int i=0; i< max;i++){
            scoreFinal[i]=scoreDocs.get(i);
        }

        return new SearchRankingImpl(this.index, scoreFinal);
    }
    
    /**
     * 
     * Calcula el tf dado una frecuencia de un termino
     * 
     * @param freq la frecuencia de un termino
     * 
     * @return la probabilidad del termino en el documento
     */
    private float tf(double freq){
        if(freq > 0){
            return (float) (1+log2(freq));
        }
        else{
            return 0;
        }
    }
    
    /**
     * Calcula el idf dado una frecuencia de un termino
     * 
     * @param freq la frecuencia de un termino
     * 
     * @return la probabilidad del termino en el indice
     */
    private float idf(double freq){ 
        int numDocs=((LuceneIndex) index).getIndex().numDocs();
        return  (float) (log2(1+numDocs/(freq+1)));
    }

    /**
     * Devuelve la puntacion de un documento dada una query
     * 
     * @param docID el ID del documento
     * 
     * @return la puntuacion del documento
     * 
     * @throws IOException
     * @throws FileNotFoundException
     * @throws ClassNotFoundException 
     */
    private float getScore(int docID) throws IOException, FileNotFoundException, ClassNotFoundException {
        float score=0;
        float sumaParcial=0;
        HashMap<Integer, Float> modulos;

        //obtenemos el modulo del documento requerido
        modulos = ((LuceneIndex)index).readModulo();
        float moduloParcial= modulos.get(docID);
        
        //por cada palabra de la query, obtenemos sus valores 
        //para calcular la puntuacion
        for(String queryTerm : queryTerms){
            //obtenemos la frecuencia de un termino en un documento
            long termFreq=index.getTermFreq(queryTerm, docID);
            //obtenemos el numero de documentos que contienen el termino
            long docFreq= index.getDocFreq(queryTerm);
            
            //obtenemos el tf del termino
            float tfResult=tf(termFreq);
            //obtenemos el idf del termino
            float idfResult=idf(docFreq);
            
            //finalmente obtenemos la puntuacion de un termino en un documento
            sumaParcial=(float) (tfResult*idfResult);
            score+= sumaParcial;
        }
        
        return (moduloParcial>0)? (score/moduloParcial): score;
    }
}
