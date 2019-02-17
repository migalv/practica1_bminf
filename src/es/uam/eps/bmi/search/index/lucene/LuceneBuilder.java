/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.IndexBuilder;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
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
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.jsoup.Jsoup;


/**
 *
 * @author e341058
 */
public class LuceneBuilder implements IndexBuilder{
    
    Analyzer analyzer;
    
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
            // Creamos un stringbuilder para leer la entrada (fichero) del zipfile
            StringBuilder stringBuilder = new StringBuilder();
            // Cada linea del fichero
            String line;
            
            // Por cada fichero en el directorio collectionPath
            for (final File fileEntry : new File(collectionPath).listFiles()) {
                // Si el fichero no es un directoio
                if (!fileEntry.isDirectory()) {
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
        builder.close();
        
    }
    
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
    
}
