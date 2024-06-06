<%--
  Created by IntelliJ IDEA.
  User: jaimi
  Date: 5/06/2024
  Time: 14:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<html>
<head>
    <title>Lista de Puestos de Trabajo</title>
</head>
<body>
<h2>Lista de Puestos de Trabajo</h2>
<table border="1">
    <tr>
        <th>ID del Puesto</th>
    </tr>
    <% List<String> listJobs = (List<String>) request.getAttribute("listJobs");
        for (String jobId : listJobs) { %>
    <tr>
        <td><%= jobId %></td>
    </tr>
    <% } %>
</table>
</body>
</html>


