package es.uam.eps.bmi.search.lucene;

public class LuceneEngine extends AbstractEngine{
    IndexSearcher indexSearcher;

    public LuceneEngine(String indexPath) {
        Directory directory = FSDirectory.open(Paths.get(indexPath));
        IndexReader index = DirectoryReader.open(directory);
        indexSearcher = new IndexSearcher(index);
    }

    public SearchRanking search(String query, int cutoff) throws IOException{

    }
}