package com.ai.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeEmbeddingStore;
import dev.langchain4j.store.embedding.pinecone.PineconeIndexConfig;
import dev.langchain4j.store.embedding.pinecone.PineconeServerlessIndexConfig;

@Configuration
public class EmbeddingStoreConfig {

    @Autowired
    private EmbeddingModel embeddingModel;

    @Bean
    public EmbeddingStore<TextSegment> embeddingStore(){

        PineconeEmbeddingStore pineconeEmbeddingStore = PineconeEmbeddingStore.builder().apiKey("pcsk_5TFKGZ_JbyHLhRTuc3zZh22MqgtmkxR4LmeNt8erec87y7Jte3ZqBZWTrGjw5rtdaZif4w")
                   .index("my-index")
                   .nameSpace("my-namespace")//没有则新建
                   .createIndex(PineconeServerlessIndexConfig.builder().cloud("AWS").region("us-east-1").dimension(embeddingModel.dimension()).build()).build();;


        return pineconeEmbeddingStore;

    }
}
