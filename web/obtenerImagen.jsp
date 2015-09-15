<%@page import="jufroweb.Layout"%>
<%
    int layoutID = Integer.parseInt(request.getParameter("layout"));
    String imgPath = new Layout().getImagePath(layoutID);
    response.getWriter().print(imgPath.trim());
%>
