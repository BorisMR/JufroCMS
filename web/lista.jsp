<%@page import="jufroweb.Layout"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="jufroweb.JufroCMSConnection"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="miaplicacionweb.MiConfiguracion"%>
<%@page import="jufroweb.Content"%>

<%

    MiConfiguracion miweb = new MiConfiguracion();
    Layout stl = new Layout();
    miweb.setContent(stl.generarHtmlLista(),request,session);
    out.print(miweb.getWebPage()); 

%>
        
        <style>
            #opsel{
                width: 400px;
                height: 300px;
                background-color: black;
                color: white;
                font-style: italic;
                text-align: center;
                font-weight: bold;
                border-radius: 5px 0px;
            }
        </style>