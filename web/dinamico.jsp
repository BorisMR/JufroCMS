<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="miaplicacionweb.MiConfiguracion"%>
<%@page import="jufroweb.Content"%>

        <%
        
        MiConfiguracion miweb = new MiConfiguracion();
        //JufroPage mipagina = new JufroPage(mic,request,session);
        Content gestor = new Content();
        String todos = gestor.allToHtml();
        miweb.setContent(todos,request,session);
        out.print(miweb.getWebPage()); 
        
        %>
