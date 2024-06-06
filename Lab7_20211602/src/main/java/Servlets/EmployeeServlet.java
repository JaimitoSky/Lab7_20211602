package Servlets;

import dao.DaoEmployee;
import modelo.Employees;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.sql.Date;


@WebServlet(name = "EmployeeServlet", urlPatterns = {"/home"})
public class EmployeeServlet extends HttpServlet {
    private DaoEmployee daoEmployee;

    public void init() {
        daoEmployee = new DaoEmployee();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;

                case "/insert":
                    insertEmployee(request, response);
                    break;
                case "/delete":
                    deleteEmployee(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateEmployee(request, response);
                    break;
                default:
                    listEmployees(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException("Database error", ex);
        }
    }


    private void listEmployees(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        List<Employees> listEmployees = daoEmployee.listarEmpleados();
        request.setAttribute("listEmployees", listEmployees);
        request.getRequestDispatcher("/WEB-INF/Views/employees_list.jsp").forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/Views/employees_list.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        int id = Integer.parseInt(request.getParameter("id"));
        Employees existingEmployee = daoEmployee.obtenerEmpleado(id);
        request.setAttribute("employee", existingEmployee);
        request.getRequestDispatcher("/WEB-INF/Views/employees_list.jsp").forward(request, response);
    }

    private void insertEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {//no bota error pero en si no agrega un empleado a la BD
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String jobId = request.getParameter("jobId");
        double salary = Double.parseDouble(request.getParameter("salary"));
        double commissionPct = Double.parseDouble(request.getParameter("commissionPct"));
        int managerId = request.getParameter("managerId") != null && !request.getParameter("managerId").isEmpty() ? Integer.parseInt(request.getParameter("managerId")) : 0;
        int departmentId = request.getParameter("departmentId") != null && !request.getParameter("departmentId").isEmpty() ? Integer.parseInt(request.getParameter("departmentId")) : 0;
        java.sql.Date hireDate = request.getParameter("hireDate") != null ? java.sql.Date.valueOf(request.getParameter("hireDate")) : null;

        Employees newEmployee = new Employees();
        newEmployee.setFirstName(firstName);
        newEmployee.setLastName(lastName);
        newEmployee.setEmail(email);
        newEmployee.setPhoneNumber(phoneNumber);
        newEmployee.setHireDate(hireDate);
        newEmployee.setJobId(jobId);
        newEmployee.setSalary(salary);
        newEmployee.setCommissionPct(commissionPct);
        newEmployee.setManagerId(managerId);
        newEmployee.setDepartmentId(departmentId);

        daoEmployee.agregarEmpleado(newEmployee);

        response.sendRedirect("home");
    }


    private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {//funcional menos con el jefe, empleado de id 100
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String jobId = request.getParameter("jobId");
        String hireDateString = request.getParameter("hireDate");
        Date hireDate = null;
        if (hireDateString != null && !hireDateString.isEmpty()) {
            hireDate = Date.valueOf(hireDateString);
        }
        double salary = Double.parseDouble(request.getParameter("salary"));
        double commissionPct = Double.parseDouble(request.getParameter("commissionPct"));
        int managerId = 0;
        if (request.getParameter("managerId") != null && !request.getParameter("managerId").isEmpty()) {
            managerId = Integer.parseInt(request.getParameter("managerId"));
        }

        int departmentId = 0;
        if (request.getParameter("departmentId") != null && !request.getParameter("departmentId").isEmpty()) {
            departmentId = Integer.parseInt(request.getParameter("departmentId"));
        }



        Employees updatedEmployee = new Employees(id, firstName, lastName, email, phoneNumber, hireDate, jobId, salary, commissionPct, managerId, departmentId);
        daoEmployee.actualizarEmpleado(updatedEmployee);
        response.sendRedirect("home");
    }

    private void deleteEmployee(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException { //no funcional
        int id = Integer.parseInt(request.getParameter("id"));
        daoEmployee.borrarEmpleado(id);
        response.sendRedirect("home");
    }
}

