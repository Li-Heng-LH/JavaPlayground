package me.liheng;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class CustomerRecords implements Iterable<Customer> {
    private Map<String, Customer> records;

    public CustomerRecords() {
        this.records = new HashMap<String, Customer>();
    }

    public void addCustomer(Customer c) {
        this.records.put(c.getName(), c);
    }

    public Map<String, Customer> getCustomers() {
        //Original:
        //return this.records;

        //Solution 2: return a copy of internal object
        //return new HashMap<String, Customer>(this.records);

        //Solution 3: Return unmodifyableMap
        return Collections.unmodifiableMap(this.records);
    }

    //Solution 1: make internal object iterable
    public Iterator<Customer> iterator() {
        return records.values().iterator();
    }

}
