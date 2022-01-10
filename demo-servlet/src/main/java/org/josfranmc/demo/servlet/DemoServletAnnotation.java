package org.josfranmc.demo.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/demoAnnotation")
public class DemoServletAnnotation extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public DemoServletAnnotation() {
		super();
	}
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	response.setContentType("text/html; charset=UTF-8");
    	PrintWriter pw = response.getWriter();
    	
    	pw.println ("<!DOCTYPE html>");
    	pw.println ("<html lang=\"es\">");
    	pw.println ("<html>");
    	pw.println ("<head>");
    	pw.println ("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/formato.css\" />");
    	pw.println ("</head>");
    	pw.println ("<body>");
        pw.println("Respuesta del servlet registrado con anotaciones.<br>");
        pw.println("<a href=\"javascript: history.go(-1)\">Volver</a>");    	
    	pw.println ("</body>");
    	pw.println ("</html>");        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String mensaje = request.getParameter("mensaje");
    	
    	response.setContentType("text/html; charset=UTF-8");
    	PrintWriter pw = response.getWriter();
    	
    	pw.println ("<!DOCTYPE html>");
    	pw.println ("<html lang=\"es\">");
    	pw.println ("<html>");
    	pw.println ("<head>");
    	pw.println ("<link rel=\"stylesheet\" type=\"text/css\" href=\"css/formato.css\" />");
    	pw.println ("</head>");
    	pw.println ("<body>");
        pw.println("Respuesta del servlet registrado en fichero web.xml<br>");
        pw.println("Mensaje recibido: " + mensaje  + "<br>");
        pw.println("<a href=\"javascript: history.go(-1)\">Volver</a>");    	
    	pw.println ("</body>");
    	pw.println ("</html>");
    }
}
