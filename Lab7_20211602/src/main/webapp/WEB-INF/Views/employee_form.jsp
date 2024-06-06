<%--
  Created by IntelliJ IDEA.
  User: jaimi
  Date: 4/06/2024
  Time: 18:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="modelo.Employees" %>
<%
    Employees employee = (Employees) request.getAttribute("employee");
    String jobTitle = (String) request.getAttribute("jobTitle");  // Título del puesto obtenido
    boolean isEdit = employee != null;
%>
<html>
<head>
    <title><%= isEdit ? "Editar Empleado" : "Agregar Empleado" %></title>
</head>
<body>
<h2><%= isEdit ? "Editar Empleado" : "Agregar Empleado" %></h2>
<form action="<%= isEdit ? "editEmployee?action=update" : "insertEmployee" %>" method="post">
    <% if (isEdit) { %>
    <input type="hidden" name="id" value="<%= employee.getId() %>" />
    <% } %>
    <label for="firstName">Nombre:</label>
    <input type="text" id="firstName" name="firstName" value="<%= isEdit ? employee.getFirstName() : "" %>" /><br/>

    <label for="lastName">Apellido:</label>
    <input type="text" id="lastName" name="lastName" value="<%= isEdit ? employee.getLastName() : "" %>" /><br/>

    <label for="email">Email:</label>
    <input type="text" id="email" name="email" value="<%= isEdit ? employee.getEmail() : "" %>" /><br/>

    <label for="phoneNumber">Teléfono:</label>
    <input type="text" id="phoneNumber" name="phoneNumber" value="<%= isEdit ? employee.getPhoneNumber() : "" %>" /><br/>

    <label for="hireDate">Fecha de Contratación:</label>
    <input type="date" id="hireDate" name="hireDate" value="<%= isEdit && employee.getHireDate() != null ? employee.getHireDate().toString() : "" %>" /><br/>

    <label for="jobId">Puesto Actual:</label>
    <input type="text" id="jobId" name="jobId" value="<%= isEdit ? employee.getJobId() : "" %>" /> -
    <input type="text" disabled value="<%= jobTitle %>" /><br/>

    <label for="salary">Salario:</label>
    <input type="text" id="salary" name="salary" value="<%= isEdit ? employee.getSalary() : "" %>" /><br/>

    <label for="commissionPct">Porcentaje de Comisión:</label>
    <input type="text" id="commissionPct" name="commissionPct" value="<%= isEdit ? employee.getCommissionPct() : "" %>" /><br/>

    <label for="managerId">ID del Manager:</label>
    <input type="text" id="managerId" name="managerId" value="<%= isEdit ? employee.getManagerId() : "" %>" /><br/>

    <label for="departmentId">ID del Departamento:</label>
    <input type="text" id="departmentId" name="departmentId" value="<%= isEdit ? employee.getDepartmentId() : "" %>" /><br/>

    <input type="submit" value="<%= isEdit ? "Actualizar" : "Agregar" %>" />
</form>

<!-- Formulario para agregar un nuevo puesto -->
<h3>Agregar Nuevo Puesto</h3>
<form action="editEmployee?action=addJob" method="post">
    <label for="newJobId">ID del Puesto:</label>
    <input type="text" id="newJobId" name="newJobId" /><br/>

    <label for="newJobTitle">Título del Puesto:</label>
    <input type="text" id="newJobTitle" name="newJobTitle" /><br/>

    <label for="minSalary">Salario Mínimo:</label>
    <input type="text" id="minSalary" name="minSalary" /><br/>

    <label for="maxSalary">Salario Máximo:</label>
    <input type="text" id="maxSalary" name="maxSalary" /><br/>

    <input type="submit" value="Agregar Puesto" />
</form>
</body>
</html>


