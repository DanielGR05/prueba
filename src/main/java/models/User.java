package models;

import java.util.ArrayList;
import java.util.List;

public class User {
    private List<Order> orderList;

    public User() {
        orderList = new ArrayList<>();
    }

    public List<Order> orders() {
        return orderList;
    }

    public void addOrder(Order o) {
        orderList.add(o);
    }
}
