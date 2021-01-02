package me.liheng;

public class Main {

    public static void main(String[] args) {
        CustomerRecords records = new CustomerRecords();

        records.addCustomer(new Customer("John"));
        records.addCustomer(new Customer("Simon"));

        //Solution 3: UnsupportedOperationException
        //records.getCustomers().clear();

        for (Customer next : records.getCustomers().values())
        {
            System.out.println(next);
        }

        ReadOnlyCustomer john = records.getReadOnlyCustomerByName("John");
        //john.setName("ABC"); //Error
    }

}
