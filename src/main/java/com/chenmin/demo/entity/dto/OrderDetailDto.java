package com.store.entity.dto;


import com.store.entity.book.Item;

/**
 * @author: 许同樵
 * @description: 订单明细类
 */
public class OrderDetailDto {
    private String orderId;//订单号
    private Item item; //图书
    private String num;//某本图书的下单数量
    private double price;//下单时候图书的单价

    @Override
    public String toString() {
        return "OrderDetailDto{" +
                "orderId='" + orderId + '\'' +
                ", item=" + item +
                ", num='" + num + '\'' +
                ", price=" + price +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
