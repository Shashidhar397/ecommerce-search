package com.ecommerce.ecommerce_search.models;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SearchRequest {
    private String collection; // Target collection or index (Solr/ES)
    private String query; // Main query string (q for Solr, query for ES)
    private List<String> filters; // Filter queries (fq for Solr, filter for ES)
    private List<String> fields; // Fields to return (fl for Solr, _source for ES)
    private List<String> sortFields; // Sort fields strings (sort for Solr/ES)
    private String sortOrder; // Sort order (asc/desc for Solr/ES)
    private List<String> facets; // Facet or aggregation fields (facet for Solr, aggs for ES)
    private int start = 0; // Pagination start (start for Solr, from for ES)
    private int rows = 10; // Pagination size (rows for Solr, size for ES)
    private Map<String, Object> extraParams; // For any backend-specific options (e.g., highlight, suggest, etc.)
}
