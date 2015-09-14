/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import jufroweb.JufroCMSConnection;
import miaplicacionweb.MiConfiguracion;

/**
 *
 * @author Felipe
 */

//No tocar
@WebServlet(name = "Estilo", urlPatterns = {"/Estilo"})
//
public class Estilo extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Estilo</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Estilo at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally {
            out.close();
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
        //processRequest(request, response);
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
        PrintWriter out = response.getWriter();
        //processRequest(request, response);
        try{
        MiConfiguracion miweb = new MiConfiguracion();
    int layoutSelected = Integer.parseInt(request.getParameter("opsel"));
    int layoutInUse;
    //String layoutSelectedDel = null;
    String relPath = "C:\\Users\\MULTIVAC\\Documents\\NetBeansProjects\\GithubProject\\JufroCMS\\web\\";
   
    String layoutSelectedDel = request.getParameter("action");
    
    
    JufroCMSConnection conec = new JufroCMSConnection();
    Statement stm = conec.createStatement();

    if(layoutSelectedDel != null && layoutSelectedDel.equalsIgnoreCase("Borrar Layout")){
        //el siguiente if debe verificar si es que se va a borrar el mismo layout que esta aplicado
        stm.execute("SELECT SELSTYLE FROM CONFIG");
        ResultSet result = stm.getResultSet();
        result.next();
        layoutInUse = result.getInt(1);
        if(layoutSelected == layoutInUse){
            stm.execute("UPDATE CONFIG SET SELSTYLE = 0");
            stm.execute("DELETE FROM STYLES WHERE ID = "+layoutSelected);
            miweb.setLayout(relPath+"layouts\\layoutDefault.html");
            miweb.setFooter("Zhurdazo Productions Co.<br>+562345321, Techmuco EcoSoftware Space<br>Powered by &copy; JUFRO");
            HttpSession session = null;
            miweb.setContent("<b>LAYOUT ELIMINADO</b>",request,session);
            miweb.setHeader("Can&iacute;bales del Tiempo, RAD to your life");
            out.print(miweb.getWebPage()); 
        }else{
            stm.execute("DELETE FROM STYLES WHERE ID = "+layoutSelected);
            miweb.setFooter("Zhurdazo Productions Co.<br>+562345321, Techmuco EcoSoftware Space<br>Powered by &copy; JUFRO");
            HttpSession session = null;
            miweb.setContent("<b>LAYOUT ELIMINADO</b>",request,session);
            miweb.setHeader("Can&iacute;bales del Tiempo, RAD to your life");
            out.print(miweb.getWebPage());
        }
                    
    }else{            
        stm.execute("UPDATE CONFIG SET SELSTYLE = "+layoutSelected);
        stm.execute("SELECT NAME FROM STYLES WHERE ID="+layoutSelected);
        ResultSet result = stm.getResultSet();
        result.next();
        String layoutName = result.getString("NAME");
        if(layoutSelected != -1){
            miweb.setLayout(relPath+"layouts\\"+layoutName);
        }else{  
            miweb.setLayout(relPath+"layouts\\layoutDefault.html");
        }
        miweb.setFooter("Zhurdazo Productions Co.<br>+562345321, Techmuco EcoSoftware Space<br>Powered by &copy; JUFRO");
            HttpSession session = null;
        miweb.setContent("<b>LAYOUT MODIFICADO</b>",request,session);
        miweb.setHeader("Can&iacute;bales del Tiempo, RAD to your life");

        out.print(miweb.getWebPage());    
    }
        }
        catch(SQLException e){
            
    }   catch (ClassNotFoundException ex) {
            Logger.getLogger(Estilo.class.getName()).log(Level.SEVERE, null, ex);
        }
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
