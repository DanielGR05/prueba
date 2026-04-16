package models;

public class Product {

    private String id;
    private String name;
    private double price;
    private int sold;

    public Product(String id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sold = 0;
    }

    public int sales() {
        return sold;
    }

    public void addSales(int n) {
        this.sold += n;
    }

    public double getPrice() {
        return price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
