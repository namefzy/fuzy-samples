package com.fuzy.example.visitor;
import java.util.*;

/**
 * @ClassName CustomerGroup
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 9:34
 * @Version 1.0.0
 */
public class CustomerGroup {

    private List<Customer> customers = new ArrayList<>();

    void accept(Visitor visitor) {
        for (Customer customer : customers) {
            customer.accept(visitor);
        }
    }

    void addCustomer(Customer customer) {
        customers.add(customer);
    }
}
