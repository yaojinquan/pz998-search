package com.pz998.search.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.highlight.HighlightField;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry.Option;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pz998.search.model.dto.SearchParamDto;
import com.pz998.search.model.entity.BdBodySymptom;
import com.pz998.search.model.entity.BdDepartment;
import com.pz998.search.model.entity.BdDisease;
import com.pz998.search.model.entity.BdDoctor;
import com.pz998.search.model.vo.HospitalSearchVo;
import com.pz998.search.model.vo.PromptSearchVo;
import com.pz998.search.model.vo.ResponseVo;
import com.pz998.search.model.vo.SearchResultVo;
import com.pz998.search.utils.SearchHelp;


@Controller
@RequestMapping(value="/pzsearch")
public class SearchController {
	
    public static final String FIELD_HIGHLIGHT_PRE_TAG = "<span style=\"color:#22bba7\">";
    
    public static final String FIELD_HIGHLIGHT_POST_TAG = "</span>";
    
    public static final String DISEASE_INDEX = "disease";
    
    public static final String DOCTOR_INDEX = "doctor";
    
    public static final String HOSPITAL_INDEX = "hospital";
    
    public static final String SYMPTOM_INDEX = "symptom";
    
    public static final String DEPARTMENT_INDEX = "department";
    
    public static final String PROMPT_INDEX = "prompt";
    
    
    
	
	@RequestMapping(value = "/indexSearch")
	@ResponseBody
	public  ResponseVo<SearchResultVo> indexSearch(SearchParamDto searchParam) throws IOException {
		ResponseVo<SearchResultVo> resp = new ResponseVo<SearchResultVo>();
		Client client = null;
		try {
			client = SearchHelp.getSearchClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		resp.setCode(ResponseVo.CODE_COMMON_SUCCESS);
		resp.setCodeMsg(ResponseVo.CODE_MAP.get(ResponseVo.CODE_COMMON_SUCCESS));
		resp.setDatas(queryAll(searchParam,client));
		return resp;
	}
	
	@RequestMapping(value = "/diseaseSearch")
	@ResponseBody
	public ResponseVo<SearchResultVo> diseaseSearch(SearchParamDto searchParam){
		ResponseVo<SearchResultVo> resp = new ResponseVo<SearchResultVo>();
		Client client = null;
		try {
			client = SearchHelp.getSearchClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		resp.setCode(ResponseVo.CODE_COMMON_SUCCESS);
		resp.setCodeMsg(ResponseVo.CODE_MAP.get(ResponseVo.CODE_COMMON_SUCCESS));
		resp.setDatas(queryDiseaseOrDoctor(searchParam,client));
		return resp;
	}
	
	@RequestMapping(value = "/symptomSearch")
	@ResponseBody
	public ResponseVo<SearchResultVo> symptomSearch(SearchParamDto searchParam){
		ResponseVo<SearchResultVo> resp = new ResponseVo<SearchResultVo>();
		Client client = null;
		try {
			client = SearchHelp.getSearchClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		resp.setCode(ResponseVo.CODE_COMMON_SUCCESS);
		resp.setCodeMsg(ResponseVo.CODE_MAP.get(ResponseVo.CODE_COMMON_SUCCESS));
		resp.setDatas(querySymptomOrDoctor(searchParam,client));
		return resp;
	}
	
	
	@RequestMapping(value = "/nearSearch")
	@ResponseBody
	public ResponseVo<SearchResultVo> nearSearch(SearchParamDto searchParam){
		ResponseVo<SearchResultVo> resp = new ResponseVo<SearchResultVo>();
		Client client = null;
		try {
			client = SearchHelp.getSearchClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		resp.setCode(ResponseVo.CODE_COMMON_SUCCESS);
		resp.setCodeMsg(ResponseVo.CODE_MAP.get(ResponseVo.CODE_COMMON_SUCCESS));
		resp.setDatas(queryNear(searchParam,client));
		return resp;
	} 
	
	@RequestMapping(value = "/diseaseMoreSearch")
	@ResponseBody
	public ResponseVo<SearchResultVo> diseaseMoreSearch(SearchParamDto searchParam){
		ResponseVo<SearchResultVo> resp = new ResponseVo<SearchResultVo>();
		Client client = null;
		try {
			client = SearchHelp.getSearchClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		resp.setCode(ResponseVo.CODE_COMMON_SUCCESS);
		resp.setCodeMsg(ResponseVo.CODE_MAP.get(ResponseVo.CODE_COMMON_SUCCESS));
		resp.setDatas(queryDisease(searchParam,client));
		return resp;
	}
	
	@RequestMapping(value = "/symptomMoreSearch")
	@ResponseBody
	public ResponseVo<SearchResultVo> symptomMoreSearch(SearchParamDto searchParam){
		ResponseVo<SearchResultVo> resp = new ResponseVo<SearchResultVo>();
		Client client = null;
		try {
			client = SearchHelp.getSearchClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		resp.setCode(ResponseVo.CODE_COMMON_SUCCESS);
		resp.setCodeMsg(ResponseVo.CODE_MAP.get(ResponseVo.CODE_COMMON_SUCCESS));
		resp.setDatas(querySymptom(searchParam,client));
		return resp;
	}
	
	@RequestMapping(value = "/hospitalMoreSearch")
	@ResponseBody
	public ResponseVo<SearchResultVo> hospitalMoreSearch(SearchParamDto searchParam){
		ResponseVo<SearchResultVo> resp = new ResponseVo<SearchResultVo>();
		Client client = null;
		try {
			client = SearchHelp.getSearchClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		resp.setCode(ResponseVo.CODE_COMMON_SUCCESS);
		resp.setCodeMsg(ResponseVo.CODE_MAP.get(ResponseVo.CODE_COMMON_SUCCESS));
		resp.setDatas(queryHospital(searchParam,client));
		return resp;
	}
	
	@RequestMapping(value = "/departmentMoreSearch")
	@ResponseBody
	public ResponseVo<SearchResultVo> departmentMoreSearch(SearchParamDto searchParam){
		ResponseVo<SearchResultVo> resp = new ResponseVo<SearchResultVo>();
		Client client = null;
		try {
			client = SearchHelp.getSearchClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		resp.setCode(ResponseVo.CODE_COMMON_SUCCESS);
		resp.setCodeMsg(ResponseVo.CODE_MAP.get(ResponseVo.CODE_COMMON_SUCCESS));
		resp.setDatas(queryDepartment(searchParam,client));
		return resp;
	}
	
	@RequestMapping(value = "/doctorMoreSearch")
	@ResponseBody
	public ResponseVo<SearchResultVo> doctorMoreSearch(SearchParamDto searchParam){
		ResponseVo<SearchResultVo> resp = new ResponseVo<SearchResultVo>();
		Client client = null;
		try {
			client = SearchHelp.getSearchClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		resp.setCode(ResponseVo.CODE_COMMON_SUCCESS);
		resp.setCodeMsg(ResponseVo.CODE_MAP.get(ResponseVo.CODE_COMMON_SUCCESS));
		resp.setDatas(queryDoctor(searchParam,client));
		return resp;
	}
	
	@RequestMapping(value = "/promptSearch")
	@ResponseBody
	public ResponseVo<List<PromptSearchVo>> promptSearch(SearchParamDto searchParam){
		ResponseVo<List<PromptSearchVo>> resp = new ResponseVo<List<PromptSearchVo>>();
		Client client = null;
		try {
			client = SearchHelp.getSearchClient();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		resp.setCode(ResponseVo.CODE_COMMON_SUCCESS);
		resp.setCodeMsg(ResponseVo.CODE_MAP.get(ResponseVo.CODE_COMMON_SUCCESS));
		//resp.setDatas(queryPrompt(keyword,client));
		resp.setDatas(querySearchPrompt(searchParam, client));
		return resp;
	}
	
	private SearchResultVo queryAll(SearchParamDto searchParam, Client client) {
		Integer from = searchParam.getFrom()==null?0:searchParam.getFrom();
		Integer size = searchParam.getPageSize()==null?0:searchParam.getPageSize();
		MultiSearchResponse response = client.prepareMultiSearch()
				//搜索疾病索引
				.add(client.prepareSearch(DISEASE_INDEX)// 检索的目录
					.setSearchType(SearchType.DEFAULT)// Query type				
					.setQuery(QueryBuilders.disMaxQuery()
				              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
				             // .add(QueryBuilders.matchQuery("intro", searchParam.getKeyword()))
							)	
					
					.addHighlightedField("name")
					//.addHighlightedField("intro")
					.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
					.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
					.setFrom(from).setSize(size).setExplain(true))
				//搜索医生索引
				.add(client.prepareSearch(DOCTOR_INDEX)// 检索的目录
						.setSearchType(SearchType.DEFAULT)// Query type				
						.setQuery(QueryBuilders.disMaxQuery()
					              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
					              .add(QueryBuilders.matchQuery("hospital_name", searchParam.getKeyword()))
					              .add(QueryBuilders.matchQuery("department_name", searchParam.getKeyword()))
					              .add(QueryBuilders.matchQuery("disease_tag", searchParam.getKeyword()))
								)	
						.setPostFilter(QueryBuilders.termQuery("city", searchParam.getCity()))
						.addHighlightedField("name")
						.addHighlightedField("hospital_name")
						.addHighlightedField("department_name")
						.addHighlightedField("disease_tag")
						.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
						.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
						.setFrom(from).setSize(size).setExplain(true))
				//搜索症状索引
				.add(client.prepareSearch(SYMPTOM_INDEX)// 检索的目录
						.setSearchType(SearchType.DEFAULT)// Query type				
						.setQuery(QueryBuilders.disMaxQuery()
					              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
					             // .add(QueryBuilders.matchQuery("intro", searchParam.getKeyword()))
					              )	
						
						.addHighlightedField("name")
						//.addHighlightedField("intro")
						.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
						.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
						.setFrom(from).setSize(size).setExplain(true))
				//搜索医院索引
				.add(client.prepareSearch(HOSPITAL_INDEX)// 检索的目录
						.setSearchType(SearchType.DEFAULT)// Query type				
						.setQuery(QueryBuilders.disMaxQuery()
					              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
					              //.add(QueryBuilders.matchQuery("content", searchParam.getKeyword()))
								)	
						.setPostFilter(QueryBuilders.termQuery("city", searchParam.getCity()))
						.addHighlightedField("name")
						//.addHighlightedField("content")
						.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
						.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
						.setFrom(from).setSize(size).setExplain(true))
				//搜索科室索引
				.add(client.prepareSearch(DEPARTMENT_INDEX)// 检索的目录
						.setSearchType(SearchType.DEFAULT)// Query type				
						.setQuery(QueryBuilders.disMaxQuery()
					              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
					              //.add(QueryBuilders.matchQuery("content", searchParam.getKeyword()))
					              
								)	
						.setPostFilter(QueryBuilders.termQuery("city", searchParam.getCity()))
						.addHighlightedField("name")
						//.addHighlightedField("content")
						.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
						.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
						.setFrom(from).setSize(size).setExplain(true))
				.execute().actionGet();// 执行
				
		
		        SearchResultVo searchResultVo = new SearchResultVo();
				if(response.getResponses() != null) { 
					MultiSearchResponse.Item diseaseItem = response.getResponses().length>0?(response.getResponses())[0]:null;
					MultiSearchResponse.Item doctorItem = response.getResponses().length>1?(response.getResponses())[1]:null;
					MultiSearchResponse.Item symptomItem = response.getResponses().length>2?(response.getResponses())[2]:null;
					MultiSearchResponse.Item hospitalItem = response.getResponses().length>3?(response.getResponses())[3]:null;
					MultiSearchResponse.Item departmentItem = response.getResponses().length>4?(response.getResponses())[4]:null;

					
					if(diseaseItem!=null){
						SearchResponse diseasResp = diseaseItem.getResponse();
						buildDiseaseResult(diseasResp, searchResultVo);
					}
					
					if(doctorItem!=null){
						SearchResponse doctorResp = doctorItem.getResponse();
						buildDoctorResult(doctorResp, searchResultVo);
					}
					
		            if(symptomItem!=null){
						SearchResponse symptomResp = symptomItem.getResponse();
						buildSymptomResult(symptomResp,searchResultVo);
		            }
		            
		            if(hospitalItem!=null){
						SearchResponse hospitalResp = hospitalItem.getResponse();
						buildHospitalResult(hospitalResp,searchResultVo);
		            }
		            
		            if(departmentItem!=null){
						SearchResponse departmentResp = departmentItem.getResponse();
						buildDepartmentResult(departmentResp, searchResultVo);
		            }
		            
		        }
				return searchResultVo;
	}
	
	private List<PromptSearchVo> querySearchPrompt(SearchParamDto searchParam, Client client){
				SearchRequestBuilder requestBuilder = client.prepareSearch(PROMPT_INDEX);// 检索的目录
				if(SearchParamDto.HOSPITAL_TYPE.equals(searchParam.getType())){
					requestBuilder = requestBuilder.setPostFilter(QueryBuilders.termQuery("type","医院"));
				}
				SearchResponse sp =  requestBuilder.setSearchType(SearchType.DEFAULT)// Query type				
													.setQuery(QueryBuilders.disMaxQuery()
												              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
												     )
													.setSize(5)
													.execute().actionGet();// 执行
		
		
		
		
		List<PromptSearchVo> promptList = new ArrayList<PromptSearchVo>();
		if(sp!=null){
			
			for (SearchHit hits : sp.getHits().getHits()) {			
				Map<String, Object> sourceAsMap = hits.sourceAsMap();
	            String name = (String)sourceAsMap.get("name");
	            String type = sourceAsMap.get("type")==null?"":sourceAsMap.get("type").toString();
	            String code = sourceAsMap.get("code")==null?"":sourceAsMap.get("code").toString();
	            String hospitalName = sourceAsMap.get("hospital_name")==null?"":sourceAsMap.get("hospital_name").toString();
	            if("科室".equals(type)){
	            	name = hospitalName+"_"+name;
	            }
	            
	            PromptSearchVo promptSearchResult = new PromptSearchVo();
	            promptSearchResult.setName(name);
	            promptSearchResult.setType(type);
	            promptSearchResult.setCode(code);
	            promptList.add(promptSearchResult);
			}
		}
		
		return promptList;
	}
	
	private List<PromptSearchVo> queryPrompt(String keyword, Client client) {
		List<PromptSearchVo> promptList = new ArrayList<PromptSearchVo>();
		SuggestResponse sp = client.prepareSuggest(PROMPT_INDEX)// 检索的目录		
                .addSuggestion(SuggestBuilders.completionSuggestion("name").field("name").text(keyword).size(10))
				.execute().actionGet();// 执行
		
		if(sp.getSuggest() != null) {
            List<? extends org.elasticsearch.search.suggest.Suggest.Suggestion.Entry<? extends Option>> list = sp.getSuggest().getSuggestion("name").getEntries();
            if(list != null) {
                for (org.elasticsearch.search.suggest.Suggest.Suggestion.Entry<? extends Option> entry : list) {
                    for (Option option : entry) {
                        PromptSearchVo prompt = new PromptSearchVo();
                        prompt.setName(option.getText().toString());
                        promptList.add(prompt);
                        System.out.println(option.getText());
                    }
                }
            }
        }
		
		return promptList;
	}



	private SearchResultVo queryDoctor(SearchParamDto searchParam, Client client) {
		Integer from = searchParam.getFrom()==null?0:searchParam.getFrom();
		Integer size = searchParam.getPageSize()==null?0:searchParam.getPageSize();
		SearchResultVo searchResultVo = new SearchResultVo();

		SearchResponse sp = client.prepareSearch(DOCTOR_INDEX)// 检索的目录
				.setSearchType(SearchType.DEFAULT)// Query type				
				.setQuery(QueryBuilders.disMaxQuery()
			              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
			              .add(QueryBuilders.matchQuery("hospital_name", searchParam.getKeyword()))
			              .add(QueryBuilders.matchQuery("department_name", searchParam.getKeyword()))
			              .add(QueryBuilders.matchQuery("disease_tag", searchParam.getKeyword()))
			     )	
				.setPostFilter(QueryBuilders.termQuery("city", searchParam.getCity()))
				.addHighlightedField("name")
				.addHighlightedField("disease_tag")
				.addHighlightedField("hospital_name")
				.addHighlightedField("department_name")
				.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
				.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
				.setFrom(from).setSize(size).setExplain(true)// topN方式
				.execute().actionGet();// 执行

		buildDoctorResult(sp, searchResultVo);
		return searchResultVo;
	}



	private SearchResultVo queryDepartment(SearchParamDto searchParam, Client client) {
		Integer from = searchParam.getFrom()==null?0:searchParam.getFrom();
		Integer size = searchParam.getPageSize()==null?0:searchParam.getPageSize();
		String keyword = searchParam.getKeyword();
		SearchResultVo searchResultVo = new SearchResultVo();
		
		List<BdDepartment> departmentList = new ArrayList<BdDepartment>();
		SearchResponse sp = client.prepareSearch(DEPARTMENT_INDEX)// 检索的目录
				.setSearchType(SearchType.DEFAULT)// Query type				
				.setQuery(QueryBuilders.disMaxQuery()
			              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
			              //.add(QueryBuilders.matchQuery("content", searchParam.getKeyword()))
			     )	
				.setPostFilter(QueryBuilders.termQuery("city", searchParam.getCity()))
				.addHighlightedField("name")
				//.addHighlightedField("content")
				.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
				.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
				.setFrom(from).setSize(size).setExplain(true)// topN方式
				.execute().actionGet();// 执行
		
		buildDepartmentResult(sp, searchResultVo);
		return searchResultVo;
	
	}



	private SearchResultVo queryHospital(SearchParamDto searchParam, Client client) {
		Integer from = searchParam.getFrom()==null?0:searchParam.getFrom();
		Integer size = searchParam.getPageSize()==null?0:searchParam.getPageSize();
		String keyword = searchParam.getKeyword();
		SearchResultVo searchResultVo = new SearchResultVo();
		
		List<HospitalSearchVo> hospitalList = new ArrayList<HospitalSearchVo>();	
		SearchRequestBuilder searchRequestBuilder= client.prepareSearch(HOSPITAL_INDEX);// 检索的目录
		
		if(searchParam.getLatitude()!=null&&searchParam.getLongitude()!=null){
			searchRequestBuilder = searchRequestBuilder.addSort(SortBuilders.geoDistanceSort("location")
		            .unit(DistanceUnit.KILOMETERS)
		            .order(SortOrder.ASC)
		            .point(searchParam.getLatitude(),searchParam.getLongitude()));
		}
		
		
		SearchResponse sp =searchRequestBuilder.setQuery(QueryBuilders.disMaxQuery()
			              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
			              //.add(QueryBuilders.matchQuery("content", searchParam.getKeyword()))
			     )	
				
				.addHighlightedField("name")
				//.addHighlightedField("content")
				.setPostFilter(QueryBuilders.termQuery("city", searchParam.getCity()))
				
				.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
				.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
				
				.setFrom(from).setSize(size).setExplain(true)// topN方式
				.execute().actionGet();// 执行
		
		
		

		buildHospitalResult(sp, searchResultVo);
		return searchResultVo;
	
	}



	private SearchResultVo querySymptom(SearchParamDto searchParam, Client client) {
		Integer from = searchParam.getFrom()==null?0:searchParam.getFrom();
		Integer size = searchParam.getPageSize()==null?0:searchParam.getPageSize();
		String keyword = searchParam.getKeyword();
		SearchResultVo searchResultVo = new SearchResultVo();
		
		
		SearchResponse sp = client.prepareSearch(SYMPTOM_INDEX)// 检索的目录
				.setSearchType(SearchType.DEFAULT)// Query type				
				.setQuery(QueryBuilders.disMaxQuery()
			               .add(QueryBuilders.matchQuery("name", keyword))
			              // .add(QueryBuilders.matchQuery("intro", keyword))
			     )	
				.addHighlightedField("name")
				//.addHighlightedField("intro")
				.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
				.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
				.setFrom(from).setSize(size).setExplain(true)// topN方式
				.execute().actionGet();// 执行
		buildSymptomResult(sp, searchResultVo);
		return searchResultVo;
	
	}



	private SearchResultVo queryDisease(SearchParamDto searchParam, Client client) {
		Integer from = searchParam.getFrom()==null?0:searchParam.getFrom();
		Integer size = searchParam.getPageSize()==null?0:searchParam.getPageSize();
		String keyword = searchParam.getKeyword();
		SearchResultVo searchResultVo = new SearchResultVo();
		
		
		SearchResponse sp = client.prepareSearch(DISEASE_INDEX)// 检索的目录
				.setSearchType(SearchType.DEFAULT)// Query type				
				.setQuery(QueryBuilders.disMaxQuery()
			               .add(QueryBuilders.matchQuery("name", keyword))
			               //.add(QueryBuilders.matchQuery("intro", keyword))
			               )	
				.addHighlightedField("name")
				//.addHighlightedField("intro")
				.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
				.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
//				.addSort("price", SortOrder.DESC) // 排序 -- sort
				.setFrom(from).setSize(size).setExplain(true)// topN方式
				.execute().actionGet();// 执行
		buildDiseaseResult(sp, searchResultVo);
		return searchResultVo;
	
	}
	
	public SearchResultVo queryDiseaseOrDoctor(SearchParamDto searchParam,Client client){	
		Integer from = searchParam.getFrom()==null?0:searchParam.getFrom();
		Integer size = searchParam.getPageSize()==null?0:searchParam.getPageSize();
		MultiSearchResponse response = client.prepareMultiSearch()
				//搜索疾病索引
				.add(client.prepareSearch(DISEASE_INDEX)// 检索的目录
					.setSearchType(SearchType.DEFAULT)// Query type				
					.setQuery(QueryBuilders.disMaxQuery()
				              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
				              //.add(QueryBuilders.matchQuery("intro", searchParam.getKeyword()))
				              )	
					.addHighlightedField("name")
					//.addHighlightedField("intro")
					
					.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
					.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
					.setFrom(from).setSize(size).setExplain(true))
				//搜索医生索引
				.add(client.prepareSearch(DOCTOR_INDEX)// 检索的目录
						.setSearchType(SearchType.DEFAULT)// Query type				
						.setQuery(QueryBuilders.disMaxQuery()
					              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
					              .add(QueryBuilders.matchQuery("hospital_name", searchParam.getKeyword()))
					              .add(QueryBuilders.matchQuery("department_name", searchParam.getKeyword()))
					              .add(QueryBuilders.matchQuery("disease_tag", searchParam.getKeyword()))
					               
								)
						.setPostFilter(QueryBuilders.termQuery("city", searchParam.getCity()))
						.addHighlightedField("name")
						.addHighlightedField("disease_tag")
						.addHighlightedField("hospital_name")
						.addHighlightedField("department_name")
						.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
						.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
						.setFrom(from).setSize(size).setExplain(true))
				.execute().actionGet();// 执行
				
		        SearchResultVo searchResultVo = new SearchResultVo();
				List<BdDisease> diseaseList = new ArrayList<BdDisease>();
				List<BdDoctor> doctorList = new ArrayList<BdDoctor>();
				if(response.getResponses() != null) { 
					MultiSearchResponse.Item diseaseItem = response.getResponses().length>0?(response.getResponses())[0]:null;
					MultiSearchResponse.Item doctorItem = response.getResponses().length>1?(response.getResponses())[1]:null;
					
					if(diseaseItem!=null){
						SearchResponse diseasResp = diseaseItem.getResponse();
						buildDiseaseResult(diseasResp, searchResultVo);
						
					}
					
					if(doctorItem!=null){
						SearchResponse doctorResp = doctorItem.getResponse();
						buildDoctorResult(doctorResp, searchResultVo);
					}
		            
		        }
		
		return searchResultVo;
	}
	
	private SearchResultVo queryNear(SearchParamDto searchParam, Client client) {
		Integer from = searchParam.getFrom()==null?0:searchParam.getFrom();
		Integer size = searchParam.getPageSize()==null?0:searchParam.getPageSize();
		MultiSearchResponse response = client.prepareMultiSearch()
				//搜索医生索引
				.add(client.prepareSearch(DOCTOR_INDEX)// 检索的目录
						.setSearchType(SearchType.DEFAULT)// Query type				
						.setQuery(QueryBuilders.disMaxQuery()
					              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
					              .add(QueryBuilders.matchQuery("hospital_name", searchParam.getKeyword()))
					              .add(QueryBuilders.matchQuery("department_name", searchParam.getKeyword()))
					              .add(QueryBuilders.matchQuery("disease_tag", searchParam.getKeyword()))
								)	
						.setPostFilter(QueryBuilders.termQuery("city", searchParam.getCity()))
						.addHighlightedField("name")
						.addHighlightedField("disease_tag")
						.addHighlightedField("hospital_name")
						.addHighlightedField("department_name")
						.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
						.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
						.addSort(SortBuilders.geoDistanceSort("location")
					            .unit(DistanceUnit.KILOMETERS)
					            .order(SortOrder.ASC)
					            .point(searchParam.getLatitude(),searchParam.getLongitude()))
						 //分页
						.setFrom(from).setSize(size).setExplain(true))
				//搜索医院索引
				.add(client.prepareSearch(HOSPITAL_INDEX)// 检索的目录
						.setSearchType(SearchType.DEFAULT)// Query type				
						.setQuery(QueryBuilders.disMaxQuery()
					              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
					              //.add(QueryBuilders.matchQuery("content", searchParam.getKeyword()))
								)	
						.setPostFilter(QueryBuilders.termQuery("city", searchParam.getCity()))
						.addHighlightedField("name")
						//.addHighlightedField("content")
						.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
						.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
						.addSort(SortBuilders.geoDistanceSort("location")
					            .unit(DistanceUnit.KILOMETERS)
					            .order(SortOrder.ASC)
					            .point(searchParam.getLatitude(),searchParam.getLongitude()))
						.setFrom(from).setSize(size).setExplain(true))
				//搜索科室索引
				.add(client.prepareSearch(DEPARTMENT_INDEX)// 检索的目录
						.setSearchType(SearchType.DEFAULT)// Query type				
						.setQuery(QueryBuilders.disMaxQuery()
					              .add(QueryBuilders.matchQuery("name", searchParam.getKeyword()))
					             //.add(QueryBuilders.matchQuery("content", searchParam.getKeyword()))
								)	
						.setPostFilter(QueryBuilders.termQuery("city", searchParam.getCity()))
						.addHighlightedField("name")
						//.addHighlightedField("content")
						.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
						.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
						.addSort(SortBuilders.geoDistanceSort("location")
					            .unit(DistanceUnit.KILOMETERS)
					            .order(SortOrder.ASC)
					            .point(searchParam.getLatitude(),searchParam.getLongitude()))
						.setFrom(from).setSize(size).setExplain(true))
				.execute().actionGet();// 执行	
		
		        SearchResultVo searchResultVo = new SearchResultVo();
				List<BdDoctor> doctorList = new ArrayList<BdDoctor>();
				List<HospitalSearchVo> hospitalList = new ArrayList<HospitalSearchVo>();
				List<BdDepartment> departmentList = new ArrayList<BdDepartment>();

				if(response.getResponses() != null) { 
					MultiSearchResponse.Item doctorItem = response.getResponses().length>0?(response.getResponses())[0]:null;
					MultiSearchResponse.Item hospitalItem = response.getResponses().length>1?(response.getResponses())[1]:null;
					MultiSearchResponse.Item departmentItem = response.getResponses().length>2?(response.getResponses())[2]:null;
					
					if(doctorItem!=null){
						SearchResponse doctorResp = doctorItem.getResponse();
						buildDoctorResult(doctorResp, searchResultVo);
					}
		            
		            if(hospitalItem!=null){
						SearchResponse hospitalResp = hospitalItem.getResponse();
						buildHospitalResult(hospitalResp, searchResultVo);
		            }
		            
		            if(departmentItem!=null){
						SearchResponse departmentResp = departmentItem.getResponse();
						buildDepartmentResult(departmentResp, searchResultVo);
		            }
		            
		        }
				return searchResultVo;
	}
	
	public SearchResultVo querySymptomOrDoctor(SearchParamDto searchParam,Client client){
		Integer from = searchParam.getFrom()==null?0:searchParam.getFrom();
		Integer size = searchParam.getPageSize()==null?0:searchParam.getPageSize();
		String keyword = searchParam.getKeyword()==null?"":searchParam.getKeyword();
		MultiSearchResponse response = client.prepareMultiSearch()
				//搜索症状索引
				.add(client.prepareSearch(SYMPTOM_INDEX)// 检索的目录
					.setSearchType(SearchType.DEFAULT)// Query type				
					.setQuery(QueryBuilders.disMaxQuery()
				              .add(QueryBuilders.matchQuery("name", keyword))
				             // .add(QueryBuilders.matchQuery("intro", keyword))
				              )	
					.addHighlightedField("name")
					//.addHighlightedField("intro")
					.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
					.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
					.setFrom(from).setSize(size).setExplain(true))
				//搜索医生索引
				.add(client.prepareSearch(DOCTOR_INDEX)// 检索的目录
						.setSearchType(SearchType.DEFAULT)// Query type				
						.setQuery(QueryBuilders.disMaxQuery()
					              .add(QueryBuilders.matchQuery("name", keyword))
					              .add(QueryBuilders.matchQuery("hospital_name", searchParam.getKeyword()))
					              .add(QueryBuilders.matchQuery("department_name", searchParam.getKeyword()))
					              .add(QueryBuilders.matchQuery("disease_tag", keyword))
								)	
						.setPostFilter(QueryBuilders.termQuery("city", searchParam.getCity()))
						.addHighlightedField("name")
						.addHighlightedField("disease_tag")
						.addHighlightedField("hospital_name")
						.addHighlightedField("department_name")
						.setHighlighterPreTags(FIELD_HIGHLIGHT_PRE_TAG)
						.setHighlighterPostTags(FIELD_HIGHLIGHT_POST_TAG)
						.setFrom(from).setSize(size).setExplain(true))
				.execute().actionGet();// 执行
				
		        SearchResultVo searchResultVo = new SearchResultVo();
				List<BdBodySymptom> symptomList = new ArrayList<BdBodySymptom>();
				List<BdDoctor> doctorList = new ArrayList<BdDoctor>();
				if(response.getResponses() != null) { 
					MultiSearchResponse.Item symptomItem = response.getResponses().length>0?(response.getResponses())[0]:null;
					MultiSearchResponse.Item doctorItem = response.getResponses().length>1?(response.getResponses())[1]:null;
					
					if(symptomItem!=null){
						SearchResponse symptomResp = symptomItem.getResponse();
						buildSymptomResult(symptomResp, searchResultVo);
						
					}
					
					if(doctorItem!=null){
						SearchResponse doctorResp = doctorItem.getResponse();
						buildDoctorResult(doctorResp, searchResultVo);
						
					}
		            
		        }
		
		return searchResultVo;
	}
	
	private void buildHospitalResult(SearchResponse hospitalResp,SearchResultVo searchResultVo){
		if(hospitalResp!=null){
			System.out.println("命中医院条数: " + hospitalResp.getHits().getTotalHits());
			
			List<HospitalSearchVo> hospitalList = new ArrayList<HospitalSearchVo>();
			searchResultVo.setHospitalTotal(hospitalResp.getHits().getTotalHits());
			for (SearchHit hits : hospitalResp.getHits().getHits()) {			
				Map<String, Object> sourceAsMap = hits.sourceAsMap();
				 //获取对应的高亮域
	            Map<String, HighlightField> result = hits.highlightFields();  
	            
	            //从设定的高亮域中取得指定域
	            HighlightField highlightFieldText = result.get("name");  
	            HighlightField contentField = result.get("content");
	            
	            String code = (String)sourceAsMap.get("code");
	            String name = getHighlightFieldText(highlightFieldText);
	            name = StringUtils.isEmpty(name)?(String)sourceAsMap.get("name"):name;
	            String content = getHighlightFieldText(contentField);
	            content = StringUtils.isEmpty(content)?(String)sourceAsMap.get("content"):content;
	            String score = sourceAsMap.get("score")==null?"":sourceAsMap.get("score").toString();
	            String level = sourceAsMap.get("level")==null?"":sourceAsMap.get("level").toString();
	            String isMedicalInsurance = sourceAsMap.get("is_medical_insurance")==null?"":sourceAsMap.get("is_medical_insurance").toString();
	            String address = sourceAsMap.get("address")==null?"":sourceAsMap.get("address").toString();
	            
	            String distance = "";
	            if(hits.getSortValues()!=null&&hits.getSortValues().length>0){
		            BigDecimal geoDis=new BigDecimal(Double.valueOf(String.valueOf(hits.getSortValues()[0])));  
		            distance = geoDis==null?"":geoDis.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();
	            }
	            
	            
	            HospitalSearchVo bdHospital = new HospitalSearchVo();
	            bdHospital.setCode(code);
	            bdHospital.setName(name);
	            bdHospital.setScore(score);
	            bdHospital.setLevel(level);
	            bdHospital.setIsMedicalInsurance(isMedicalInsurance);
	            bdHospital.setContent(content);
	            bdHospital.setAddress(address);
	            bdHospital.setDistance(distance);
	            hospitalList.add(bdHospital);
			}
			searchResultVo.setHospitalList(hospitalList);
		}
	}
	
	private void buildDoctorResult(SearchResponse doctorResp,SearchResultVo searchResultVo){
		if(doctorResp!=null){
			List<BdDoctor> doctorList = new ArrayList<BdDoctor>();
			System.out.println("命中医生条数: " + doctorResp.getHits().getTotalHits());
			searchResultVo.setDoctorTotal(doctorResp.getHits().getTotalHits());
			for (SearchHit hits : doctorResp.getHits().getHits()) {			
				Map<String, Object> sourceAsMap = hits.sourceAsMap();
				
				 //获取对应的高亮域
	            Map<String, HighlightField> result = hits.highlightFields();  
	            //从设定的高亮域中取得指定域
	           
	            String code = (String)sourceAsMap.get("code");
	            String practiceTitle = (String)sourceAsMap.get("practice_title");
	            String imageUrl = (String)sourceAsMap.get("image_url");
	            String recommendScore = sourceAsMap.get("recommend_score")==null?"":sourceAsMap.get("recommend_score").toString();
	            
	            HighlightField departmentNameFieldText = result.get("department_name"); 
	            String departmentName = getHighlightFieldText(departmentNameFieldText);
	            departmentName = StringUtils.isEmpty(departmentName)?(String)sourceAsMap.get("department_name"):departmentName;;
	            
	            HighlightField hospitalNameFieldText = result.get("hospital_name"); 
	            String hospitalName = getHighlightFieldText(hospitalNameFieldText);
	            hospitalName = StringUtils.isEmpty(hospitalName)?(String)sourceAsMap.get("hospital_name"):hospitalName;;

	            HighlightField highlightFieldText = result.get("name"); 
	            String name = getHighlightFieldText(highlightFieldText);
	            name = StringUtils.isEmpty(name)?(String)sourceAsMap.get("name"):name;
	            
	            HighlightField diseaseTagField = result.get("disease_tag");
	            String diseaseTag = getHighlightFieldText(diseaseTagField);
	            diseaseTag = StringUtils.isEmpty(diseaseTag)?(String)sourceAsMap.get("disease_tag"):diseaseTag;
	            
	            
	            
	            BdDoctor bdDoctorRpc = new BdDoctor();
	            bdDoctorRpc.setName(name);
	            bdDoctorRpc.setCode(code);
	            bdDoctorRpc.setImageUrl(imageUrl);
	            bdDoctorRpc.setPracticeTitle(practiceTitle);
	            bdDoctorRpc.setRecommendScore(recommendScore);
	            bdDoctorRpc.setDiseaseTag(diseaseTag);
	            bdDoctorRpc.setHospitalName(hospitalName);
	            bdDoctorRpc.setDepartmentName(departmentName);
	            doctorList.add(bdDoctorRpc);
			}
			searchResultVo.setDoctorList(doctorList);
		}
	}
	
	private void buildDepartmentResult(SearchResponse departmentResp,SearchResultVo searchResultVo){
		if(departmentResp!=null){
			List<BdDepartment> departmentList = new ArrayList<BdDepartment>();
			System.out.println("命中科室条数: " + departmentResp.getHits().getTotalHits());
			searchResultVo.setDepartmentTotal(departmentResp.getHits().getTotalHits());
			for (SearchHit hits : departmentResp.getHits().getHits()) {			
				Map<String, Object> sourceAsMap = hits.sourceAsMap();
				 //获取对应的高亮域
	            Map<String, HighlightField> result = hits.highlightFields();  
	            
	            //从设定的高亮域中取得指定域
	            HighlightField highlightFieldText = result.get("name");  
	            HighlightField contentField = result.get("content");
	            
	            String code = (String)sourceAsMap.get("code");
	            String name = getHighlightFieldText(highlightFieldText);
	            name = StringUtils.isEmpty(name)?(String)sourceAsMap.get("name"):name;
	            String content = getHighlightFieldText(contentField);
	            content = StringUtils.isEmpty(content)?(String)sourceAsMap.get("content"):content;
	            String hospitalSource = sourceAsMap.get("hospital_source")==null?"":(String)sourceAsMap.get("hospital_source");
	            
	            BdDepartment bdDepartmentRpc = new BdDepartment();
	            bdDepartmentRpc.setCode(code);
	            bdDepartmentRpc.setName(name);
	            bdDepartmentRpc.setContent(content);
	            bdDepartmentRpc.setHospitalSource(hospitalSource);
	            departmentList.add(bdDepartmentRpc);
			}
			searchResultVo.setDepartmentList(departmentList);
		}
	}
	
	private void buildDiseaseResult(SearchResponse sp,SearchResultVo searchResultVo){
		if(sp!=null){
			List<BdDisease> diseaseList = new ArrayList<BdDisease>();
			System.out.println("本次查询命中条数: " + sp.getHits().getTotalHits());
			searchResultVo.setDiseaseTotal(sp.getHits().getTotalHits());
			for (SearchHit hits : sp.getHits().getHits()) {			
				Map<String, Object> sourceAsMap = hits.sourceAsMap();
				 //获取对应的高亮域
	            Map<String, HighlightField> result = hits.highlightFields();  
	            
	            //从设定的高亮域中取得指定域
	            HighlightField highlightFieldText = result.get("name");  
	            String code = (String)sourceAsMap.get("code");
	            HighlightField introField = result.get("intro");
	            
	            String name = getHighlightFieldText(highlightFieldText);
	            name = StringUtils.isEmpty(name)?(String)sourceAsMap.get("name"):name;
	            
	            String intro = getHighlightFieldText(introField);
	            intro = StringUtils.isEmpty(intro)?(String)sourceAsMap.get("intro"):intro;
	            
	            BdDisease bdDiseaseRpc = new BdDisease();
	            bdDiseaseRpc.setName(name);
	            bdDiseaseRpc.setCode(code);
	            bdDiseaseRpc.setIntro(intro);
	            
	            diseaseList.add(bdDiseaseRpc);
			}
			searchResultVo.setDiseaseList(diseaseList);
		}
	}
	
	private void buildSymptomResult(SearchResponse symptomResp,SearchResultVo searchResultVo){
		if(symptomResp!=null){
			System.out.println("命中症状条数: " + symptomResp.getHits().getTotalHits());
			List<BdBodySymptom> symptomList = new ArrayList<BdBodySymptom>();
			searchResultVo.setSymptomTotal(symptomResp.getHits().getTotalHits());
			for (SearchHit hits : symptomResp.getHits().getHits()) {			
				Map<String, Object> sourceAsMap = hits.sourceAsMap();
				 //获取对应的高亮域
	            Map<String, HighlightField> result = hits.highlightFields();  
	            
	            //从设定的高亮域中取得指定域
	            HighlightField highlightFieldText = result.get("name");  
//	            HighlightField introField = result.get("intro");
	            HighlightField introField = result.get("intro_text");
	            
	            String code = (String)sourceAsMap.get("code");
	            String name = getHighlightFieldText(highlightFieldText);
	            name = StringUtils.isEmpty(name)?(String)sourceAsMap.get("name"):name;
	            String intro = getHighlightFieldText(introField);
	            intro = StringUtils.isEmpty(intro)?(String)sourceAsMap.get("intro_text"):intro;
	            
	            BdBodySymptom bdBodySymptomRpc = new BdBodySymptom();
	            bdBodySymptomRpc.setCode(code);
	            bdBodySymptomRpc.setName(name);
	            bdBodySymptomRpc.setIntro(intro);
	            symptomList.add(bdBodySymptomRpc);
			}
			searchResultVo.setSymptomList(symptomList);
		}
	}
	
	public String getHighlightFieldText(HighlightField highlightFieldText){
		if(highlightFieldText==null){
			return "";
		}
		
        //取得定义的高亮标签
        Text[] higlightTexts =  highlightFieldText.fragments();
        
        //为title串值增加自定义的高亮标签
        String higlightText = "";  
        for(Text text : higlightTexts){
        	higlightText += text;  
        }
        return higlightText;
	}
}
