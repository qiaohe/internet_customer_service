package com.threeti.ics.server.search;

import com.threeti.ics.server.common.Constants;
import com.threeti.ics.server.config.ServerConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 25/09/12
 * Time: 19:06
 * To change this template use File | Settings | File Templates.
 */
public class Searcher {
    private static final Logger LOGGER = LoggerFactory.getLogger(Searcher.class);
    public static final String VISITOR = "visitor";
    public static final String TOPIC = "topic";
    public static final String MESSAGEBODY = "messageBody";
    public static final String MESSAGEDATE = "date";
    public static final String CUSTOMERSERVICEUSERNAME = "customerServiceUserName";
    public static final String CONVERSATIONID = "conversationId";
    private static IndexSearcher INDEX_SEARCHER;

    static {
        try {
            FSDirectory directory = FSDirectory.open(new File(ServerConfig.getInstance().getSearchServerIndexDir()));
            INDEX_SEARCHER = new IndexSearcher(IndexReader.open(directory));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    private final String customerServiceUserName;

    public Searcher(final String customerServiceUserName) {
        this.customerServiceUserName = customerServiceUserName;
    }

    public List<Long> search(String fieldName, String queryString) {
        List<Long> result = new ArrayList<Long>();
        try {
            TermQuery termQuery1 = new TermQuery(new Term(CUSTOMERSERVICEUSERNAME, customerServiceUserName));
            WildcardQuery termQuery2 = new WildcardQuery(new Term(fieldName, "*" + queryString + "*"));
            BooleanQuery booleanQuery = new BooleanQuery();
            booleanQuery.add(termQuery1, BooleanClause.Occur.MUST);
            booleanQuery.add(termQuery2, BooleanClause.Occur.MUST);
            ScoreDoc[] queryResults = INDEX_SEARCHER.search(booleanQuery, 100).scoreDocs;
            for (ScoreDoc scoreDoc : queryResults) {
                Document doc = INDEX_SEARCHER.doc(scoreDoc.doc);
                System.out.println("topic" + doc.get("topic"));
                System.out.println("messageBody" + doc.get("messageBody"));
                result.add(Long.parseLong(doc.get(CONVERSATIONID)));
            }
            return result;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    public Set<Long> searchByVisitorName(final String visitorName) {
        Set<Long> result = new HashSet<Long>();
        try {
            TermQuery termQuery1 = new TermQuery(new Term(VISITOR, visitorName));
            ScoreDoc[] queryResults = INDEX_SEARCHER.search(termQuery1, 100).scoreDocs;
            for (ScoreDoc scoreDoc : queryResults) {
                Document doc = INDEX_SEARCHER.doc(scoreDoc.doc);
                System.out.println("user" + doc.get("customerServiceUserName"));
                System.out.println("topic" + doc.get("topic"));
                System.out.println("messageBody" + doc.get("messageBody"));
                result.add(Long.parseLong(doc.get(CONVERSATIONID)));
            }
            return result;
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return null;
    }

    private TermRangeQuery createRangeTerm(String fieldName, Date date) {
        String start = DateTools.dateToString(date, DateTools.Resolution.SECOND);
        String end = DateTools.dateToString(new Date(), DateTools.Resolution.SECOND);
        return new TermRangeQuery(fieldName, start, end, true, false);
    }

    public List<Long> search(String fieldName, Date date) {
        List<Long> result = new ArrayList<Long>();
        BooleanQuery query = new BooleanQuery();
        query.add(new TermQuery(new Term(CUSTOMERSERVICEUSERNAME, customerServiceUserName)), BooleanClause.Occur.SHOULD);
        query.add(createRangeTerm(fieldName, date), BooleanClause.Occur.SHOULD);
        try {
            ScoreDoc[] queryResults = INDEX_SEARCHER.search(query, 100).scoreDocs;
            for (ScoreDoc scoreDoc : queryResults) {
                Document doc = INDEX_SEARCHER.doc(scoreDoc.doc);
                result.add(Long.parseLong(doc.get(CONVERSATIONID)));
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return result;
    }

    public Set<Long> searchBy(String queryString) {
        Set<Long> result = new HashSet<Long>(search(TOPIC, queryString));
        result.addAll(search(MESSAGEBODY, queryString));
        return result;
    }

    public static void main(String[] args) {
//        Set<Long> d = new Searcher("abcdef0000").searchBy("0");
//        for (Long m : d) {
//            System.out.println(m);
//        }
        System.out.println(Runtime.getRuntime().availableProcessors());

    }
}
