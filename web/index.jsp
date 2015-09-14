<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="miaplicacionweb.MiConfiguracion"%>

<%
    MiConfiguracion miweb = new MiConfiguracion();
    //JufroPage mipagina = new JufroPage(mic,request,session);
    miweb.setContent("HOLA MUNDO",request,session);
    out.print(miweb.getWebPage());        
%>

