    package Servlets;

    import java.sql.Date;

    import dao.DaoEmployee;
    import dao.DaoJobs;
    import modelo.Employees;
    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.HttpServlet;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.servlet.http.HttpServletResponse;
    import java.io.IOException;
    import java.sql.SQLException;

    @WebServlet(name = "EmployeeEditServlet", urlPatterns = {"/editEmployee"})
    public class EmployeeEditServlet extends HttpServlet {
        private DaoEmployee daoEmployee;
        private DaoJobs daoJobs;

        public void init() {
            daoEmployee = new DaoEmployee();
            daoJobs = new DaoJobs();
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String action = request.getParameter("action");
            try {
                if ("update".equals(action)) {
                    updateEmployee(request, response);
                } else if ("addJob".equals(action)) {
                    addJob(request, response);
                }
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                showEditForm(request, response);
            } catch (SQLException ex) {
                throw new ServletException(ex);
            }
        }

        private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
            String idStr = request.getParameter("id");
            String newEmployee = request.getParameter("new");
            if ("true".equals(newEmployee)) {
                request.setAttribute("employee", new Employees());
                request.getRequestDispatcher("/WEB-INF/Views/employee_form.jsp").forward(request, response);
            } else if (idStr != null && !idStr.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    Employees existingEmployee = daoEmployee.obtenerEmpleado(id);
                    if (existingEmployee != null) {
                        String jobTitle = daoJobs.obtenerPuestoPorId(existingEmployee.getJobId());
                        request.setAttribute("jobTitle", jobTitle);
                        request.setAttribute("employee", existingEmployee);
                        request.getRequestDispatcher("/WEB-INF/Views/employee_form.jsp").forward(request, response);
                    } else {

                        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Empleado con id  " + id + " no fue encontrado.");
                    }
                } catch (NumberFormatException e) {

                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Carácter incorrecto, ingresar números");
                }
            } else {

                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta ID o esta es incorrecta");
            }
        }
        private void updateEmployee(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
            int id = Integer.parseInt(request.getParameter("id"));
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String email = request.getParameter("email");
            String phoneNumber = request.getParameter("phoneNumber");
            String jobId = request.getParameter("jobId");
            double salary = Double.parseDouble(request.getParameter("salary"));
            double commissionPct = Double.parseDouble(request.getParameter("commissionPct"));
            int managerId = 0; // valor predeterminado si es nulo
            if (request.getParameter("managerId") != null && !request.getParameter("managerId").isEmpty()) {
                managerId = Integer.parseInt(request.getParameter("managerId"));
            }

            int departmentId = 0;
            if (request.getParameter("departmentId") != null && !request.getParameter("departmentId").isEmpty()) {
                departmentId = Integer.parseInt(request.getParameter("departmentId"));
            }
            Date hireDate = Date.valueOf(request.getParameter("hireDate"));


            Employees employee = new Employees(id, firstName, lastName, email, phoneNumber, hireDate, jobId, salary, commissionPct, managerId, departmentId);
            daoEmployee.actualizarEmpleado(employee);
            response.sendRedirect("home");
        }

        private void addJob(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {

            String jobId = request.getParameter("newJobId");
            String jobTitle = request.getParameter("newJobTitle");
            int minSalary = Integer.parseInt(request.getParameter("minSalary"));
            int maxSalary = Integer.parseInt(request.getParameter("maxSalary"));

            daoJobs.agregarPuesto(jobId, jobTitle, minSalary, maxSalary);
            response.sendRedirect("employees");
        }
    }


