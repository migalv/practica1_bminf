/**
 * 
 * Fichero TermStats.java.
 * 
 * 
 * @version 1.0
 * 
 * Created on 13/02/2019  
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
 * Clase TermStats que se encarga de escribir en un fichero la frecuencia
 * de los terminos en el indice y la aparicion de los terminos a lo largo de 
 * todos los documentos
 *
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 * 
 */
public class TermStats {
    
    /**
     * Funcion main encargada dede escribir en un fichero la frecuencia
    * de los terminos en el indice y la aparicion de los terminos a lo largo de 
    * todos los documentos
    * 
     * @param args argumentos si hubiese de entrada
     * 
     * @throws IOException 
     */
    public static void main (String args[]) throws IOException {
        LuceneIndex index = new LuceneIndex("index/docs");
        //LuceneIndex index = new LuceneIndex("index/urls");

        //creamos una lista con todos los terminos del indice
        List<String> allTerms = new ArrayList<>(index.getAllTerms());
        //creamos un HashMap con los terminos y sus respectivas frecuencias
        Map <String, Integer> termMap = new HashMap<>();

        //por cada termino insertamos su frecuencia total en el indice
        for(String term: allTerms){
            termMap.put(term, (int)index.getTotalFreq(term));
        }
        
        //escribimos en un fichero las frecuencias de cada termino
        try (BufferedWriter file = Files.newBufferedWriter(Paths.get("./termfreq.txt"))) {
            termMap.entrySet().stream().sorted(Map.Entry.<String, Integer>comparingByValue().reversed()).forEach((t)->{try {
                String str=t.getKey()+"\t"+t.getValue()+"\n" ;
                file.write(str);
            } catch (IOException ex) {
                Logger.getLogger(TermStats.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            });
        }

        //creamos un hashmap con el numero de documentos en los que aparece un termino
        Map <String, Integer> termDocMap = new HashMap<>();
        for(String term: allTerms){
            termDocMap.put(term, (int)index.getDocFreq(term));
        }
        
        //escribimos en un fichero el numero de documentos en los que aparece un documento
        //junto con su termino
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

