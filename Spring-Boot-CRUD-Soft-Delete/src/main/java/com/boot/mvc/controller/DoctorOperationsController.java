package com.boot.mvc.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.boot.mvc.dto.DoctorDto;
import com.boot.mvc.service.IDoctorMgmtService;

import jakarta.servlet.http.HttpSession;

@Controller  // Marks this class as Spring MVC Controller
public class DoctorOperationsController {

	// Injecting Service layer object (Dependency Injection)
	@Autowired
	private IDoctorMgmtService docService;

	// ========================= HOME PAGE =========================
	@GetMapping("/")  // Handles request for root URL
	public String showHome() {
		// Returns Logical View Name (LVN)
		return "welcome";
	}

	// ========================= SHOW REPORT =========================
	@GetMapping("/report")
	public String showReport(Map<String, Object> map) {

		// Fetch all doctors from service layer
		List<DoctorDto> listDto = docService.showAllDoctors();

		// Add data to model (to send to view)
		map.put("listDto", listDto);

		return "show_report";
	}

	// ========================= ADD FORM DISPLAY =========================
	@GetMapping("/add")
	public String showAddDoctorFormPage(@ModelAttribute("docDto") DoctorDto docdto) {

		// @ModelAttribute creates empty DoctorDto object
		// This object is used to bind form data
		return "doctor_register_form";
	}

	// ===============================================================
	// VERSION 1: Using Map (Model) with redirect  (NOT RECOMMENDED)
	// ===============================================================

	/*
	@PostMapping("/add")
	public String saveDoctor(@ModelAttribute("docDto") DoctorDto docdto,
	                         Map<String,Object> map) {

		// Save doctor
		String result = docService.saveDoctor(docdto);

		// Add result to model
		map.put("resultMsg", result);

		// PROBLEM:
		// When we use redirect, model data will NOT be available
		// because redirect creates a new request

		return "redirect:report";
	}
	*/

	// ===============================================================
	// VERSION 2: Using RedirectAttributes (BEST PRACTICE)
	// ===============================================================

	
	@PostMapping("/add")
	public String saveDoctor(@ModelAttribute("docDto") DoctorDto docdto,
	                         RedirectAttributes attrs) {

		// Save doctor details
		String result = docService.saveDoctor(docdto);

		// Flash attribute:
		// Available only for next request after redirect
		attrs.addFlashAttribute("resultMsg", result);

		// Follows PRG Pattern (Post-Redirect-Get)
		return "redirect:report";
	}
	

	// ===============================================================
	// VERSION 3: Using HttpSession (WORKS but NOT IDEAL)
	// ===============================================================

	/*
	@PostMapping("/add")
	public String saveDoctor(@ModelAttribute("docDto") DoctorDto docdto,
	                         HttpSession ses) {

		// Save doctor
		String result = docService.saveDoctor(docdto);

		// Store message in session
		// Session data remains until removed manually
		ses.setAttribute("resultMsg", result);

		// Redirect to report page
		return "redirect:report";
	}
	*/
	
	// ========================= EDIT FORM DISPLAY =========================
	@GetMapping("/edit")
	public String showEditDoctorFormPage(@ModelAttribute("docDto") DoctorDto docdto,
	                                     @RequestParam Integer no) {

		// Fetch doctor data by ID
		DoctorDto docdto1 = docService.showDoctorById(no);

		// Copy properties to form object
		BeanUtils.copyProperties(docdto1, docdto);

		return "doctor_edit_form";
	}

	// ========================= EDIT SUBMIT =========================
	@PostMapping("/edit")
	public String editDoctor(@ModelAttribute("docDto") DoctorDto docdto,
	                         RedirectAttributes attrs) {

		// Update doctor details
		String msg = docService.editDoctor(docdto);

		// Add flash message
		attrs.addFlashAttribute("resultMsg", msg);

		return "redirect:report";
	}

	// ===============================================================
	// DELETE VERSION 1: Using RedirectAttributes (BEST PRACTICE) 
	// ===============================================================

	
	@GetMapping("/delete")
	public String deleteDoctor(@RequestParam("no") int no,
	                           RedirectAttributes attrs) {

		// Delete doctor by ID
		String msg = docService.deleteDoctorById(no);

		// Flash attribute survives redirect
		attrs.addFlashAttribute("resultMsg", msg);

		// Redirect (new request)
		return "redirect:report";
	}
	

	// ===============================================================
	// DELETE VERSION 2: Using Forward
	// ===============================================================

	/*
	@GetMapping("/delete")
	public String deleteDoctor(@RequestParam("no") int no,
	                           Map<String, Object> map) {

		// Delete doctor
		String msg = docService.deleteDoctorById(no);

		// Add message to model
		map.put("resultMsg", msg);

		// forward:
		// Internal request forwarding
		// URL does NOT change
		// Same request object is used
		return "forward:report";
	}
	*/
}
