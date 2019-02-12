/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.IndexBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
    
   
    @Override
    public void build(String collectionPath, String indexPath) throws IOException {
        Directory directory = FSDirectory.open(Paths.get("res/index"));
        boolean rebuild = true;
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        ArrayList<String> urls = new ArrayList<>();
        IndexWriter builder;
        
        if (rebuild)
            config.setOpenMode(OpenMode.CREATE);
        else
            config.setOpenMode(OpenMode.CREATE_OR_APPEND);

        builder = new IndexWriter(directory, config);

        if(collectionPath.endsWith(".txt")){
            File file = new File(collectionPath);

            BufferedReader br = new BufferedReader(new FileReader(file));             

            String aux;
            while ((aux = br.readLine()) != null){
                urls.add(aux);
            } 

            // 1. Añadir documentos al índice
            for (String url : urls) {
                Document doc = new Document();
                doc.add(new TextField("path", url, Field.Store.YES));
                FieldType type = new FieldType();
                type.setIndexOptions (IndexOptions.DOCS_AND_FREQS);
                type.setStoreTermVectors (true);
                String text = Jsoup.parse(new URL(url), 10000).text();
                doc.add(new Field("content", text, type));
                builder.addDocument(doc);
            }
            builder.close();
        
        }
        else if(collectionPath.endsWith(".zip")){
            ZipFile zipFile = new ZipFile(collectionPath);

            Enumeration<? extends ZipEntry> entries = zipFile.entries();

            while(entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();
                InputStream stream = zipFile.getInputStream(entry);
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                    
            }
            
            
                
        }else{
            
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesForFolder(fileEntry);
                } else {
                    System.out.println(fileEntry.getName());
                }
            }   
        
        }
    }
    
}
