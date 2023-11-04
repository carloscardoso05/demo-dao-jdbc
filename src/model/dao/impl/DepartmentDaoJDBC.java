package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entities.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDaoJDBC implements DepartmentDao {

    private final Connection connection;
    public DepartmentDaoJDBC(Connection connection) {
        this.connection = connection;
    }
    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("DepartmentId"));
        department.setName(rs.getString("DepName"));
        return department;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement("""
                    INSERT INTO department (Name)
                    VALUES (?)
                    """, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, obj.getName());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected error! No rows affected");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement("""
                    UPDATE department
                    SET Name = ?
                    WHERE Id = ?
                    """);
            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());
            st.executeQuery();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;

        try {
            st = connection.prepareStatement("""
                    DELETE FROM department
                    WHERE Id = ?
                    """);
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement("""
                    SELECT * FROM department
                    WHERE Id = ?
                    """);
            st.setInt(1, id);
            rs = st.executeQuery();

            if (rs.next()) {
                return instantiateDepartment(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement("""
                    SELECT * FROM department
                    """);
            rs = st.executeQuery();

            List<Department> departments = new ArrayList<>();
            while (rs.next()) {
                departments.add(instantiateDepartment(rs));
            }
            return departments;
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }
    }
}
