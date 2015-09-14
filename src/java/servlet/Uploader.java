/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author MULTIVAC
 */
@WebServlet(name = "Uploader", urlPatterns = {"/Uploader"})
public class Uploader extends HttpServlet {

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
            out.println("<title>Servlet Uploader</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Uploader at " + request.getContextPath() + "</h1>");
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
        PrintWriter out = response.getWriter();
        //processRequest(request, response);
        try{
            String filePathLayout = "C:\\Users\\MULTIVAC\\Documents\\NetBeansProjects\\GithubProjects\\JufroCMS\\web\\layouts\\";
            String filePathImages = "C:\\Users\\MULTIVAC\\Documents\\NetBeansProjects\\GithubProjects\\JufroCMS\\web\\images\\";
            String fileAuxLay = null;
            String fileAuxImg = null;
            String fileName = null;
            String fileExtension = null;

            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            JufroCMSConnection conec = new JufroCMSConnection();
            Statement stmnt = conec.createStatement();

            //Captura los archivos desde el request
            List<FileItem> fileItems = upload.parseRequest(request);
            for(FileItem item : fileItems) {
                if(!item.isFormField()) {
                    fileName = item.getName();
                    int index = fileName.lastIndexOf('.');
                    fileExtension = (fileName.substring(index + 1)).toLowerCase();

                    if( fileExtension.equals("html") ){
                        fileAuxLay = filePathLayout + item.getName();
                        File fileH = new File(filePathLayout + item.getName());
                        item.write(fileH);
                    }else if ( fileExtension.equals("jpg") || fileExtension.equals("png") || fileExtension.equals("bmp") ){
                        fileAuxImg = filePathImages + item.getName();
                        File fileI = new File(filePathImages + item.getName());
                        item.write(fileI);
                    }

                } else {
                    System.out.println("NO file!");
                }
            }
            stmnt.execute("INSERT INTO STYLES ( NAME, LAYOUTPATH, IMAGEPATH) VALUES ( '"+fileName+"','"+fileAuxLay+"','"+fileAuxImg+"')");

            MiConfiguracion miweb = new MiConfiguracion();
            miweb.setFooter("Zhurdazo Productions Co.<br>+562345321, Techmuco EcoSoftware Space<br>Powered by &copy; JUFRO");
            HttpSession session = null;
            miweb.setContent("<b>Archivo Subido</b>",request,session);
            miweb.setHeader("Can&iacute;bales del Tiempo, RAD to your life");

            out.print(miweb.getWebPage());
        }catch(SQLException e){

        }catch (ClassNotFoundException ex) {
            Logger.getLogger(StyleChang.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
            Logger.getLogger(Uploader.class.getName()).log(Level.SEVERE, null, ex);
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
