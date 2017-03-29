package com.pz998.search.init;

import static org.elasticsearch.common.settings.Settings.settingsBuilder;

import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.alias.exists.AliasesExistRequestBuilder;
import org.elasticsearch.action.admin.indices.alias.exists.AliasesExistResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.suggest.SuggestResponse;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.Suggest.Suggestion.Entry.Option;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
/**
* @author mhl
* @version 创建时间：2016年11月21日
* 
*/
public class SearchTest {
    TransportClient client = null; {
        client = TransportClient.builder().settings(settings()).build();
        
//        client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("10.3.32.81", 9300)))
//        .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("10.3.32.82", 9300)))
//        .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("10.3.32.83", 9300)));
        
        client.addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("10.3.32.53", 9300)))
        .addTransportAddress(new InetSocketTransportAddress(new InetSocketAddress("10.3.32.54", 9300)));
    }
    
    protected TransportClient client() {
        return client;
    }
    
    protected Settings settings() {
        return settingsBuilder()
        		
//                .put("cluster.name", "es-cluster")
//                .put("client.transport.sniff", true)
//                .put("client.transport.ignore_cluster_name", false)
//                .put("client.transport.ping_timeout", "5s")
//                .put("client.transport.nodes_sampler_interval", "5s")
        		
                .put("cluster.name", "elasticsearch")
                .put("client.transport.sniff", true)
                .put("client.transport.ignore_cluster_name", false)
                .put("client.transport.ping_timeout", "5s")
                .put("client.transport.nodes_sampler_interval", "5s")
                .build();
    }
    
	public void createDiseaseMapping() {
		try{
			new XContentFactory();
				XContentBuilder builder=XContentFactory.jsonBuilder()
				.startObject()
				.startObject("disease")
				.startObject("properties")
				//id
				.startObject("id")
				.field("type", "long")
				.field("store", "yes")
				.field("include_in_all", "true")
				.endObject()
				//code
				//index: 'not_analyzed' 
				.startObject("code")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				source_id	varchar
				.startObject("source_id")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				name	varchar
				.startObject("name")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik")
				.field("store", "yes")
				.endObject()
//				intro	text
				.startObject("intro")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				diagnosis_descr	text
				.startObject("diagnosis_descr")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				treatment_descr	text
				.startObject("treatment_descr")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				pathogeny_descr	text
				.startObject("pathogeny_descr")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				phenomenon_descr	text
				.startObject("phenomenon_descr")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				source_type	smallint
				.startObject("source_type")
				.field("type", "integer")
				.field("store", "yes")
				.endObject()
//				department_id	bigint
				.startObject("department_id")
				.field("type", "long")
				.field("store", "yes")
				.endObject()
//				department_code	varchar
				.startObject("department_code")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()		
				//create_time
				.startObject("create_time")
				.field("type", "date")
				.field("format", "strict_date_optional_time||epoch_millis")
				.endObject()
				//create_by
				.startObject("create_by")
				.field("type", "long")
				.field("store", "yes")
				.endObject()
				//update_time
				.startObject("update_time")
				.field("type", "date")
				.field("format", "strict_date_optional_time||epoch_millis")
				.endObject()
				//update_by
				.startObject("update_by")
				.field("type", "long")
				.field("store", "yes")
				.endObject()
				//is_deleted
				.startObject("is_deleted")
				.field("type", "integer")
				.field("store", "yes")
				.endObject()
				
				.endObject()
				.endObject()
				.endObject();
				PutMappingRequest mapping = Requests.putMappingRequest("disease").type("disease").source(builder);
				client.admin().indices().putMapping(mapping).actionGet();
				client.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
    
    public static void main(String[] args) throws Exception {
        SearchTest search = new SearchTest();
        /*search.searchAll("productAlias");
        //多字段查询
        TreeMap<String,Object> fields = new TreeMap<String,Object>();
        fields.put("intro", "糖尿病123");
        search.searchByFields("disease_ik","disease_ik",fields);
        
        //按照某一field排序
        search.sortByField("hospital","hospital","score");
        
        //拼写建议  需要建索引时，对应的field设置为type:completion  payloads:true
        search.suggestionByField("hospital","name","武");
        
        //多索引查询
        search.mutilIndexSearch();
        
        //分组查询
        search.aggregationSearch("hospital","hospital","city","北京");*/
        
        // geo, 需要建索引设置
        /**
         * location  "sql" : "SELECT bh.*,bh.latitude AS \"location.lat\", bh.longitude as \"location.lon\" FROM bd_hospital bh;",
         * startObject("location").field("type", "geo_point").field("store", "yes").endObject()
         */
        //search.geoSearch();
        
        //给索引添加一个别名，并且用别名搜索
        //search.createAliases("product", "productAlias");
        
        //删除别名并且重新建别名
        //search.changeAliases("product","product1","productAlias");
        
        //自动补全
        /**
         * sql: SELECT bh.*,bh.name as nameSuggest,bh.latitude AS \"location.lat\", bh.longitude as \"location.lon\" FROM bd_hospital bh;
         * 增加field 
         * .startObject("nameSuggest")
            .field("type", "completion")
            .field("analyzer", "ik")
            .field("payloads", true)
            .field("preserve_separators", false)
            .field("preserve_position_increments", false)
            .field("max_input_length",10)
            .endObject()
         */
        //search.autoCompletion();
        //search.geoSearch();
        

        //search.createCluterName("doctor");
        //search.createDoctorMapping();

        //search.createCluterName("doctor");
        //search.createDoctorMapping();
        
        //search.createCluterName("department");
        //search.createDepartmentMapping();
        
        //search.createCluterName("symptom");
        //search.createSymptomMapping();
        
        //search.createCluterName("hospital");
        //search.createHospitalMapping();
        
         //search.createCluterName("disease");
         //search.createDiseaseMapping();
        
        //search.createCluterName("prompt");
        search.createPromptMapping();
        
        search.client.close();
    }
    
    public  void createCluterName(String indices){
		client.admin().indices().prepareCreate(indices).execute().actionGet();
		client.close();
	}
    
    public void createSymptomMapping() {
		try{
			new XContentFactory();
				XContentBuilder builder=XContentFactory.jsonBuilder()
				.startObject()
				.startObject("symptom")
				.startObject("properties")
				//id
				.startObject("id")
				.field("type", "long")
				.field("store", "yes")
				.field("include_in_all", "true")
				.endObject()
				//code
				//index: 'not_analyzed' 
				.startObject("code")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				source_id	varchar
				.startObject("source_id")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				name	varchar
				.startObject("name")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				body_area_source_id	varchar
				.startObject("body_area_source_id")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				body_area_id	bigint
				.startObject("body_area_id")
				.field("type", "long")
				.field("store", "yes")
				.endObject()
//				body_area_code	varchar
				.startObject("body_area_code")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				source_type	smallint
				.startObject("source_type")
				.field("type", "integer")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				recommend_department_name	varchar
				.startObject("recommend_department_name")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				diagnosis_descr	text
				.startObject("diagnosis_descr")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				intro	text
				.startObject("intro")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				intro_text	text
				.startObject("intro_text")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				treatment_descr	text
				.startObject("treatment_descr")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				pathogeny_descr	text
				.startObject("pathogeny_descr")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				prevention_descr	text
				.startObject("prevention_descr")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				examination_descr	text
				.startObject("examination_descr")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				maybe_diseases	varchar
				.startObject("maybe_diseases")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				is_child	tinyint
				.startObject("is_child")
				.field("type", "integer")
				.field("store", "yes")
				.endObject()
//				is_man	tinyint
				.startObject("is_man")
				.field("type", "integer")
				.field("store", "yes")
				.endObject()
//				is_woman	tinyint
				.startObject("is_woman")
				.field("type", "integer")
				.field("store", "yes")
				.endObject()
				//create_time
				.startObject("create_time")
				.field("type", "date")
				.field("format", "strict_date_optional_time||epoch_millis")
				.endObject()
				//create_by
				.startObject("create_by")
				.field("type", "long")
				.field("store", "yes")
				.endObject()
				//update_time
				.startObject("update_time")
				.field("type", "date")
				.field("format", "strict_date_optional_time||epoch_millis")
				.endObject()
				//update_by
				.startObject("update_by")
				.field("type", "long")
				.field("store", "yes")
				.endObject()
				//is_deleted
				.startObject("is_deleted")
				.field("type", "integer")
				.field("store", "yes")
				.endObject()

				
				.endObject()
				.endObject()
				.endObject();
				PutMappingRequest mapping = Requests.putMappingRequest("symptom").type("symptom").source(builder);
				client.admin().indices().putMapping(mapping).actionGet();
				client.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
    
    public void createPromptMapping() {
		try{
			new XContentFactory();
				XContentBuilder builder=XContentFactory.jsonBuilder()
				.startObject()
				.startObject("prompt")
				.startObject("properties")
//				name	varchar
				.startObject("type")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
				//code
				.startObject("code")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
				//hospital_name
				.startObject("hospital_name")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				type	text
//				"max_input_length": 10,
//				"payloads": true,
//				"analyzer": "ik",
//				"preserve_position_increments": false,
//				"type": "completion",
//				"preserve_separators": false
				
//				.startObject("name")
//				.field("type", "completion")
//				.field("preserve_separators",false)
//				.field("preserve_position_increments", false)
//				.field("analyzer", "ik")
//				.field("payloads", true)
//				.field("max_input_length", 10)
//				.endObject()
				
				.startObject("name")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik")
				.field("store", "yes")
				.endObject()
				
				.endObject()
				.endObject()
				.endObject();
				PutMappingRequest mapping = Requests.putMappingRequest("prompt").type("prompt").source(builder);
				client.admin().indices().putMapping(mapping).actionGet();
				client.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
    
	public  void createDoctorMapping()throws Exception{
		new XContentFactory();
			XContentBuilder builder=XContentFactory.jsonBuilder()
			.startObject()
			.startObject("doctor")
			.startObject("properties")
			//id
			.startObject("id")
			.field("type", "long")
			.field("store", "yes")
			.field("include_in_all", "true")
			.endObject()
			//code
			.startObject("source_id")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//department_id
			.startObject("department_id")
			.field("type", "long")
			.field("store", "yes")
			.endObject()
			//name
			.startObject("name")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//医院名称
			.startObject("hospital_name")
			.field("type", "string")
			.field("include_in_all", "true")
			.field("analyzer","ik")
			.field("store", "yes")
			.endObject()
			//科室名称
			.startObject("department_name")
			.field("type", "string")
			.field("include_in_all", "true")
			.field("analyzer","ik")
			.field("store", "yes")
			.endObject()
			//department_code
			.startObject("department_code")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//department_source_id
			.startObject("department_source_id")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//hospital_id
			.startObject("hospital_id")
			.field("type", "long")
			.field("store", "yes")
			.endObject()
			//hospital_code
			.startObject("hospital_code")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//disease_tag
			.startObject("disease_tag")
			.field("type", "string")
			.field("store", "yes")
			.field("include_in_all", "true")
			.field("analyzer","ik_smart")
			.endObject()
			//hospital_source_id
			.startObject("hospital_source_id")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//image_url
			.startObject("image_url")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//sex
			.startObject("sex")
			.field("type", "integer")
			.field("store", "yes")
			.endObject()
			//intro
			.startObject("intro")
			.field("type", "string")
			.field("include_in_all", "true")
			.field("analyzer","ik_smart")
			.field("store", "yes")
			.endObject()
			//identify_mark
			.startObject("attitude_score")
			.field("type", "double")
			.field("store", "yes")
			.endObject()
			//identify_mark
			.startObject("identify_mark")
			.field("type", "string")
			.field("include_in_all", "true")
			.field("analyzer","ik_smart")
			.field("store", "yes")
			.endObject()
			//attitude_score
			.startObject("attitude_score")
			.field("type", "double")
			.field("store", "yes")
			.endObject()
			//treatment_effect_score
			.startObject("treatment_effect_score")
			.field("type", "double")
			.field("store", "yes")
			.endObject()
			//recommend_score
			.startObject("recommend_score")
			.field("type", "double")
			.field("store", "yes")
			.endObject()
			//patient_comment_num
			.startObject("patient_comment_num")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//practice_title
			.startObject("practice_title")
			.field("type", "string")
			.field("include_in_all", "true")
			.field("analyzer","ik_smart")
			.field("store", "yes")
			.endObject()
			//spider_id
			.startObject("spider_id")
			.field("type", "integer")
			.field("store", "yes")
			.endObject()
			//doc_state
			.startObject("doc_state")
			.field("type", "integer")
			.field("store", "yes")
			.endObject()
			//source_type
			.startObject("source_type")
			.field("type", "long")
			.field("store", "yes")
			.endObject()
			//city
			.startObject("city")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//坐标
			.startObject("location")
			.field("type", "geo_point")
			.field("store", "yes")
			.endObject()
			//create_time
			.startObject("create_time")
			.field("type", "date")
			.field("format", "strict_date_optional_time||epoch_millis")
			.endObject()
			//create_by
			.startObject("create_by")
			.field("type", "long")
			.field("store", "yes")
			.endObject()
			//update_time
			.startObject("update_time")
			.field("type", "date")
			.field("format", "strict_date_optional_time||epoch_millis")
			.endObject()
			//update_by
			.startObject("update_by")
			.field("type", "long")
			.field("store", "yes")
			.endObject()
			
			.endObject()
			.endObject()
			.endObject();
			PutMappingRequest mapping = Requests.putMappingRequest("doctor").type("doctor").source(builder);
			client.admin().indices().putMapping(mapping).actionGet();
			client.close();
	}
    
	public void createDepartmentMapping() {
		try{
			new XContentFactory();
				XContentBuilder builder=XContentFactory.jsonBuilder()
				.startObject()
				.startObject("department")
				.startObject("properties")
				//id
				.startObject("id")
				.field("type", "long")
				.field("store", "yes")
				.field("include_in_all", "true")
				.endObject()
				//code
				//index: 'not_analyzed' 
				.startObject("code")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				source_id	varchar
				.startObject("source_id")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				name	varchar
				.startObject("name")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				parent_source	varchar
				.startObject("parent_source")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				parent_id	bigint
				.startObject("parent_id")
				.field("type", "long")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				hospital_source	varchar
				.startObject("hospital_source")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				hospital_id	bigint
				.startObject("hospital_id")
				.field("type", "long")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				hospital_code	varchar
				.startObject("hospital_code")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				source_type	smallint
				.startObject("source_type")
				.field("type", "long")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				type	smallint
				.startObject("type")
				.field("type", "integer")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				level	smallint
				.startObject("level")
				.field("type", "integer")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				title_descr	varchar
				.startObject("title_descr")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				content	text
				.startObject("content")
				.field("type", "string")
				.field("include_in_all", "true")
				.field("analyzer","ik_smart")
				.field("store", "yes")
				.endObject()
//				phone	varchar
				.startObject("phone")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				address	varchar
				.startObject("address")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				reserve_doctor_num	varchar
				.startObject("reserve_doctor_num")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				success_reserve_num	varchar
				.startObject("success_reserve_num")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
//				spider_ip	varchar
				.startObject("spider_ip")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
				//city
				.startObject("city")
				.field("type", "string")
				.field("index","not_analyzed")
				.field("store", "yes")
				.endObject()
				//坐标
				.startObject("location")
				.field("type", "geo_point")
				.field("store", "yes")
				.endObject()
				//create_time
				.startObject("create_time")
				.field("type", "date")
				.field("format", "strict_date_optional_time||epoch_millis")
				.endObject()
				//create_by
				.startObject("create_by")
				.field("type", "long")
				.field("store", "yes")
				.endObject()
				//update_time
				.startObject("update_time")
				.field("type", "date")
				.field("format", "strict_date_optional_time||epoch_millis")
				.endObject()
				//update_by
				.startObject("update_by")
				.field("type", "long")
				.field("store", "yes")
				.endObject()
				//is_deleted
				.startObject("is_deleted")
				.field("type", "long")
				.field("store", "yes")
				.endObject()
				
				.endObject()
				.endObject()
				.endObject();
				PutMappingRequest mapping = Requests.putMappingRequest("department").type("department").source(builder);
				client.admin().indices().putMapping(mapping).actionGet();
				client.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
    public  void createHospitalMapping()throws Exception{
		new XContentFactory();
			XContentBuilder builder=XContentFactory.jsonBuilder()
			.startObject()
			.startObject("hospital")
			.startObject("properties")
			//id
			.startObject("id")
			.field("type", "long")
			.field("store", "yes")
			.field("include_in_all", "true")
			.endObject()
			//code
			//index: 'not_analyzed' 
			.startObject("code")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//source_id
			.startObject("source_id")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//name
			.startObject("name")
			.field("type", "string")
			.field("include_in_all", "true")
			.field("analyzer","ik")
			.field("store", "yes")
			.endObject()
			
			//content
			.startObject("content")
			.field("type", "string")
			.field("include_in_all", "true")
			.field("analyzer","ik_smart")
			.field("store", "yes")
			.endObject()
			//history
			.startObject("history")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//characteristic_dept
			.startObject("characteristic_dept")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//team
			.startObject("team")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//honor
			.startObject("honor")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//address
			.startObject("address")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//province
			.startObject("province")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//city
			.startObject("city")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//area
			.startObject("area")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//level
			.startObject("level")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//phone
			.startObject("phone")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//image_url
			.startObject("image_url")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//characteristic_faculty
			.startObject("characteristic_faculty")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//strategy
			.startObject("strategy")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//spider_id
			.startObject("spider_id")
			.field("type", "long")
			.field("store", "yes")
			.endObject()
			//spider_ip
			.startObject("spider_ip")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//latitude
			.startObject("latitude")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//longitude
			.startObject("longitude")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//source_type
			.startObject("source_type")
			.field("type", "long")
			.field("store", "yes")
			.endObject()
			//hospital_type
			.startObject("hospital_type")
			.field("type", "long")
			.field("store", "yes")
			.endObject()
			//hospital_state
			.startObject("hospital_state")
			.field("type", "long")
			.field("store", "yes")
			.endObject()
			//score
			.startObject("score")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//hospital_type
			.startObject("hospital_type")
			.field("type", "long")
			.field("store", "yes")
			.endObject()
//			patient_comment_num	varchar
			.startObject("patient_comment_num")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
//			high_quality_doctor_num	varchar
			.startObject("high_quality_doctor_num")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
//			finished_service_num	varchar
			.startObject("finished_service_num")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
//			is_medical_insurance	varchar
			.startObject("is_medical_insurance")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
//			web_site	varchar
			.startObject("web_site")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
//			logo	varchar
			.startObject("logo")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
//			city_source_id	varchar
			.startObject("city_source_id")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
//			region_source_id	varchar
			.startObject("region_source_id")
			.field("type", "string")
			.field("index","not_analyzed")
			.field("store", "yes")
			.endObject()
			//坐标
			.startObject("location")
			.field("type", "geo_point")
			.field("store", "yes")
			.endObject()
			//create_time
			.startObject("create_time")
			.field("type", "date")
			.field("format", "strict_date_optional_time||epoch_millis")
			.endObject()
			//create_by
			.startObject("create_by")
			.field("type", "long")
			.field("store", "yes")
			.endObject()
			//update_time
			.startObject("update_time")
			.field("type", "date")
			.field("format", "strict_date_optional_time||epoch_millis")
			.endObject()
			//update_by
			.startObject("update_by")
			.field("type", "long")
			.field("store", "yes")
			.endObject()
			
			.endObject()
			.endObject()
			.endObject();
			PutMappingRequest mapping = Requests.putMappingRequest("hospital").type("hospital").source(builder);
			client.admin().indices().putMapping(mapping).actionGet();
			client.close();
	}
    /**
     * mhl 2016年11月21日
     */
    private void searchAll(String indexName) {
        SearchResponse response = client.prepareSearch(indexName).execute().actionGet();
        for (SearchHit hits : response.getHits().getHits()) {
            String sourceAsString = hits.sourceAsString();// 以字符串方式打印
            System.out.println(sourceAsString);
        }
        System.out.println("searchAll finished");
    }
    
    /**
     * mhl 2016年11月21日
     * @param indexName
     * @param type
     * @param fields
     */
    private void searchByFields(String indexName, String type, TreeMap<String,Object> fields) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (Entry<String, Object> temp : fields.entrySet()) {//must should mustnot 相当于 & || !
            boolQueryBuilder.must(QueryBuilders.queryStringQuery((String) temp.getValue()).queryName(temp.getKey())); //此处为querystring的方式
            //boolQueryBuilder.mustNot(QueryBuilders.queryStringQuery("东湖").queryName(temp.getKey()));  //查询name中包括武汉但不包括东湖的医院
            //boolQueryBuilder.must(QueryBuilders.termQuery(temp.getKey(), temp.getValue()));  //此处为termQuery的方式
        }
        
        SearchResponse response = client.prepareSearch(indexName).setTypes(type).setQuery(boolQueryBuilder)
                .addHighlightedField("intro")
                .setHighlighterPreTags("<span style=\"color:red\">")
                .setHighlighterPostTags("</span>")
                .setFrom(0).setSize(10).execute().actionGet();
        SearchHits hits = response.getHits();
        if(hits != null) {
            for (SearchHit searchHit : hits) {
                String result = searchHit.sourceAsString();
                System.out.println(result);
            }
        }
        System.out.println("searchByFields finished");
    }
    

    /**
     * mhl 2016年11月21日
     * @param indexName
     * @param type
     * @param field
     */
    private void sortByField(String indexName, String type, String field) {
        SearchResponse response = client.prepareSearch(indexName).setTypes(type).setQuery(QueryBuilders.matchAllQuery())
                                        .addSort(field,SortOrder.DESC).execute().actionGet();
        SearchHits hits = response.getHits();
        if(hits != null) {
            for (SearchHit searchHit : hits) {
                String result = searchHit.sourceAsString();
                System.out.println(result);
            }
        }
        System.out.println("sortByField finished");
    }
    
    /**
     * mhl 2016年11月21日
     * @param indexName
     * @param field
     * @param input
     */
    private void suggestionByField(String indexName, String field, String input) {
        SuggestResponse response = client.prepareSuggest(indexName)
                                         .addSuggestion(new CompletionSuggestionBuilder(field).field(field).text(input).size(10))
                                         .execute().actionGet();
        
        Suggest hits = response.getSuggest();
        System.out.println(hits);
        System.out.println("suggestionByField finished");
    }
    
    /**
     * mhl 2016年11月21日
     */
    private void mutilIndexSearch() {
        MultiSearchResponse response = client.prepareMultiSearch()
                                      .add(client.prepareSearch("product").setQuery(QueryBuilders.queryStringQuery("china").field("address")))
                                      .add(client.prepareSearch("cc").setQuery(QueryBuilders.queryStringQuery("武汉").field("name")))
                                      .execute().actionGet();
        if(response.getResponses() != null) {
            for (MultiSearchResponse.Item item : response.getResponses()) {
                SearchResponse temp = item.getResponse();
                if(temp.getHits() != null) {
                    for (SearchHit searchHit : temp.getHits()) {
                        String result = searchHit.sourceAsString();
                        System.out.println(result);
                    }
                }
            }
        }
        
        System.out.println("mutilIndexSearch finished");
    }
    
    /**
     * mhl 2016年11月21日
     * @param indexName
     * @param type
     * @param field
     * @param input
     */
    private void aggregationSearch(String indexName, String type, String field, String input) {
        SearchResponse response = client.prepareSearch(indexName).setTypes(type).setQuery(QueryBuilders.matchAllQuery())
                                  .addAggregation(AggregationBuilders.terms(input).field(field)).execute().actionGet();
        SearchHits hits = response.getHits();
        if(hits != null) {
            for (SearchHit searchHit : hits) {
                String result = searchHit.sourceAsString();
                System.out.println(result);
            }
        }
        Terms terms = response.getAggregations().get(input);
        if (terms != null) {
            for (Terms.Bucket entry : terms.getBuckets()) {
                System.out.println(entry.getKey() + "   " + entry.getDocCount());
            }
        }
        System.out.println("aggregationSearch finished");
    }
    
    
    /**
     * mhl 2016年11月21日
     */
    private void geoSearch() {
        //1. 普通搜索结果
        TreeMap<String,Object> fields = new TreeMap<String,Object>();
        fields.put("name", "医院");
        searchByFields("hospital1","hospital1",fields);
        
        //2. 通过location查离自己最近的医院
        SearchResponse response = client.prepareSearch("hospital1").setTypes("hospital1")
                                        .addSort(SortBuilders.geoDistanceSort("location")
                                                 .unit(DistanceUnit.KILOMETERS)
                                                 .order(SortOrder.ASC)
                                                 .point(30.5878611076, 114.2711108938))
                                        .execute().actionGet(); 
        
        if(response.getHits() != null) {
            for (SearchHit searchHit : response.getHits()) {
                Map<String, Object> sourceAsMap = searchHit.getSource();
                BigDecimal geoDis=new BigDecimal(Double.valueOf(String.valueOf(searchHit.getSortValues()[0])));  
                sourceAsMap.put("geoDistance", geoDis.setScale(2, BigDecimal.ROUND_HALF_DOWN));
                System.out.println("name :" + sourceAsMap.get("name") + "  location:" + sourceAsMap.get("location") 
                                   + " 距离：" + sourceAsMap.get("geoDistance") + DistanceUnit.KILOMETERS.toString());
            }
        }
        System.out.println("查最近的医院 finished");
        
        //3. 查几公里以内的医院
        response = client.prepareSearch("hospital1").setTypes("hospital1")
                                        .setQuery(QueryBuilders.geoDistanceRangeQuery("location")
                                                  .point(30.5878611076, 114.2711108938)
                                                  .from("0km")
                                                  .to("1km")
                                                  .geoDistance(GeoDistance.ARC))
                                        .addSort(SortBuilders.geoDistanceSort("location")
                                                 .unit(DistanceUnit.KILOMETERS)
                                                 .order(SortOrder.ASC)
                                                 .point(30.5878611076, 114.2711108938))
                                        .execute().actionGet(); 
                        
        if(response.getHits() != null) {
            for (SearchHit searchHit : response.getHits()) {
            Map<String, Object> sourceAsMap = searchHit.getSource();
            BigDecimal geoDis=new BigDecimal(Double.valueOf(String.valueOf(searchHit.getSortValues()[0])));  
            sourceAsMap.put("geoDistance", geoDis.setScale(2, BigDecimal.ROUND_HALF_DOWN));
            System.out.println("name :" + sourceAsMap.get("name") + "  location:" + sourceAsMap.get("location") 
                       + " 距离：" + sourceAsMap.get("geoDistance") + DistanceUnit.KILOMETERS.toString());
            }
        }
        System.out.println("查2km以内的医院 finished");
        
        System.out.println("geoSearch finished");
    }
    
    /**
     * mhl 2016年11月22日
     * @param indexName
     * @param alias
     */
    private void createAliases(String indexName, String alias) {
        IndicesAliasesResponse response = client.admin().indices().prepareAliases().addAlias(indexName, alias).execute().actionGet();
        if(isAliasExists(alias)) {
            searchAll(alias);
            System.out.println("别名搜索成功");
        }else {
            System.out.println("创建别名失败");
        }
        
        System.out.println("createAliases finished");
    }
    
    /**
     * mhl 2016年11月22日
     * @param string
     */
    private boolean isAliasExists(String string) {
        boolean isAliasExists = false;
        AliasesExistRequestBuilder builder = client.admin().indices().prepareAliasesExist(string);
        AliasesExistResponse response = builder.execute().actionGet();
        if(response.isExists()) {
            System.out.println("别名存在");
            isAliasExists = true;
        }else  {
            System.out.println("别名不存在");
            isAliasExists = false;
        }
        
        System.out.println("isAliasExists finished");
        return isAliasExists;
    }
    
    /**
     * mhl 2016年11月22日
     * @param oldIndexName
     * @param newIndexName
     * @param alias
     */
    private void changeAliases(String oldIndexName, String newIndexName, String alias) {
        if(isAliasExists(alias)) {
            //删除别名
            client.admin().indices().prepareAliases().removeAlias(oldIndexName, alias).execute().actionGet();
            IndicesAliasesResponse response = client.admin().indices().prepareAliases().addAlias(newIndexName, alias).execute().actionGet();
            if(isAliasExists(alias)) {
                System.out.println("更改别名成功");
                searchAll(alias);
            }else {
                System.out.println("更改别名失败");
            }
        }
        
        System.out.println("changeAliases finished");
    }
    
    /**
     * mhl 2016年11月24日
     */
    private void autoCompletion() {
    	
        //1. 首字自动补全
        SuggestResponse response = client.prepareSuggest("hospital2")
                                         .addSuggestion(SuggestBuilders.completionSuggestion("nameSuggest").field("nameSuggest").text("上海").size(10))
                                         .execute().actionGet();
        
        
        if(response.getSuggest() != null) {
            List<? extends org.elasticsearch.search.suggest.Suggest.Suggestion.Entry<? extends Option>> list = response.getSuggest().getSuggestion("nameSuggest").getEntries();
            if(list != null) {
                for (org.elasticsearch.search.suggest.Suggest.Suggestion.Entry<? extends Option> entry : list) {
                    for (Option option : entry) {
                    	
                        System.out.println(option.getText());
                    }
                }
            }
        }
        
        System.out.println("autoCompletion finished");
    }
}
