package com.ecommerce.ecommerce_search.models;

import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class SearchResponse {
    private long numFound;
    private long start;
    private List<Map<String, Object>> docs;
    private Map<String, List<FacetValue>> facets;
}
