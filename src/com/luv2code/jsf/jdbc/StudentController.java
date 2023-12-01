package com.luv2code.jsf.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
@SessionScoped
public class StudentController {

	private List<Student> students;
	private StudentDbUtil studentDbUtil;
	private Logger logger = Logger.getLogger(getClass().getName());
	private boolean show = true;
	private boolean showImagen = true;
	private String factura;
	private boolean showPDF;
	public StudentController() throws Exception {
		show = true;
		showImagen = true;
		factura = "/image/factura_1.jpg";
		students = new ArrayList<>();
		
		studentDbUtil = StudentDbUtil.getInstance();
	}
	
	
	
	public boolean isShowPDF() {
		return showPDF;
	}



	public void setShowPDF(boolean showPDF) {
		this.showPDF = showPDF;
	}



	public String getFactura() {
		return factura;
	}

	public void setFactura(String factura) {
		this.factura = factura;
	}

	public boolean isShowImagen() {
		return showImagen;
	}

	public void setShowImagen(boolean showImagen) {
		this.showImagen = showImagen;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void loadStudents() {

		logger.info("Loading students");
		
		students.clear();

		try {
			
			// get all students from database
			students = studentDbUtil.getStudents();
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading students", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
		}
	}
	
	/**
	 * Checks if is show media.
	 *
	 * @return true, if is show
	 */
	public boolean isShow() {
		return show;
	}


	/**
	 * Sets the show media.
	 *
	 * @param show the new show media
	 */
	public void setShow(boolean show) {
		this.show = show;
	}
	
	public StreamedContent getTempPdfFile() throws IOException {
	     File testPdfFile = Paths.get("D:\\Facturas.pdf").toFile();
	     FileInputStream fileInputStream = new FileInputStream(testPdfFile);
	     return new DefaultStreamedContent(fileInputStream, "application/pdf",
	                "Facturas");
	}
		
	public String addStudent(Student theStudent) {

		logger.info("Adding student: " + theStudent);

		try {
			
			// add student to the database
			studentDbUtil.addStudent(theStudent);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error adding students", exc);
			
			// add error message for JSF page
			addErrorMessage(exc);

			return null;
		}
		
		return "list-students?faces-redirect=true";
	}

	public String loadStudent(int studentId) {
		
		logger.info("loading student: " + studentId);
		
		try {
			// get student from database
			Student theStudent = studentDbUtil.getStudent(studentId);
			
			// put in the request attribute ... so we can use it on the form page
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();		

			Map<String, Object> requestMap = externalContext.getRequestMap();
			requestMap.put("student", theStudent);	
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error loading student id:" + studentId, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
				
		return "update-student-form.xhtml";
	}	
	
	public String updateStudent(Student theStudent) {

		logger.info("updating student: " + theStudent);
		
		try {
			
			// update student in the database
			studentDbUtil.updateStudent(theStudent);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error updating student: " + theStudent, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "list-students?faces-redirect=true";		
	}
	
	public String deleteStudent(int studentId) {

		logger.info("Deleting student id: " + studentId);
		
		try {

			// delete the student from the database
			studentDbUtil.deleteStudent(studentId);
			
		} catch (Exception exc) {
			// send this to server logs
			logger.log(Level.SEVERE, "Error deleting student id: " + studentId, exc);
			
			// add error message for JSF page
			addErrorMessage(exc);
			
			return null;
		}
		
		return "list-students";	
	}	
	
	private void addErrorMessage(Exception exc) {
		FacesMessage message = new FacesMessage("Error: " + exc.getMessage());
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
}
