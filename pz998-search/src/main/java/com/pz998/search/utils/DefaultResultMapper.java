package com.pz998.search.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;

public class DefaultResultMapper implements SearchResultMapper{

	public <T> List<T> mapResults(SearchResponse response, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		long totalHits = response.getHits().totalHits();
		List<T> results = new ArrayList<T>();
		for (SearchHit hit : response.getHits()) {
			if (hit != null) {
				T result = null;
				if (!Strings.isNullOrEmpty(hit.sourceAsString())) {
					result = mapEntity(hit.sourceAsString(), clazz);
				} else {
					result = mapEntity(hit.getFields().values(), clazz);
				}
				
				results.add(result);
			}
		}
		return results;
	}

	private <T> T mapEntity(Collection<SearchHitField> values, Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	private <T> T mapEntity(String sourceAsString, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return objectMapper.readValue(sourceAsString, clazz);
	}

}
