package dao;

import modelo.Employees;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DaoEmployee {
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

    public List<Employees> listarEmpleados() throws SQLException {
        List<Employees> empleados = new ArrayList<>();
        String sql = "SELECT * FROM employees";
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Employees empleado = new Employees();
                empleado.setId(resultSet.getInt("employee_id"));
                empleado.setFirstName(resultSet.getString("first_name"));
                empleado.setLastName(resultSet.getString("last_name"));
                empleado.setEmail(resultSet.getString("email"));
                empleado.setPhoneNumber(resultSet.getString("phone_number"));
                empleado.setHireDate(resultSet.getDate("hire_date"));
                empleado.setJobId(resultSet.getString("job_id"));
                empleado.setSalary(resultSet.getDouble("salary"));
                empleado.setCommissionPct(resultSet.getDouble("commission_pct"));
                empleado.setManagerId(resultSet.getInt("manager_id"));
                empleado.setDepartmentId(resultSet.getInt("department_id"));
                empleados.add(empleado);
            }
        }
        return empleados;
    }

    public Employees obtenerEmpleado(int id) throws SQLException {
        Employees empleado = null;
        String sql = "SELECT * FROM employees WHERE employee_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    empleado = new Employees();
                    empleado.setId(resultSet.getInt("employee_id"));
                    empleado.setFirstName(resultSet.getString("first_name"));
                    empleado.setLastName(resultSet.getString("last_name"));
                    empleado.setEmail(resultSet.getString("email"));
                    empleado.setPhoneNumber(resultSet.getString("phone_number"));
                    empleado.setHireDate(resultSet.getDate("hire_date"));
                    empleado.setJobId(resultSet.getString("job_id"));
                    empleado.setSalary(resultSet.getDouble("salary"));
                    empleado.setCommissionPct(resultSet.getDouble("commission_pct"));
                    empleado.setManagerId(resultSet.getInt("manager_id"));
                    empleado.setDepartmentId(resultSet.getInt("department_id"));
                }
            }
        }
        return empleado;
    }

    public void actualizarEmpleado(Employees empleado) throws SQLException {
        String sql = "UPDATE employees SET first_name = ?, last_name = ?, email = ?, phone_number = ?, hire_date = ?, job_id = ?, salary = ?, commission_pct = ?, manager_id = ?, department_id = ? WHERE employee_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, empleado.getFirstName());
            statement.setString(2, empleado.getLastName());
            statement.setString(3, empleado.getEmail());
            statement.setString(4, empleado.getPhoneNumber());
            if (empleado.getHireDate() != null) {
                statement.setDate(5, new java.sql.Date(empleado.getHireDate().getTime()));
            } else {
                statement.setDate(5, null);
            }

            statement.setString(6, empleado.getJobId());
            statement.setDouble(7, empleado.getSalary());
            statement.setDouble(8, empleado.getCommissionPct());
            statement.setInt(9, empleado.getManagerId());
            statement.setInt(10, empleado.getDepartmentId());
            statement.setInt(11, empleado.getId());
            statement.executeUpdate();
        }
    }

    public void borrarEmpleado(int id) throws SQLException {
        String sql = "DELETE FROM employees WHERE employee_id = ?";
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
    public void agregarEmpleado(Employees empleado) throws SQLException {
        String sql = "INSERT INTO employees (first_name, last_name, email, phone_number, hire_date, job_id, salary, commission_pct, manager_id, department_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, empleado.getFirstName());
            preparedStatement.setString(2, empleado.getLastName());
            preparedStatement.setString(3, empleado.getEmail());
            preparedStatement.setString(4, empleado.getPhoneNumber());
            java.util.Date utilDate = empleado.getHireDate();
            if(utilDate != null) {
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                preparedStatement.setDate(5, sqlDate);
            } else {
                preparedStatement.setNull(5, Types.DATE);
            }
            preparedStatement.setString(6, empleado.getJobId());
            preparedStatement.setDouble(7, empleado.getSalary());
            preparedStatement.setDouble(8, empleado.getCommissionPct());
            if (empleado.getManagerId() != null) {
                preparedStatement.setInt(9, empleado.getManagerId());
            } else {
                preparedStatement.setNull(9, Types.INTEGER);
            }
            if (empleado.getDepartmentId() != null) {
                preparedStatement.setInt(10, empleado.getDepartmentId());
            } else {
                preparedStatement.setNull(10, Types.INTEGER);
            }
            preparedStatement.executeUpdate();
        }
    }
}
