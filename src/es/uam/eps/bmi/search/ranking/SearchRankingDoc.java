/**
 * 
 * Fichero SearchRankingDoc.java.
 * 
 * 
 * @version 1.0
 * 
 * Created on 07/02/2019  
 */
package es.uam.eps.bmi.search.ranking;

import java.io.IOException;

/**
 * 
 * Clase abstracta SearchRankingDoc preparada para comparar las puntuaciones
 * de las diferentes busquedas realizadas
 * 
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 */
public abstract class SearchRankingDoc implements Comparable<SearchRankingDoc> {
        
    /**
     * 
     * Comparador entre puntuaciones de busquedas realizadas con el proposito
     * de ordenar la que mayor puntuacion tenga.
     * 
     * @param o el objeto a comparar
     * 
     * @return 1 si es mayor, -1 si es menor o 0 en caso de que sean iguales
     */
    @Override
    public int compareTo(SearchRankingDoc o) {
        
        if(this.getScore() > o.getScore()){
            return 1;
        }else if(this.getScore() < o.getScore()){
            return -1;
        }else{
            return 0;
        }
        
    }
    
    /**
     * Devuelve el path de la busqueda realizada
     * 
     * @return el path de la busqueda realizada
     * 
     * @throws IOException 
     */
    public abstract String getPath() throws IOException;
    
    /**
     * Devuelve la puntuacion de una busqueda
     * 
     * @return la puntuacion de una busqueda
     */
    public abstract double getScore();
}