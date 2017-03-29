package com.pz998.search.utils;

import java.util.List;


import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;


public interface SearchParam {
	QueryBuilder getQuery();



	List<SortBuilder> getElasticsearchSorts();



	List<AbstractAggregationBuilder> getAggregations();

	HighlightBuilder.Field[] getHighlightFields();
}
