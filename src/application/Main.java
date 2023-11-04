package application;

import model.entities.Department;
import model.entities.Seller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Department obj = new Department(1, "Books");
        Seller seller = new Seller(2, "Carlos", "email@gmail.com", new Date(), 5020.99, obj);

        System.out.println(seller);
    }
}
