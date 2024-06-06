package Servlets;

import dao.DaoJobs;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "JobServlet", urlPatterns = {"/jobs"})
public class JobServlet extends HttpServlet {
    private DaoJobs daoJobs;

    public void init() {
        daoJobs = new DaoJobs();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            listJobs(request, response);
        } catch (SQLException ex) {
            throw new ServletException("SQL Error listing jobs", ex);
        }
    }

    private void listJobs(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        List<String> listJobs = daoJobs.listarPuestos();
        request.setAttribute("listJobs", listJobs);
        request.getRequestDispatcher("/WEB-INF/Views/jobs_list.jsp").forward(request, response);
    }
}
