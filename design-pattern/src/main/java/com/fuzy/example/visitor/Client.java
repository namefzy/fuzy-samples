package com.fuzy.example.visitor;

/**
 * @ClassName Client
 * @Description TODO
 * @Author 11564
 * @Date 2020/11/22 9:38
 * @Version 1.0.0
 */
public class Client {

    public static void main(String[] args) {
        Customer customer1 = new Customer("customer1");
        customer1.addOrder(new Order("order1", "item1"));
        customer1.addOrder(new Order("order2", "item1"));
        customer1.addOrder(new Order("order3", "item1"));

        Order order = new Order("order_a");
        order.addItem(new Item("item_a1"));
        order.addItem(new Item("item_a2"));
        order.addItem(new Item("item_a3"));
        Customer customer2 = new Customer("customer2");
        customer2.addOrder(order);

        CustomerGroup customers = new CustomerGroup();
        customers.addCustomer(customer1);
        customers.addCustomer(customer2);

        GeneralReport visitor = new GeneralReport();
        //把这些数据汇报给visitor角色
        customers.accept(visitor);
        visitor.displayResults();
    }
}
