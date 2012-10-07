package com.threeti.ics.server.search;

import com.threeti.ics.server.common.Constants;
import com.threeti.ics.server.config.ServerConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.DateTools;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: qhe
 * Date: 25/09/12
 * Time: 18:04
 * To change this template use File | Settings | File Templates.
 */
@Component(value = "indexer")
public class Indexer {
    private IndexWriter writer;

    @PostConstruct
    private void init() throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_36, new SmartChineseAnalyzer(Version.LUCENE_36));
        FSDirectory directory = FSDirectory.open(new File(ServerConfig.getInstance().getSearchServerIndexDir()));
        writer = new IndexWriter(directory, config);
    }

    public void index(MessageItem item) throws IOException {
        writer.deleteDocuments(new Term("id", item.getMessage().getId().toString()));
        Document doc = new Document();
        doc.add(new Field("id", item.getMessage().getIdAsString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("visitor", item.getVisitor(), Field.Store.YES, Field.Index.ANALYZED));
        final String cn = item.getMessage().getCustomerServiceUser();
        doc.add(new Field("customerServiceUserName", cn == null ? StringUtils.EMPTY : cn, Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("conversationId", item.getMessage().getConversationId().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("messageBody", item.getMessage().getMessageBody(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("topic", item.getTopic(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field("status", item.getMessage().getStatus().toString(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("date", DateTools.dateToString(Constants.getDate(item.getMessage().getDateString()), DateTools.Resolution.SECOND), Field.Store.YES, Field.Index.ANALYZED));
        writer.addDocument(doc);
        writer.commit();
    }
    @PreDestroy
    public void close() throws IOException {
        writer.close(true);
    }
}