<%--
  Created by IntelliJ IDEA.
  User: jaimi
  Date: 4/06/2024
  Time: 18:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="modelo.Employees" %>

<html>
<head>
    <title>Lista de Empleados</title>
</head>
<body>
<h2>Lista de Empleados</h2>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Nombre</th>
        <th>Email</th>
        <th>Teléfono</th>
        <th>Fecha de Contratación</th>
        <th>Acciones</th>
    </tr>
    <%
        Object obj = request.getAttribute("listEmployees");
        if (obj instanceof List) {
            List<Employees> listEmployees = (List<Employees>) obj;
            for (Employees employee : listEmployees) {
    %>
    <tr>
        <td><%= employee.getId() %></td>
        <td><%= employee.getFullNameEmployee() %></td>
        <td><%= employee.getEmail() %></td>
        <td><%= employee.getPhoneNumber() %></td>
        <td><%= employee.getHireDate().toString() %></td>
        <td>
            <a href="editEmployee?edit=true&id=<%= employee.getId() %>">Editar</a>
            <a href="home?action=delete&id=<%= employee.getId() %>" onclick="return confirm('¿Está seguro?')">Eliminar</a>
        </td>
    </tr>
    <%
            }
        }
    %>
</table>
<a href="editEmployee?new=true">Agregar Empleado</a>
</body>
</html>