package com.ecommerce.ecommerce_search.services;

import com.ecommerce.ecommerce_search.models.Product;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SolrServiceImpl implements IFSolrService{

    private final SolrClient solrClient;
    public SolrServiceImpl(SolrClient solrClient) {
        this.solrClient = solrClient;
    }


    @Override
    public boolean indexProduct(Product product) {
        try {
            SolrInputDocument doc = new SolrInputDocument();
            doc.addField("name", product.getName());
            doc.addField("description", product.getDescription());
            doc.addField("price", product.getPrice());
            doc.addField("product_uuid", product.getProduct_uuid());
            doc.addField("category", product.getCategory());
            // Add more fields as needed based on your Product model
            UpdateResponse addResponse = solrClient.add("products", doc);
            if (addResponse.getStatus() != 0) {
                return false;
            }
            UpdateResponse commitResponse = solrClient.commit("products");
            return commitResponse.getStatus() == 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean searchProduct(String query, int page, int size) throws SolrServerException, IOException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(query);
        solrQuery.setStart((page - 1) * size);
        solrQuery.setRows(size);

        QueryResponse response = this.solrClient.query("products", solrQuery);
        response.getResults().forEach(System.out::println);
        // Process response.getResults() as needed

        return false;
    }
}
