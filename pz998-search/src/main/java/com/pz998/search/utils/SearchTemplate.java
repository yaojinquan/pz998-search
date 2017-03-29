package com.pz998.search.utils;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;




public class SearchTemplate {
	private TransportClient client;
	
	private SearchResultMapper resultsMapper;
	
	public SearchTemplate(TransportClient client) {
		this(client,null);
	}
	
	
	public SearchTemplate(TransportClient client, SearchResultMapper resultsMapper) {
		this.client = client;
		this.resultsMapper = (resultsMapper == null) ? new DefaultResultMapper() : resultsMapper;
	}
	
	
	
	
	public <T> List<T> queryForList(SearchParam searchQuery, Class<T> clazz){
//		SearchResponse sp = client.prepareSearch(SYMPTOM_INDEX)// 检索的目录
//				.setSearchType(SearchType.DEFAULT)// Query type				
//				.setQuery(QueryBuilders.disMaxQuery()
//			               .add(QueryBuilders.matchQuery("name", keyword))
//			               .add(QueryBuilders.matchQuery("intro", keyword)))	
//				.addHighlightedField("name")
//				.addHighlightedField("intro")
//				.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
//				.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
//				.setFrom(from).setSize(size).setExplain(true)// topN方式
//				.execute().actionGet();// 执行
//		return resultsMapper.mapResults(sp, clazz);
		return null;
	}
	
}
