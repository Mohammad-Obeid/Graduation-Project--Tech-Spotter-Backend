package com.example.GradProJM.Utils;


import com.example.GradProJM.Model.Shop_Products;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimilarityUtil {
//    private static

    public static List<Integer> findSimilarProducts(String text, List<Shop_Products> products) throws IOException, ParseException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        RAMDirectory ramDirectory = new RAMDirectory();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(ramDirectory, indexWriterConfig);

        for (Shop_Products product : products) {
            Document doc = new Document();
            doc.add(new TextField("id", String.valueOf(product.getId()), Field.Store.YES));
            doc.add(new TextField("productCategory", product.getProduct().getProductCategory(), Field.Store.YES));
            doc.add(new TextField("productName", product.getProduct().getProductName(), Field.Store.YES));
//            doc.add(new TextField("name", product.getProduct().getProductName(), Field.Store.YES));
//            todo: when i add status
            indexWriter.addDocument(doc);
        }
        indexWriter.close();

        Query query = new QueryParser("productName", analyzer).parse(QueryParser.escape(text));

        IndexReader reader = DirectoryReader.open(ramDirectory);
        IndexSearcher searcher = new IndexSearcher(reader);
        searcher.setSimilarity(new BM25Similarity());

        ScoreDoc[] hits = searcher.search(query, 10).scoreDocs;
        List<Integer> similarProductIds = new ArrayList<>();
        for (ScoreDoc hit : hits) {
            Document hitDoc = searcher.doc(hit.doc);
            similarProductIds.add(Integer.valueOf(hitDoc.get("id")));
        }
        reader.close();

        return similarProductIds;
    }

    public static List<Integer> findSimilarProducts2(String text, List<Shop_Products> products) throws IOException, ParseException {
        StandardAnalyzer analyzer = new StandardAnalyzer();
        RAMDirectory ramDirectory = new RAMDirectory();
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        IndexWriter indexWriter = new IndexWriter(ramDirectory, indexWriterConfig);
        for (Shop_Products product : products) {
            Document doc = new Document();
            doc.add(new TextField("id", String.valueOf(product.getId()), Field.Store.YES));
            doc.add(new TextField("productCategory", product.getProduct().getProductCategory(), Field.Store.YES));
            doc.add(new TextField("productName", product.getProduct().getProductName(), Field.Store.YES));
            doc.add(new TextField("productCompanyName", product.getProduct().getProductCompanyName(), Field.Store.YES));
            indexWriter.addDocument(doc);
        }
        indexWriter.close();
        // Print the query being executed
        Query query = new QueryParser("productCategory", analyzer).parse(QueryParser.escape(text));

        IndexReader reader = DirectoryReader.open(ramDirectory);
        IndexSearcher searcher = new IndexSearcher(reader);
        searcher.setSimilarity(new BM25Similarity());

        ScoreDoc[] hits = searcher.search(query, 8).scoreDocs;
        List<Integer> similarProductIds = new ArrayList<>();
        for (ScoreDoc hit : hits) {
            Document hitDoc = searcher.doc(hit.doc);
            similarProductIds.add(Integer.valueOf(hitDoc.get("id")));
        }
        reader.close();
        return similarProductIds;
    }

}
