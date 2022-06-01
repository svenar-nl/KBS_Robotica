package nl.windesheim.ictm2f.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import nl.windesheim.ictm2f.Main;
import nl.windesheim.ictm2f.util.GridPoint;

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

        this.orders.add(newOrder);
    }

    public void updateOrder(Order order) {
        int index = 0;
        for (Order cachedOrder : this.orders) {
            if (cachedOrder.getName().equals(order.getName())) {
                this.orders.set(index, order);
                break;
            }
            index++;
        }
    }

    public void setCurrentOrder(String orderName) {
        if (currentOrder != null) {
            updateOrder(currentOrder);
        }
        for (Order order : this.orders) {
            if (order.getName().equals(orderName)) {
                this.currentOrder = order;
                break;
            }
        }
    }

    public void deleteOrder(String orderName) {
        for (Order order : this.orders) {
            if (order.getName().equals(orderName)) {
                this.orders.remove(order);
                break;
            }
        }
    }

    public void save() {
        for (Order order : getOrders()) {
            String orderName = order.getName();
            String orderPoints = "";
            for (GridPoint point : order.getPoints()) {
                orderPoints += point.getName() + "," + point.getX() + "," + point.getY() + ";";
            }
            if (orderPoints.endsWith(";")) {
                orderPoints = orderPoints.substring(0, orderPoints.length() - 1);
            }

            Main.getInstance().getCachedData().set("orders." + orderName, orderPoints);
        }
    }

    public void load() {
        this.orders.clear();
        for (Entry<String, Object> entry : Main.getInstance().getCachedData().getData().entrySet()) {
            if (entry.getKey().startsWith("orders.")) {
                String orderName = entry.getKey().replace("orders.", ""); // .substring(7);
                Order order = new Order();
                order.setName(orderName);
                List<GridPoint> points = new ArrayList<GridPoint>();
                String stringPoints = Main.getInstance().getCachedData().getString(entry.getKey());
                if (stringPoints.length() > 0) {
                    for (String pointData : stringPoints.split(";")) {
                        String pointName = pointData.split(",")[0];
                        int pointX = Integer.parseInt(pointData.split(",")[1]);
                        int pointY = Integer.parseInt(pointData.split(",")[2]);
                        points.add(new GridPoint(pointName, pointX, pointY));
                    }
                }
                order.setPoints(points);
                this.orders.add(order);
            }
        }
    }
}
