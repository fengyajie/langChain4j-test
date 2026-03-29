package com.ai.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.alibaba.dashscope.threads.ContentText.Text;

import dev.langchain4j.community.model.dashscope.QwenTokenCountEstimator;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.parser.apache.pdfbox.ApachePdfBoxDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import dev.langchain4j.store.embedding.EmbeddingMatch;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.IngestionResult;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

@SpringBootTest
public class RagTest {


    @Test
    public void ragTest(){

        Document document = FileSystemDocumentLoader.loadDocument("D://ragtest.txt");
        System.out.println(document.text());
    }

    @Test
    public void ragTest1(){

        Document document = FileSystemDocumentLoader.loadDocument("D://2024年Java偏设计.pdf",new ApachePdfBoxDocumentParser());
        System.out.println(document.text());
    }


    @Test
    public void ragTest2(){

        Document document = FileSystemDocumentLoader.loadDocument("D://ragtest.txt");
        System.out.println(document.text());
        InMemoryEmbeddingStore<TextSegment> inMemoryEmbeddingStore = new InMemoryEmbeddingStore<>();
        //分割文档
        //文档向量化
        EmbeddingStoreIngestor.ingest(document, inMemoryEmbeddingStore);

        System.out.println(inMemoryEmbeddingStore);
    }

    /**
     * 自定义分割器
     */
    @Test
    public void ragTest3(){

        Document document = FileSystemDocumentLoader.loadDocument("D://ragtest.txt");
        System.out.println(document.text());
        InMemoryEmbeddingStore<TextSegment> inMemoryEmbeddingStore = new InMemoryEmbeddingStore<>();
        DocumentByParagraphSplitter documentByParagraphSplitter = new DocumentByParagraphSplitter(300, 30);
        IngestionResult ingest = EmbeddingStoreIngestor.builder().embeddingStore(inMemoryEmbeddingStore).documentSplitter(documentByParagraphSplitter).build().ingest(document);

        System.out.println(inMemoryEmbeddingStore);
    }


    /**
     * 计算token
     */
    @Test
    public void tokenCount(){

        QwenTokenCountEstimator qwenTokenCountEstimator = new QwenTokenCountEstimator("sk-12f711f107544a5592a07c5ad1d4ef97", "qwen-max");
        int estimateTokenCountInText = qwenTokenCountEstimator.estimateTokenCountInText("分割文档分割文档分割文档分割文档分割文档分割文档");
        System.out.println(estimateTokenCountInText);
    }

    @Autowired
    private EmbeddingModel embeddingModel;

    /**
     * 阿里百炼向量模型
     */
    @Test
    public void testEmbedding(){
        Response<Embedding> embed = embeddingModel.embed("你好");
        System.out.println("向量输出:"+embed.toString());

    }


    /**
     * pinecone向量库
     */
    @Autowired
    private EmbeddingStore embeddingStore;
    @Test
    public void pineconeTest(){

        TextSegment text = TextSegment.from("我喜欢莎莎");

        Embedding embedding = embeddingModel.embed(text).content();

        embeddingStore.add(embedding, text);

        TextSegment from = TextSegment.from("我喜欢");

    }

    /**
     * pinecone向量库搜索
     */
    @Test
    public void embeddingSerch(){
        Response<Embedding> embed = embeddingModel.embed("我喜欢");;

        EmbeddingSearchRequest embeddingSearchRequest = EmbeddingSearchRequest.builder().queryEmbedding(embed.content()).maxResults(1).build();
        //请求相似度
        EmbeddingSearchResult<TextSegment> search = embeddingStore.search(embeddingSearchRequest);

        EmbeddingMatch<TextSegment> match = search.matches().get(0);
        System.out.println(match.score());

        System.out.println(match.embedded().text());
    }
}
