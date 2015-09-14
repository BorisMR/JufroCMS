<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="jufroweb.JufroCMSConnection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="miaplicacionweb.MiConfiguracion"%>
<%@page import="jufroweb.Content"%>

<%
    MiConfiguracion miweb = new MiConfiguracion();
    int layoutSelected = Integer.parseInt(request.getParameter("opsel"));
    int layoutInUse;
    //String layoutSelectedDel = null;
    String relPath = "C:\\Users\\MULTIVAC\\Documents\\NetBeansProjects\\GithubProjects\\JufroCMSv5\\web\\";
   
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
            miweb.setContent("<b>LAYOUT ELIMINADO</b>",request,session);
            miweb.setHeader("Can&iacute;bales del Tiempo, RAD to your life");
            out.print(miweb.getWebPage()); 
        }else{
            stm.execute("DELETE FROM STYLES WHERE ID = "+layoutSelected);
            miweb.setFooter("Zhurdazo Productions Co.<br>+562345321, Techmuco EcoSoftware Space<br>Powered by &copy; JUFRO");
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
        miweb.setContent("<b>LAYOUT MODIFICADO</b>",request,session);
        miweb.setHeader("Can&iacute;bales del Tiempo, RAD to your life");

        out.print(miweb.getWebPage());    
    }
%>
