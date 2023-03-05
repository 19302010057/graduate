package com.store.controller;
import com.store.entity.dto.CartItemDto;
import com.store.service.imp.CartService;
import com.store.service.imp.ItemService;
import com.store.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
@ResponseBody
@RequestMapping(value = "/cart")
public class CartController {

    @Autowired
    @Qualifier("firstCart")
    CartService cartService;

    @Autowired
    @Qualifier("firstItem")
    ItemService itemService;

    /**
     * 添加到购物车
     * @param id 商品的id
     * @param num 商品数量
     * @param account 账户名
     */
    @GetMapping("/addCart")
    public Map<String,Object> addCart(@RequestParam("id")int id,
                                      @RequestParam("num")int num,
                                      @RequestParam("account")String account){
        if(cartService.existProduct(account,id)>0){
            return ResultUtil.resultCode(500,"购物车中已经存在该商品");
        }
        if(cartService.addProduct(account,id,num)>0){
            return ResultUtil.resultCode(200,"添加到购物车成功");
        }
        return ResultUtil.resultCode(500,"添加到购物车失败");
    }

    /**
     * 删除购物车商品
     * @param id 商品id
     * @param account 账户名
     */
    @GetMapping("/delCart")
    public Map<String,Object> delCart(@RequestParam("id")int id,
                                      @RequestParam("account")String account){
        if(cartService.deleteProduct(account,id)>0){
            return ResultUtil.resultCode(200,"删除成功");
        }
        return ResultUtil.resultCode(500,"删除失败");
    }

    /**
     * 批量删除购物车商品
     * @param ids 商品id
     * @param account 账户名
     */
    @GetMapping("/batchDelCart")
    public Map<String,Object> addCart(@RequestParam("ids")int[] ids,
                                      @RequestParam("account")String account){
        if(cartService.delBatchProduct(account,ids)>0){
            return ResultUtil.resultCode(200,"删除成功");
        }
        return ResultUtil.resultCode(500,"删除失败");
    }

    /**
     * 修改购物车商品数量
     * @param id 商品id
     * @param num 修改后数量
     * @param account 账户名
     * @return
     */
    @GetMapping("/modifyCart")
    public Map<String,Object> modifyCart(@RequestParam("id")int id,
                                      @RequestParam("num")int num,
                                      @RequestParam("account")String account){
        if(cartService.modifyProductNum(account,id,num)>0){
            return ResultUtil.resultCode(200,"修改成功");
        }
        return ResultUtil.resultCode(500,"修改失败");
    }


    /**
     * 获取商品列表
     * @param account 账户名
     * @param page 当前页
     * @param pageSize 页的大小
     * @return 包括cartBookDtoList（列表）和total（总量）
     */
    @GetMapping("/getCartList")
    public Map<String,Object> getCartList(@RequestParam("account")String account,
                                          @RequestParam("page")int page,
                                          @RequestParam("pageSize")int pageSize){
        Map<String,Object> map = new HashMap<>();
        List<CartItemDto> cartBookDtoList = cartService.getCartsByPage(account, page, pageSize);
        map.put("cartBookDtoList",cartBookDtoList);
        int count = cartService.count(account);
        map.put("total",count);
        return ResultUtil.resultSuccess(map);
    }
}
