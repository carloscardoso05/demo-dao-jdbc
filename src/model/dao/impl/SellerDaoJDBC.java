package model.dao.impl;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SellerDaoJDBC implements SellerDao {

    private final Connection connection;

    public SellerDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insert(Seller obj) {

    }

    @Override
    public void update(Seller obj) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    private Department instantiateDepartment(ResultSet rs) throws SQLException {
        Department department = new Department();
        department.setId(rs.getInt("DepartmentId"));
        department.setName(rs.getString("DepName"));
        return department;
    }

    private Seller instantiateSeller(ResultSet rs, Department department) throws SQLException {
        Seller seller = new Seller();
        seller.setId(rs.getInt("Id"));
        seller.setName(rs.getString("Name"));
        seller.setBaseSalary(rs.getDouble("BaseSalary"));
        seller.setBirthDate(rs.getDate("BirthDate"));
        seller.setEmail(rs.getString("Email"));
        seller.setDepartment(department);
        return seller;
    }

    @Override
    public Seller findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement("""
                    SELECT seller.*,department.Name as DepName
                    FROM seller INNER JOIN department
                    ON seller.DepartmentId = department.Id
                    WHERE seller.Id = ?
                    """);

            st.setInt(1, id);
            rs = st.executeQuery();
            if (rs.next()) {
                Department department = instantiateDepartment(rs);
                return instantiateSeller(rs, department);
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
    public List<Seller> findByDepartment(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement("""
                    SELECT seller.*, department.Name as DepName
                    FROM seller INNER JOIN department
                    ON seller.DepartmentId = department.Id
                    WHERE DepartmentId = ?
                    ORDER BY Name 
                    """);
            st.setInt(1, department.getId());
            rs = st.executeQuery();

            List<Seller> sellers = new ArrayList<>();
            Map<Integer, Department> departmentMap = new HashMap<>();

            while (rs.next()) {
                Department dep = departmentMap.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    departmentMap.put(dep.getId(), dep);
                }
                Seller seller = instantiateSeller(rs, dep);
                sellers.add(seller);
            }

            return sellers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Seller> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = connection.prepareStatement("""
                    SELECT seller.*, department.Name as DepName
                    FROM seller INNER JOIN department
                    ON seller.DepartmentId = department.Id
                    ORDER BY Name 
                    """);
            rs = st.executeQuery();

            Map<Integer, Department> departmentMap = new HashMap<>();
            List<Seller> sellers = new ArrayList<>();

            while (rs.next()) {
                Department dep = departmentMap.get(rs.getInt("DepartmentId"));
                if (dep == null) {
                    dep = instantiateDepartment(rs);
                    departmentMap.put(dep.getId(), dep);
                }
                Seller seller = instantiateSeller(rs, dep);
                sellers.add(seller);
            }

            return sellers;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
    }
}
