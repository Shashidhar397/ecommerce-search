package com.ecommerce.ecommerce_search.configuration;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SolrConfig {

    @Bean
    public SolrClient solrClient() {
        String solrHost = System.getenv().getOrDefault("SOLR_HOST", "localhost");
        String solrPort = System.getenv().getOrDefault("SOLR_PORT", "8983");
        String solrUrl = "http://" + solrHost + ":" + solrPort + "/solr";
        return new HttpSolrClient.Builder(solrUrl)
                .withConnectionTimeout(10000)
                .withSocketTimeout(60000)
                .build();
    }
}
