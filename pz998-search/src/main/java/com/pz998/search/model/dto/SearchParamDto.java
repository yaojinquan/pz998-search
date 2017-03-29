package com.pz998.search.model.dto;

public class SearchParamDto {
	
	private Integer type;
	private String keyword;
	private Integer pageNo;
	private Integer pageSize;
	private Integer from;
	private String city;
	private Double latitude;
	private Double longitude;
	
	public static final Integer HOSPITAL_TYPE = 1; 
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getFrom() {
		return (this.pageNo-1)*this.pageSize;
	}
	public void setFrom(Integer from) {
		this.from = from;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	
}
