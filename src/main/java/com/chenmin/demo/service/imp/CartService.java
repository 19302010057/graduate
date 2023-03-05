package com.store.service.imp;

import com.store.entity.dto.CartItemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    int addProduct(String account,int id,int num);
    int existProduct(String account,int id);//判断购物车中是否已经存在某本图书
    int deleteProduct(String account,int id);
    int delBatchProduct(String account,int[] ids);//批量删除图书
    int modifyProductNum(String account,int id,int num);//修改购物车某本图书的数量
    List<CartItemDto> getCartsByPage(String account, int page, int pageSize);
    int count(String account);//得到某人的购物车商品总数
    int getItemCount(String account, int id);
}
