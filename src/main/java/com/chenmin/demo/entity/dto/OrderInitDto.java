package com.store.entity.dto;

import com.store.entity.order.Expense;
import com.store.entity.user.Address;

import java.util.List;

/**
 * @author: 许同樵
 * @description: 初始化订单的交互类
 */
public class OrderInitDto {
    private String account;//用户账号
    private List<OrderItemDto> itemList;
    private List<Address> addressList;//返回给前端显示的某个用户的地址
    private Expense expense;
    private Address address;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public List<OrderItemDto> getItemList() {
        return itemList;
    }

    public void setItemList(List<OrderItemDto> itemList) {
        this.itemList = itemList;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "OrderInitDto{" +
                "account='" + account + '\'' +
                ", bookList=" + itemList +
                ", addressList=" + addressList +
                ", expense=" + expense +
                ", address=" + address +
                '}';
    }
}
