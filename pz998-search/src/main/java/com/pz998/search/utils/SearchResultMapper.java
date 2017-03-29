package com.pz998.search.utils;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.action.search.SearchResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;


public interface SearchResultMapper {
	public <T> List<T> mapResults(SearchResponse response, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException;
}
