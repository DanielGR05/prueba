package models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    public static class Line {
        private int qty;
        private String productId;

        public Line(int qty, String productId) {
            this.qty = qty;
            this.productId = productId;
        }

        public int getQty() {
            return qty;
        }

        public String getProductId() {
            return productId;
        }
    }

    private String user;
    private List<Line> items;

    public Order(String dni) {
        this.user = dni;
        this.items = new ArrayList<>();
    }

    public void addLP(int i, String s) {
        items.add(new Line(i, s));
    }

    public String getUser() {
        return user;
    }

    public List<Line> lines() {
        return items;
    }
}
