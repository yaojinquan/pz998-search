package com.pz998.search.utils;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.vectorhighlight.CustomFieldQuery;
import org.apache.lucene.search.vectorhighlight.FastVectorHighlighter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.elasticsearch.common.lucene.Lucene;


public class Test {
	public void testVectorHighlighter() throws Exception {
        Directory dir = new RAMDirectory();
        IndexWriter indexWriter = new IndexWriter(dir, new IndexWriterConfig(Lucene.STANDARD_ANALYZER));

        Document document = new Document();
        document.add(new TextField("_id", "1", Field.Store.YES));
        FieldType vectorsType = new FieldType(TextField.TYPE_STORED);
        vectorsType.setStoreTermVectors(true);
        vectorsType.setStoreTermVectorPositions(true);
        vectorsType.setStoreTermVectorOffsets(true);
        document.add(new Field("content", "If you are asking about what you can do with the rest API Elasticsearch supports a highlight_query option that you can specify on the field level and that query is fed to the highlighter instead of the search field. You can't pass arbitrary text to highlight though - its normal to store that text in the _source. You could build it on the fly if you were willing to be creative and write a plugin.", vectorsType));
        indexWriter.addDocument(document);

        IndexReader reader = DirectoryReader.open(indexWriter,true);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(new TermQuery(new Term("_id", "1")), 1);

        

        FastVectorHighlighter highlighter = new FastVectorHighlighter();
        String fragment = highlighter.getBestFragment(highlighter.getFieldQuery(new TermQuery(new Term("content", "supports"))),
                reader, topDocs.scoreDocs[0].doc, "content", 30);
        
        System.out.println(fragment);
    }
	
	public void testVectorHighlighterPrefixQuery() throws Exception {
        Directory dir = new RAMDirectory();
        IndexWriter indexWriter = new IndexWriter(dir, new IndexWriterConfig(Lucene.STANDARD_ANALYZER));

        Document document = new Document();
        document.add(new TextField("_id", "1", Field.Store.YES));
        FieldType vectorsType = new FieldType(TextField.TYPE_STORED);
        vectorsType.setStoreTermVectors(true);
        vectorsType.setStoreTermVectorPositions(true);
        vectorsType.setStoreTermVectorOffsets(true);
        document.add(new Field("content", "the big bad dog", vectorsType));
        indexWriter.addDocument(document);

        IndexReader reader = DirectoryReader.open(indexWriter,true);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(new TermQuery(new Term("_id", "1")), 1);

    

        FastVectorHighlighter highlighter = new FastVectorHighlighter();

        PrefixQuery prefixQuery = new PrefixQuery(new Term("content", "ba"));
        String fragment = highlighter.getBestFragment(highlighter.getFieldQuery(prefixQuery),
                reader, topDocs.scoreDocs[0].doc, "content", 30);
 

        prefixQuery.setRewriteMethod(PrefixQuery.SCORING_BOOLEAN_REWRITE);
        Query rewriteQuery = prefixQuery.rewrite(reader);
        fragment = highlighter.getBestFragment(highlighter.getFieldQuery(rewriteQuery),
                reader, topDocs.scoreDocs[0].doc, "content", 30);
     

        // now check with the custom field query
        prefixQuery = new PrefixQuery(new Term("content", "ba"));
        fragment = highlighter.getBestFragment(new CustomFieldQuery(prefixQuery, reader, highlighter),
                reader, topDocs.scoreDocs[0].doc, "content", 30);
    
    }

    public void testVectorHighlighterNoStore() throws Exception {
        Directory dir = new RAMDirectory();
        IndexWriter indexWriter = new IndexWriter(dir, new IndexWriterConfig(Lucene.STANDARD_ANALYZER));

        Document document = new Document();
        document.add(new TextField("_id", "1", Field.Store.YES));
        FieldType vectorsType = new FieldType(TextField.TYPE_NOT_STORED);
        vectorsType.setStoreTermVectors(true);
        vectorsType.setStoreTermVectorPositions(true);
        vectorsType.setStoreTermVectorOffsets(true);
        document.add(new Field("content", "the big bad dog", vectorsType));
        indexWriter.addDocument(document);

        IndexReader reader = DirectoryReader.open(indexWriter,true);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(new TermQuery(new Term("_id", "1")), 1);

        

        FastVectorHighlighter highlighter = new FastVectorHighlighter();
        String fragment = highlighter.getBestFragment(highlighter.getFieldQuery(new TermQuery(new Term("content", "bad"))),
                reader, topDocs.scoreDocs[0].doc, "content", 30);
        
    }

    public void testVectorHighlighterNoTermVector() throws Exception {
        Directory dir = new RAMDirectory();
        IndexWriter indexWriter = new IndexWriter(dir, new IndexWriterConfig(Lucene.STANDARD_ANALYZER));

        Document document = new Document();
        document.add(new TextField("_id", "1", Field.Store.YES));
        document.add(new TextField("content", "the big bad dog", Field.Store.YES));
        indexWriter.addDocument(document);

        IndexReader reader = DirectoryReader.open(indexWriter,true);
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs topDocs = searcher.search(new TermQuery(new Term("_id", "1")), 1);


        FastVectorHighlighter highlighter = new FastVectorHighlighter();
        String fragment = highlighter.getBestFragment(highlighter.getFieldQuery(new TermQuery(new Term("content", "bad"))),
                reader, topDocs.scoreDocs[0].doc, "content", 30);
        
    }
	
	public static void main(String[] args) throws Exception {
		Test t = new Test();
		t.testVectorHighlighter();
	}
}
