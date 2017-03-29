package com.pz998.search.model.entity;

public class BdDepartment{
	private Long id;
	private String code;
	private String sourceId;
	private String parentSource;
	private String hospitalSource;
	private String name;
	private String titleDescr;
	private Long parentId;
	private Long hospitalId;
	private String hospitalCode;
	private String sourceType;
	private Integer type;
	private Integer level;
	private String content;
	private String phone;
	private String address;
	private String reserveDoctorNum;
	private String successReserveNum;
	private String spiderIp;
	
	public static final Integer LEVEL_FIRST = 1;
	public static final Integer LEVEL_SECOND = 2;
	
	public static final Integer TYPE_HOSPITAL = 1;
	public static final Integer TYPE_PLATFORM =2;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSourceId() {
		return sourceId;
	}
	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}
	public String getParentSource() {
		return parentSource;
	}
	public void setParentSource(String parentSource) {
		this.parentSource = parentSource;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getHospitalId() {
		return hospitalId;
	}
	public void setHospitalId(Long hospitalId) {
		this.hospitalId = hospitalId;
	}
	
	public String getHospitalCode() {
		return hospitalCode;
	}
	public void setHospitalCode(String hospitalCode) {
		this.hospitalCode = hospitalCode;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getReserveDoctorNum() {
		return reserveDoctorNum;
	}
	public void setReserveDoctorNum(String reserveDoctorNum) {
		this.reserveDoctorNum = reserveDoctorNum;
	}
	public String getSuccessReserveNum() {
		return successReserveNum;
	}
	public void setSuccessReserveNum(String successReserveNum) {
		this.successReserveNum = successReserveNum;
	}
	public String getHospitalSource() {
		return hospitalSource;
	}
	public void setHospitalSource(String hospitalSource) {
		this.hospitalSource = hospitalSource;
	}
	public String getTitleDescr() {
		return titleDescr;
	}
	public void setTitleDescr(String titleDescr) {
		this.titleDescr = titleDescr;
	}
	public String getSpiderIp() {
		return spiderIp;
	}
	public void setSpiderIp(String spiderIp) {
		this.spiderIp = spiderIp;
	}
	
}
