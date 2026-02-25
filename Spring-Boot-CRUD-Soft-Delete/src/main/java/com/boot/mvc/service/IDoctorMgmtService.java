package com.boot.mvc.service;

import java.util.List;

import com.boot.mvc.dto.DoctorDto;


public interface IDoctorMgmtService {
	
	 public String saveDoctor(DoctorDto docdto);
	    public  List<DoctorDto>  showAllDoctors();
	    public  DoctorDto  showDoctorById(int id);
	    public   String    editDoctor(DoctorDto  docdto);
	    public   String   deleteDoctorById(int id);
}
