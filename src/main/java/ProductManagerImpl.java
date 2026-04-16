package edu.upc.dsa;

import models.Order;
import models.Product;
import models.User;

import org.apache.log4j.Logger;

import java.util.*;

public class ProductManagerImpl implements ProductManager {
    private static final Logger logger = Logger.getLogger(ProductManagerImpl.class);

    private static ProductManagerImpl instance;

    private List<Product> productList;
    private Queue<Order> orderQueue;
    private HashMap<String, User> users;

    private ProductManagerImpl() {
        productList = new ArrayList<>();
        orderQueue = new LinkedList<>();
        users = new HashMap<>();
        logger.info("ProductManagerImpl initialized");
    }

    public static ProductManagerImpl getInstance() {
        if (instance == null) {
            instance = new ProductManagerImpl();
            logger.info("ProductManagerImpl singleton instance created");
        }
        return instance;
    }

    /**
     * Clear internal state (useful for tests).
     */
    public void clear() {
        logger.info("clear() - start");
        productList.clear();
        orderQueue.clear();
        users.clear();
        logger.info("clear() - end");
    }

    @Override
    public void addProduct(String id, String name, double price) {
        logger.info(String.format("addProduct(start) id=%s name=%s price=%s", id, name, price));
        productList.add(new Product(id, name, price));
        logger.info(String.format("addProduct(end) totalProducts=%d", productList.size()));
    }

    @Override
    public List<Product> getProductsByPrice() {
        logger.info("getProductsByPrice(start)");
        List<Product> sorted = new ArrayList<>(productList);
        sorted.sort(Comparator.comparingDouble(Product::getPrice)); // ascending by price
        logger.info(String.format("getProductsByPrice(end) count=%d", sorted.size()));
        return sorted;
    }

    @Override
    public List<Product> getProductsBySales() {
        logger.info("getProductsBySales(start)");
        List<Product> sorted = new ArrayList<>(productList);
        sorted.sort(Comparator.comparingInt(Product::sales).reversed()); // descending by sales
        logger.info(String.format("getProductsBySales(end) count=%d", sorted.size()));
        return sorted;
    }

    @Override
    public void addOrder(Order order) {
        logger.info(String.format("addOrder(start) user=%s lines=%d", order.getUser(), order.lines().size()));
        orderQueue.add(order);
        logger.info(String.format("addOrder(end) queueSize=%d", orderQueue.size()));
    }

    @Override
    public int numOrders() {
        logger.info("numOrders(start)");
        int size = orderQueue.size();
        logger.info(String.format("numOrders(end)=%d", size));
        return size;
    }

    @Override
    public Order deliverOrder() {
        logger.info("deliverOrder(start)");
        Order order = orderQueue.poll();
        if (order == null) {
            logger.info("deliverOrder(end) no orders to deliver");
            return null;
        }

        // update product sales
        for (Order.Line line : order.lines()) {
            Product p = getProduct(line.getProductId());
            if (p != null) {
                p.addSales(line.getQty());
                logger.info(String.format("deliverOrder - updated sales product=%s added=%d total=%d", p.getId(), line.getQty(), p.sales()));
            } else {
                logger.warn(String.format("deliverOrder - product not found (ignored): %s", line.getProductId()));
            }
        }

        // record order for user
        String uid = order.getUser();
        User u = users.get(uid);
        if (u == null) {
            u = new User();
            users.put(uid, u);
            logger.info(String.format("deliverOrder - new user created: %s", uid));
        }
        u.addOrder(order);
        logger.info(String.format("deliverOrder(end) served user=%s totalUserOrders=%d queueRemaining=%d", uid, u.orders().size(), orderQueue.size()));

        return order;
    }

    @Override
    public Product getProduct(String c1) {
        logger.info(String.format("getProduct(start) id=%s", c1));
        for (Product p : productList) {
            if (p.getId().equals(c1)) {
                logger.info(String.format("getProduct(end) found id=%s", c1));
                return p;
            }
        }
        logger.info(String.format("getProduct(end) not found id=%s", c1));
        return null;
    }

    @Override
    public User getUser(String number) {
        logger.info(String.format("getUser(start) id=%s", number));
        User u = users.get(number);
        logger.info(String.format("getUser(end) found=%s", (u != null)));
        return u;
    }

    @Override
    public List<Order> getOrdersByUser(String userId) {
        logger.info(String.format("getOrdersByUser(start) userId=%s", userId));
        User u = users.get(userId);
        if (u == null) {
            logger.info("getOrdersByUser(end) user not found");
            return new ArrayList<>();
        }
        logger.info(String.format("getOrdersByUser(end) count=%d", u.orders().size()));
        return u.orders();
    }
}
