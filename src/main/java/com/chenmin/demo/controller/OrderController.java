package com.store.controller;

import com.store.entity.dto.*;
import com.store.entity.order.Expense;
import com.store.entity.user.Address;
import com.store.service.imp.AddressService;
import com.store.service.imp.CartService;
import com.store.service.imp.ItemService;
import com.store.service.imp.OrderService;
import com.store.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@ResponseBody
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    @Qualifier("firstItem")
    ItemService itemService;

    @Autowired
    @Qualifier("firstCart")
    CartService cartService;

    @Autowired
    @Qualifier("firstAddress")
    AddressService addressService;

    @Autowired
    @Qualifier("orderService")
    OrderService orderService;

    /**
     * 初始化订单
     * @param ids 商品id
     * @param account 账户名
     * @param from 数据源，如果是购物车提交则为1（会删除购物车内的商品），如果是详情页直接购买为0
     * @return 返回一个orderInitDto即所有详情信息，使用它可以直接通过addOrder接口提交order
     */
    @GetMapping("/initOrder")
    public Map<String,Object> initOrder(@RequestParam(value = "ids") int[] ids,
                                        @RequestParam(value = "from") int from,
                                        @RequestParam(value = "account") String account){
        for(int i=0;i<ids.length;i++){
            System.out.println("===ids[i]========"+ids[i]+"==============");
        }
        Map<String,Object> map = new HashMap<>();
        Expense expense = new Expense();
        OrderInitDto orderInitDto = new OrderInitDto();
        List<OrderItemDto> batchItemList = new ArrayList<>();
        if(from==1){//从购物车点击提交的
            batchItemList = itemService.getBatchItemList(ids);
            for(int i=0;i<batchItemList.size();i++){
                int bookCount = cartService.getItemCount(account, batchItemList.get(i).getId());
                batchItemList.get(i).setNum(bookCount);
                System.out.println("====batchBookList.get(i).getNum():======"+batchItemList.get(i).getNum()+"======");
            }
            cartService.delBatchProduct(account,ids);//删除购物车中的商品
        }else {//从详情页点击提交的
            batchItemList = itemService.getOneItemList(ids);
            batchItemList.get(0).setNum(1);
        }

        Double productTotalMoney = 0.0;
        for(OrderItemDto orderItemDto :batchItemList){
            productTotalMoney = productTotalMoney + orderItemDto.getPrice()*orderItemDto.getNum();//得到订单总价
        }
        expense.setProductTotalMoney(productTotalMoney);//商品总价
        expense.setFreight(0);
        expense.setCoupon(0);
        expense.setActivityDiscount(0);
        expense.setAllPrice(productTotalMoney);//订单总金额
        expense.setFinallyPrice(productTotalMoney);//最终实付金额
        List<Address> addressList = addressService.addressList(account);//得到某个用户的地址列表
        orderInitDto.setAddressList(addressList);
        orderInitDto.setItemList(batchItemList);
        orderInitDto.setExpense(expense);
        orderInitDto.setAccount(account);
        System.out.println("============account:==========="+account+"============");
        map.put("orderInitDto",orderInitDto);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 提交订单，提交前需让用户选定收货地址
     * @param orderInitDto 由initOrder返回的对象
     */
    @PostMapping("/addOrder")
    public Map<String,Object> addOrder(@RequestBody OrderInitDto orderInitDto){
        if(!orderService.addOrder(orderInitDto)){
            return ResultUtil.resultCode(500,"提交订单失败");
        }
        return ResultUtil.resultCode(200,"提交订单成功");
    }


    /**
     * 按页获取所有订单
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/getAdminOrderList")
    public Map<String,Object> getOrderList(@RequestParam("page")int page,
                                       @RequestParam("pageSize")int pageSize){
        System.out.println("=========请求到达获取订单接口===========");
        List<OrderDto> orderDtoList = orderService.orderDtoList("", page, pageSize,"",false);
        for(OrderDto o:orderDtoList){
            List<OrderDetailDto> orderDetailDtoList = orderService.findOrderDetailDtoList(o.getOrderId());
            o.setOrderDetailDtoList(orderDetailDtoList);
        }
        Map<String,Object> map= new HashMap<>();
        map.put("orderDtoList",orderDtoList);
        int total = orderService.count("","",false);
        map.put("total",total);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 获取某个订单信息
     * @param id 订单号
     */
    @GetMapping("/getOrderDto")
    public Map<String,Object> getOrderDto(@RequestParam("id")int id){
        OrderDto orderDto = orderService.findOrderDto(id);
        List<OrderDetailDto> orderDetailDtoList = orderService.findOrderDetailDtoList(orderDto.getOrderId());
        orderDto.setOrderDetailDtoList(orderDetailDtoList);
        //        for(int i=0;i<orderDetailDtoList.size();i++){
//            String img = itemService.getBookCover(orderDetailDtoList.get(i).getBook().getisbn());
//            orderDetailDtoList.get(i).getBook().setCoverImg(img);
//            System.out.println("=======orderDetailDtoList.size():====="+orderDetailDtoList.size()+"============");
//        }

        Map<String,Object> map= new HashMap<>();
        map.put("orderDto",orderDto);
        return ResultUtil.resultSuccess(map);
    }


    /**
     * 删除某个订单
     * @param id 订单号
     */
    @GetMapping("/delOrder")
    public Map<String,Object> delOrder(@RequestParam("id")int id){
        System.out.println("============="+id+"=================");
        if(orderService.delOrder(id)>0){
            return ResultUtil.resultCode(200,"删除订单成功");
        }
        return ResultUtil.resultCode(500,"删除订单失败");
    }
    /**
     * 发货功能
     * @param id 订单号
     * @param logisticsCompany 快递公司号，这里没有建立数据库，可能需要暂时前端规定实现一下（哪家公司对应哪个号）,我担心可能容易出错
     * @param logisticsNum 快递单号
     */
    @GetMapping("/deliverOrder")
    public Map<String,Object> delOrder(@RequestParam("id")int id,
                                      @RequestParam("logisticsCompany")int logisticsCompany,
                                      @RequestParam("logisticsNum")String logisticsNum){
        System.out.println("============="+id+"=================");
        if(orderService.deliverItem(id,logisticsCompany,logisticsNum)>0){
            return ResultUtil.resultCode(200,"发货成功");
        }
        return ResultUtil.resultCode(500,"发货失败");
    }

    /**
     * 获取用户订单信息
     * @param account 用户订单
     * @param page 页数，从1开始
     * @param pageSize 每页大小
     * @param orderStatus 状态（已发货等等）
     * @param beUserDelete 用户是否已经删除
     * @return 返回orderDtoList（列表）和total（总量）
     */
    @GetMapping("/getUserOrderList")
    public Map<String,Object> getUserOrderList(@RequestParam("account")String account,
                                               @RequestParam("page")int page,
                                               @RequestParam("pageSize")int pageSize,
                                               @RequestParam("orderStatus")String orderStatus,
                                               @RequestParam("beUserDelete")boolean beUserDelete){
        System.out.println("=========orderStatus,beUserDelete===========:"+orderStatus+" "+beUserDelete+"=========");
        List<OrderDto> orderDtoList = orderService.orderDtoList(account, page, pageSize,orderStatus,beUserDelete);
        for(OrderDto o:orderDtoList){
            List<OrderDetailDto> orderDetailDtoList = orderService.findOrderDetailDtoList(o.getOrderId());
            o.setOrderDetailDtoList(orderDetailDtoList);
        }
        //        for(int i=0;i<orderDtoList.size();i++){
//            List<OrderDetailDto> orderDetailDtoList = orderService.findOrderDetailDtoList(orderDtoList.get(i).getOrderId());
////            List<String> coverImgList = new ArrayList<>();
////            for(int j=0;j<orderDetailDtoList.size() && j<5;j++){
////                System.out.println("======orderDetailDtoList.get(j)====:"+orderDetailDtoList.get(j)+"=========");
////                String img = itemService.getBookCover(orderDetailDtoList.get(j).getBook().getisbn());
////                coverImgList.add(img);
////            }
////            System.out.println("=====coverImgList.size()====="+coverImgList.size()+"===================");
////            orderDtoList.get(i).setCoverImgList(coverImgList);
//        }
        Map<String,Object> map= new HashMap<>();
        map.put("orderDtoList",orderDtoList);
        int total = orderService.count(account, orderStatus, beUserDelete);
        map.put("total",total);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 修改订单状态
     * @param id 订单号
     * @param orderStatus 状态（例如代发货、已发货、已签收等等）
     */
    @GetMapping("/modifyOrderStatus")
    public Map<String,Object> modifyOrderStatus(@RequestParam("id")int id,
                                      @RequestParam("orderStatus")String orderStatus){
        System.out.println("========确认收货===="+id);
        if(orderService.modifyOrderStatus(id,orderStatus)>0){
            return ResultUtil.resultCode(200,"操作成功");
        }
        return ResultUtil.resultCode(500,"操作失败");
    }


    /**
     * 筛选订单范围
     * @param beginDate 起始日期
     * @param endDate 结束日期
     */
    @GetMapping("/date")
    public Map<String,Object> dateFilter(@RequestParam("beginDate")String beginDate,
                                         @RequestParam("endDate")String endDate) throws ParseException {
        // 将结束日期+1 便于sql操作
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        try {
////            Calendar calendar = Calendar.getInstance();
////            calendar.setTime(date1);
////            calendar.add(Calendar.DAY_OF_MONTH, 1);
////            date1 = calendar.getTime();
////        } catch (Exception e){
////            System.out.println(e);
////        }
        Map<String,Object> map = new HashMap<>();
        List<OrderStatistic> orderStatistic = orderService.getOrderStatistic(beginDate, endDate);
        map.put("orderStatistic",orderStatistic);
        return ResultUtil.resultSuccess(map);
    }


}
