package nl.windesheim.ictm2f.order;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    private List<Order> orders;
    private Order currentOrder;

    public OrderManager() {
        this.orders = new ArrayList<Order>();
        this.currentOrder = null;
    }

    public Order getCurrentOrder() {
        return this.currentOrder;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void createEmptyOrder(String orderName) {
        Order newOrder = new Order();
        newOrder.setName(orderName);
        if (orderName.equals("a")) {
            newOrder.addPoint("0", 1, 1);
            newOrder.addPoint("1", 2, 3);
            newOrder.addPoint("2", 4, 4);
        }

        if (orderName.equals("b")) {
            newOrder.addPoint("0", 2, 1);
            newOrder.addPoint("1", 3, 4);
            newOrder.addPoint("2", 5, 1);
        }

        this.orders.add(newOrder);
    }

    public void setCurrentOrder(Order order) {
        this.currentOrder = order;
    }

    public void deleteOrder(String orderName) {
        for (Order order : this.orders) {
            if (order.getName().equals(orderName)) {
                this.orders.remove(order);
                break;
            }
        }
    }
}
