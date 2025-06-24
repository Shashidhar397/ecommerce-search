package com.ecommerce.ecommerce_search.services;

import com.ecommerce.ecommerce_search.models.FacetValue;
import com.ecommerce.ecommerce_search.models.Product;
import com.ecommerce.ecommerce_search.models.SearchRequest;
import com.ecommerce.ecommerce_search.models.SearchResponse;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.SolrQuery.SortClause;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SolrServiceImpl implements IFSearchService{

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


    @Override
    public SearchResponse searchProducts(SearchRequest request) throws IOException, SolrServerException {
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery(request.getQuery());
        solrQuery.setStart(request.getStart());
        solrQuery.setRows(request.getRows());
        if (request.getFields() != null && !request.getFields().isEmpty()) {
            solrQuery.setFields(request.getFields().toArray(new String[0]));
        }
        if (request.getSortFields() != null && !request.getSortFields().isEmpty()) {
            String sortOrder = SolrQuery.ORDER.asc.name(); // Default sort order
            if (request.getSortOrder() != null && !request.getSortOrder().isEmpty()) {
                sortOrder = request.getSortOrder().equalsIgnoreCase("desc") ? SolrQuery.ORDER.desc.name() : SolrQuery.ORDER.asc.name();
            }
            List<SortClause> sortClauses = new ArrayList<>();
            for (String sortField : request.getSortFields()) {
                if (sortField.length() > 0) {
                    sortClauses.add(new SortClause(sortField, sortOrder));
                }
            }
            solrQuery.setSorts(sortClauses);
        }
        if (request.getFilters() != null) {
            for (String fq : request.getFilters()) {
                solrQuery.addFilterQuery(fq);
            }
        }
        if (request.getFacets() != null && !request.getFacets().isEmpty()) {
            solrQuery.setFacet(true);
            solrQuery.addFacetField(request.getFacets().toArray(new String[0]));
        }
        // Handle extraParams if needed
        if (request.getExtraParams() != null) {
            for (Map.Entry<String, Object> entry : request.getExtraParams().entrySet()) {
                solrQuery.set(entry.getKey(), entry.getValue().toString());
            }
        }
        QueryResponse response = this.solrClient.query(request.getCollection(), solrQuery);
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setNumFound(response.getResults().getNumFound());
        searchResponse.setStart(response.getResults().getStart());
        response.getResults().forEach(System.out::println);
        // Convert SolrDocumentList to List<Map<String, Object>>
        List<Map<String, Object>> docs = new ArrayList<>();
        response.getResults().forEach(doc -> {
            System.out.println(doc);
            Map<String, Object> docMap = new HashMap<>();
            doc.getFieldNames().forEach(fieldName -> {
                docMap.put(fieldName, doc.getFieldValue(fieldName));
            });
            docs.add(docMap);
        });
        searchResponse.setDocs(docs);
        // Facets
        if (response.getFacetFields() != null) {
            Map<String, List<FacetValue>> facets = new HashMap<>();
            response.getFacetFields().forEach(facetField -> {
                List<FacetValue> values = new ArrayList<>();
                facetField.getValues().forEach(fv -> {
                    FacetValue val = new FacetValue();
                    val.setValue(fv.getName());
                    val.setCount(fv.getCount());
                    values.add(val);
                });
                facets.put(facetField.getName(), values);
            });
            searchResponse.setFacets(facets);
        }
        return searchResponse;
    }
}
