package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("\n---- TEST 1: seller findById ----");
        Seller seller = sellerDao.findById(7);
        System.out.println(seller);

        System.out.println("\n---- TEST 2: seller findByDepartment ----");
        Department dep = new Department(1, null);
        List<Seller> sellers = sellerDao.findByDepartment(dep);
        for (Seller sel : sellers) {
            System.out.println(sel);
        }

        System.out.println("\n---- TEST 3: seller findAll ----");
        sellers = sellerDao.findAll();
        for (Seller sel : sellers) {
            System.out.println(sel);
        }

    }
}
