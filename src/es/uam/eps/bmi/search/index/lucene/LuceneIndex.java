/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.uam.eps.bmi.search.index.lucene;

import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.freq.FreqVector;
import es.uam.eps.bmi.search.index.freq.lucene.LuceneFreqVector;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.MultiFields;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.index.Terms;



/**
 *
 * @author e341058
 */
public class LuceneIndex implements Index{
    IndexReader index;
    Directory directory;
    List<String> termList;
    
    
    public LuceneIndex(String indexPath) throws IOException{
        directory = FSDirectory.open(Paths.get(indexPath));
        index = DirectoryReader.open(directory);
        
        termList = new ArrayList<>();
        TermsEnum terms;
        
        terms = MultiFields.getFields(index).terms("content").iterator();
        
        while (terms.next() != null){
            termList.add(terms.term().utf8ToString());
        }
        
    }
    

    @Override
    public List<String> getAllTerms() throws IOException {
        return termList;
    }

    @Override
    public long getTotalFreq(String term) throws IOException{
        Term termInstance= new Term("content",term);
        
        return index.totalTermFreq(termInstance);
    }

    @Override
    public FreqVector getDocVector(int id) throws IOException{
        Terms docVector = index.getTermVector(id, "content"); 
        
        return new LuceneFreqVector(docVector);
    }
    
    public IndexReader getIndex(){ return this.index; }
    
    public Directory getDirectory(){ return this.directory; }

    @Override
    public String getDocPath(int docID) throws IOException { 
        return index.document(docID).get("path"); 
    }

    @Override
    public long getTermFreq(String term, int docID) throws IOException {
        
        return this.getDocVector(docID).getFreq(term);
    }

    @Override
    public long getDocFreq(String term) throws IOException {
        return index.docFreq(new Term("content",term));
    }
    
}
