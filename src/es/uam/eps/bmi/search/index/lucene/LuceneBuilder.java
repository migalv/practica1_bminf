/**
 * 
 * Fichero LuceneBuilder.java que incluye el constructor de Lucene.
 * 
 * 
 * @version 1.0
 * 
 * Created on 10/02/2019  
 */
package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.IndexBuilder;
import es.uam.eps.bmi.search.index.freq.FreqVector;
import es.uam.eps.bmi.search.index.freq.TermFreq;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import static org.apache.lucene.search.similarities.SimilarityBase.log2;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;


/**
 * Clase que implementa a la interfaz de IndexBuilder y se encargara
 * de la construccion de un indice dado una serie de archivos y directorios.
 *
 * @author Miguel Alvarez Lesmes
 * @author Sergio Romero Tapiador
 * 
 */
public class LuceneBuilder implements IndexBuilder{
    
    Analyzer analyzer;
    LuceneIndex index;
    private Map<Integer, Float> modulos;

    
    /**
     * Crea el indice de Lucene.
     * 
     * @param collectionPath el path de la coleccion de documentos
     * 
     * @param indexPath el path donde queremos insertar nuestro indice
     * 
     * @throws IOException 
     */
    @Override
    public void build(String collectionPath, String indexPath) throws IOException {
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        boolean rebuild = true;
        analyzer = new StandardAnalyzer();
        
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        // Lista con el contenido parseado html de los documentos
        ArrayList<String> content = new ArrayList<>();
        // Lista con el nombre del documento o Url
        ArrayList<String> paths = new ArrayList<>();
        IndexWriter builder;
        File file = new File(collectionPath);
        
        if (rebuild)
            config.setOpenMode(OpenMode.CREATE);
        else
            config.setOpenMode(OpenMode.CREATE_OR_APPEND);

        builder = new IndexWriter(directory, config);

        // Si el archivo es un txt leemos linea a linea
        // donde cada linea es una url
        if(collectionPath.endsWith(".txt")){
            BufferedReader br = new BufferedReader(new FileReader(file));             
            String url;
            
            // Leemos las urls del archivo y parseamos el html
            while ((url = br.readLine()) != null){
                this.addToIndex(Jsoup.parse(new URL(url), 10000).text(), url, builder);
            }
            
        } // Si es un zip, por cada fichero del zip lo leemos 
        else if(collectionPath.endsWith(".zip")){
            // Abrimos el zipfile
            ZipFile zipFile = new ZipFile(collectionPath);
            // Recuperamos las entradas del zip file
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            
            // Cada linea del fichero
            int len;
                        
            // Recorremos lalista de entradas (ficheros) del zip
            while(entries.hasMoreElements()){
                // Recuperamos la entrada
                ZipEntry entry = entries.nextElement();
                
                // Comprobamos que no sea un directorio
                if(!entry.isDirectory()){
                    // Abrimos el achivo
                    InputStream stream = zipFile.getInputStream(entry);
                    ByteArrayOutputStream result = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    // Leemos el fichero
                    while ((len = stream.read(buffer, 0, buffer.length)) > 0) {
                            result.write(buffer, 0, len);
                    }
                    this.addToIndex(Jsoup.parse(result.toString("UTF-8")).text(), file.getAbsolutePath() + File.separator + entry.getName(), builder);
                }
                
            }              
        } // Si no es nada de lo otro entonces es un directorio
          // y vamos archivo a archivo 
        else{
            // Cada linea del fichero
            String line;
            
            // Por cada fichero en el directorio collectionPath
            for (final File fileEntry : new File(collectionPath).listFiles()) {
                // Si el fichero no es un directoio
                if (!fileEntry.isDirectory()) {
                    // Creamos un stringbuilder para leer la entrada (fichero) del zipfile
                    StringBuilder stringBuilder = new StringBuilder();
                    // Abrimos el achivo
                    BufferedReader br = new BufferedReader(new FileReader(fileEntry));
                    // Leemos el fichero
                    while ((line = br.readLine()) != null) {
                            stringBuilder.append(line);
                    }
                    this.addToIndex(Jsoup.parse(stringBuilder.toString()).text(), file.getAbsolutePath() + File.separator + fileEntry.getName(), builder);

                } 
            }   
        
        }
        //cerramos el indice creado y calculamos el modulo de cada documento
        builder.close();
        createModulo(indexPath);
        
    }
    
    /**
     * 
     * Funcion comun a los tres tipos diferentes de elementos que manejaremos, en la 
     * cual añadimos al indice los diferentes documentos leidos.
     * 
     * @param content el contenido que incluiremos en el indice
     * 
     * @param path el path donde queremos guardarlo
     * 
     * @param builder el objeto que nos ayudara a crear el indice
     * 
     * @throws IOException 
     */
    public void addToIndex(String content, String path,IndexWriter builder) throws IOException{

        // Creamos un documento por cada entrada en la lista content
        Document doc = new Document();
        // Añadimos el path al documento
        doc.add(new TextField("path", path, Field.Store.YES));
        FieldType type = new FieldType();
        type.setIndexOptions (IndexOptions.DOCS_AND_FREQS);
        type.setStoreTermVectors(true);
        // Añadimos el html parseado al documento
        doc.add(new Field("content", content, type));
        // Añadimos el documento al indice
        builder.addDocument(doc);
    }
    
    /**
     * 
     * Calculara todos los modulos de los documentos en el indice actual
     * 
     * @param indexPath el path donde se encuentra el indice
     * 
     * @throws IOException 
     */
    private void createModulo(String indexPath) throws IOException{
        this.index= new LuceneIndex(indexPath);
        int numDocs= index.getIndex().numDocs();
        float sumaParcial=0;
        float modulo = 0;

        //creamos un HashMap donde guardaremos el ID del documento junto con 
        //su modulo previamente calculado
        modulos= new HashMap<>();

        //por cada documento del indice
        for(int i=0; i< numDocs; i++){
            FreqVector allTerms= index.getDocVector(i);

            //por cada termino del documento i
            for(TermFreq term: allTerms){
                String word= term.getTerm();
                
                //calculamos el tf y el idf
                long termFreq=index.getTermFreq(word, i);
                long docFreq= index.getDocFreq(word);
                float tfResult= tf(termFreq);
                float idfResult= idf(docFreq);
                sumaParcial=(float) (tfResult*idfResult);

                //calculamos la suma parcial de los terminos al cuadrado
                modulo+= (pow(sumaParcial,2)); 
            }
            
            //finalmente calculamos la raiz y lo añadimos al HashMap
            modulo = (float) sqrt(modulo);
            modulos.put(i,modulo);

        }

        //por ultimo, lo escribimos en un fichero para su posterior lectura
        try (FileOutputStream fileOut = new FileOutputStream(Paths.get(indexPath+File.separator+"modulos.txt").toString()); ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(modulos);
        }
            
            
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
    
}