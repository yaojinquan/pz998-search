package com.pz998.search.model;

import java.util.ArrayList;
import java.util.List;

public class SearchConfig {
	private String clusterName;
	private boolean sniff;
	private boolean ignoreClusterName;
	private String pingTimeout;
	private String nodesSamplerInterval;
	private int port;
	private List<String> ipList = new ArrayList<String>();
	public String getClusterName() {
		return clusterName;
	}
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}
	public boolean isSniff() {
		return sniff;
	}
	public void setSniff(boolean sniff) {
		this.sniff = sniff;
	}
	public boolean isIgnoreClusterName() {
		return ignoreClusterName;
	}
	public void setIgnoreClusterName(boolean ignoreClusterName) {
		this.ignoreClusterName = ignoreClusterName;
	}
	public String getPingTimeout() {
		return pingTimeout;
	}
	public void setPingTimeout(String pingTimeout) {
		this.pingTimeout = pingTimeout;
	}
	public String getNodesSamplerInterval() {
		return nodesSamplerInterval;
	}
	public void setNodesSamplerInterval(String nodesSamplerInterval) {
		this.nodesSamplerInterval = nodesSamplerInterval;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public List<String> getIpList() {
		return ipList;
	}
	public void setIpList(List<String> ipList) {
		this.ipList = ipList;
	}
	
}
