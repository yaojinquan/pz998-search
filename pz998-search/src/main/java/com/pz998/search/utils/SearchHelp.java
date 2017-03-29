package com.pz998.search.utils;


import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.util.CollectionUtils;

import com.pz998.search.model.SearchConfig;

public class SearchHelp {
//	cluster.name=es-cluster
//			client.transport.sniff=true
//			client.transport.ignore_cluster_name=false
//			client.transport.ping_timeout=5s
//			client.transport.nodes_sampler_interval=5s
//			ip=10.3.32.81
	
	public static final String CLUSTER_NAME = "cluster.name";
	public static final String CLIENT_TRANSPORT_SNIFF = "client.transport.sniff";
	public static final String CLIENT_TRANSPORT_IGNORE_CLUSTER_NAME = "client.transport.ignore_cluster_name";
	public static final String CLIENT_TRANSPORT_PING_TIMEOUT = "client.transport.ping_timeout";
	public static final String CLIENT_TRANSPORT_NODES_SAMPLER_INTERVAL = "client.transport.nodes_sampler_interval";
	public static final String IP = "ip";
	
	private static TransportClient client = null;
	
	public static Client getSearchClient() throws Exception {
		if(client!=null){
			return client;
		}
		
		SearchConfig searchConfig = config();
		
		Settings setting = Settings.settingsBuilder()
		//集群名称		
		.put("cluster.name", searchConfig.getClusterName())
		.put("client.transport.sniff", searchConfig.isSniff())
		.put("client.transport.ignore_cluster_name", searchConfig.isIgnoreClusterName())
		.put("client.transport.ping_timeout", searchConfig.getPingTimeout())
		.put("client.transport.nodes_sampler_interval", searchConfig.getNodesSamplerInterval())
		.build();
		
		client = TransportClient.builder().settings(setting).build();
		
		List<String> ipList =  searchConfig.getIpList();
		if(CollectionUtils.isEmpty(ipList)){
			throw new Exception("缺少ip配置");
		}
		
		for(String ip:ipList){
			InetSocketTransportAddress address = new InetSocketTransportAddress(new InetSocketAddress(ip, 9300));
			((TransportClient) client).addTransportAddress(address);
		}
		
		
		return client;
	}
	
	public static SearchConfig config(){
		SearchConfig searchConfig = new SearchConfig();
		try {
			Configuration  config = new PropertiesConfiguration("conf.properties");
			String clusterName = config.getString(CLUSTER_NAME);
			boolean clientTransportSniff = config.getBoolean(CLIENT_TRANSPORT_SNIFF);
			boolean ignoreClusterName = config.getBoolean(CLIENT_TRANSPORT_IGNORE_CLUSTER_NAME);
			String timeout = config.getString(CLIENT_TRANSPORT_PING_TIMEOUT);
			String interval=config.getString(CLIENT_TRANSPORT_NODES_SAMPLER_INTERVAL);
			String ips = config.getString(IP);
			List<String> ipList = new ArrayList<String>();
			if(StringUtils.isNotEmpty(ips)){
				String[] ipArray = ips.split(",");
				for(String ip:ipArray){
					ipList.add(ip);
				}
			}
			
			searchConfig.setClusterName(clusterName);
			searchConfig.setSniff(clientTransportSniff);
			searchConfig.setIgnoreClusterName(ignoreClusterName);
			searchConfig.setPingTimeout(timeout);
			searchConfig.setNodesSamplerInterval(interval);
			searchConfig.setIpList(ipList);
			
		} catch (ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return searchConfig;
	}

}
