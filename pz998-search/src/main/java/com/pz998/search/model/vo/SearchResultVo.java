package com.pz998.search.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.pz998.search.model.entity.BdBodySymptom;
import com.pz998.search.model.entity.BdDepartment;
import com.pz998.search.model.entity.BdDisease;
import com.pz998.search.model.entity.BdDoctor;


public class SearchResultVo {
	private List<BdDisease> diseaseList = new ArrayList<BdDisease>();
	private List<BdDoctor> doctorList = new ArrayList<BdDoctor>();
	private List<BdBodySymptom> symptomList = new ArrayList<BdBodySymptom>();
	private List<HospitalSearchVo> hospitalList = new ArrayList<HospitalSearchVo>();
	private List<BdDepartment> departmentList = new ArrayList<BdDepartment>();
	
	private Long diseaseTotal = 0L;
	private Long doctorTotal = 0L;
	private Long symptomTotal = 0L;
	private Long hospitalTotal = 0L;
	private Long departmentTotal = 0L;
	
	public List<BdDisease> getDiseaseList() {
		return diseaseList;
	}
	public void setDiseaseList(List<BdDisease> diseaseList) {
		this.diseaseList = diseaseList;
	}
	public List<BdDoctor> getDoctorList() {
		return doctorList;
	}
	public void setDoctorList(List<BdDoctor> doctorList) {
		this.doctorList = doctorList;
	}
	public List<BdBodySymptom> getSymptomList() {
		return symptomList;
	}
	public void setSymptomList(List<BdBodySymptom> symptomList) {
		this.symptomList = symptomList;
	}
	public Long getDiseaseTotal() {
		return diseaseTotal;
	}
	public void setDiseaseTotal(Long diseaseTotal) {
		this.diseaseTotal = diseaseTotal;
	}
	public Long getDoctorTotal() {
		return doctorTotal;
	}
	public void setDoctorTotal(Long doctorTotal) {
		this.doctorTotal = doctorTotal;
	}
	public Long getSymptomTotal() {
		return symptomTotal;
	}
	public void setSymptomTotal(Long symptomTotal) {
		this.symptomTotal = symptomTotal;
	}
	
	public List<HospitalSearchVo> getHospitalList() {
		return hospitalList;
	}
	public void setHospitalList(List<HospitalSearchVo> hospitalList) {
		this.hospitalList = hospitalList;
	}
	public List<BdDepartment> getDepartmentList() {
		return departmentList;
	}
	public void setDepartmentList(List<BdDepartment> departmentList) {
		this.departmentList = departmentList;
	}
	public Long getHospitalTotal() {
		return hospitalTotal;
	}
	public void setHospitalTotal(Long hospitalTotal) {
		this.hospitalTotal = hospitalTotal;
	}
	public Long getDepartmentTotal() {
		return departmentTotal;
	}
	public void setDepartmentTotal(Long departmentTotal) {
		this.departmentTotal = departmentTotal;
	}

	
	
}
