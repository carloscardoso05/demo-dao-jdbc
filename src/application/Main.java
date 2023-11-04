package application;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.Date;
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

        System.out.println("\n---- TEST 4: seller deleteById ----");
        int id = 1;
        System.out.println(sellerDao.findById(id));
        sellerDao.deleteById(id);
        System.out.println("Deleted seller with Id " + id);
        sellers = sellerDao.findAll();
        for (Seller sel : sellers) {
            System.out.println(sel);
        }

        System.out.println("\n---- TEST 5: seller insert ----");
        dep = new Department(2, null);
        seller = new Seller(null, "João", "joao@gmail.com", new Date(), 12050.40, dep);
        sellerDao.insert(seller);
        sellers = sellerDao.findAll();
        for (Seller sel : sellers) {
            System.out.println(sel);
        }
    }
}
