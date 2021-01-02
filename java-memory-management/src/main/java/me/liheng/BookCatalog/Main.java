package me.liheng.BookCatalog;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;


public class Main {

    public static void main(String[] args) {

        BookCollection bc = new BookCollection();

        System.out.println("Before: ");
        bc.printAllBooks();
        System.out.println();

        //get price of book called Tom Jones in EUR
        System.out.println(bc.findBookByName("Tom Jones").getPrice().convert("EUR"));

        System.out.println("After: ");
        bc.printAllBooks();
        System.out.println();
    }
}

