package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoJobs {
    private String jdbcURL = "jdbc:mysql://localhost:3306/hr";
    private String jdbcUsername = "root";
    private String jdbcPassword = "tucontra";

    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error connecting to the database", e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Error finding the JDBC driver", e);
        }
        return connection;
    }


    public List<String> listarPuestos() throws SQLException { //funcional
        List<String> puestos = new ArrayList<>();
        String sql = "SELECT job_id FROM jobs";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                puestos.add(resultSet.getString("job_id"));
            }
        }
        return puestos;
    }


    public boolean agregarPuesto(String jobId, String jobTitle, int minSalary, int maxSalary) throws SQLException {
        String sql = "INSERT INTO jobs (job_id, job_title, min_salary, max_salary) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, jobId);
            preparedStatement.setString(2, jobTitle);
            preparedStatement.setInt(3, minSalary);
            preparedStatement.setInt(4, maxSalary);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public String obtenerPuestoPorId(String jobId) throws SQLException {
        String jobTitle = null;
        String sql = "SELECT job_title FROM jobs WHERE job_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, jobId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    jobTitle = resultSet.getString("job_title");
                }
            }
        }
        return jobTitle;
    }
}


