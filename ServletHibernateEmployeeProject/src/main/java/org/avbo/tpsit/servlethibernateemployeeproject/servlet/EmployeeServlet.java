/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package org.avbo.tpsit.servlethibernateemployeeproject.servlet;

import com.google.gson.Gson;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.MediaType;
import java.io.File;
import java.util.HashSet;
import org.avbo.tpsit.servlethibernateemployeeproject.Employee;
import org.avbo.tpsit.servlethibernateemployeeproject.Project;
import org.avbo.tpsit.servlethibernateemployeeproject.hibernate.EmployeeDAO;
import org.avbo.tpsit.servlethibernateemployeeproject.hibernate.HibernateUtil;

/**
 *
 * @author Andrea Iannì
 */
public class EmployeeServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
	super.init(config); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
	
	// Ottiene il context della servlet
	ServletContext context = config.getServletContext();
	// Ottiene il percorso in cui è stato spostato il file
	File f = new File(context.getRealPath("hibernate_many_to_many.db"));
	// Aggiorna il percorso del database prima che il file venga aperto
	HibernateUtil.SetFilePath(f.getPath());
    }
    
    
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	//Crea il dao per leggere dal DB
	var dao = new EmployeeDAO();
	//Ottiene la lista di tutti gli impiegati
	var employees = dao.getAllEmployees();
	
	//Si assicura che non ci sia riferimenti circolari per non fare impallare GSON
	//  infatti il Project.employees ha un riferimento verso Employee.projects e viceversa.
	//  Questo genera un loop infinito in GSON che termina con un'eccezione
	for (Employee employee : employees) {
	    for (Project project : employee.getProjects()) {
		//Elimina tutti i riferimenti verso Employee
		project.setEmployees(new HashSet<>());
	    }
	}
	
	Gson gson = new Gson();
	//Converte la lista in una stringa JSON
	var jsonString = gson.toJson(employees);
	
	//Indica il tipo di Output al client
	response.setContentType(MediaType.APPLICATION_JSON);
	//Indica che è andato tutto bene
	response.setStatus(200);
	
	try (PrintWriter out = response.getWriter()) {
	    //Inserisce la string a JSON nell'output
	    out.println(jsonString);
	}
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>

}
