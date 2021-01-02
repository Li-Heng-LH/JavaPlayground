package me.liheng;

public class Customer implements ReadOnlyCustomer {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer(String name) {
        this.name = name;
    }

    public Customer (Customer oldCustomer) {
        this.name = oldCustomer.name;
    }

    public String toString() {
        return name;
    }

}
