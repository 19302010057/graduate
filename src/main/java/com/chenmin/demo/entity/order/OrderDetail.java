package com.store.entity.order;

import java.sql.Timestamp;

/**
 * @author: 许同樵
 * @description: 订单明细
 */
public class OrderDetail {
    private String orderId;
    private int itemId;
    private int num;
    private double price;

    @Override
    public String toString() {
        return "OrderDetail{" +
                "orderId='" + orderId + '\'' +
                ", itemId=" + itemId +
                ", num=" + num +
                ", price=" + price +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
