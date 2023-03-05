package com.store.controller;

import com.store.entity.book.*;
import com.store.service.imp.ItemService;

import com.store.util.FileUtil;
import com.store.util.ResultUtil;
import com.store.util.UploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

@Controller
@ResponseBody
@RequestMapping(value = "/item")
public class ItemController {

    private String basePath="C://mall_web//";
    private String bookPath="static/";
    @Autowired
    @Qualifier("firstItem")
    ItemService itemService;

    /**
     * 添加一个商品
     */
    @PostMapping("/addItem")
    public Map<String,Object> addItem(@RequestParam(value = "image") MultipartFile file,
                                      @RequestParam(value = "writer") String writer,
                                      @RequestParam(value = "name") String name,
                                      @RequestParam(value = "marketprice") double marketprice,
                                      @RequestParam(value = "price") double price,
                                      @RequestParam(value = "producer") String producer,
                                      @RequestParam(value = "description") String description,
                                      @RequestParam(value = "put") boolean put
                                        ){
        Item item = new Item();
        item.setWriter(writer);
        item.setName(name);
        item.setPrice(price);
        item.setMarketPrice(marketprice);
        item.setBirthday(new Timestamp( new Date().getTime()));
        item.setProducer(producer);
        item.setDescription(description);
        item.setPut(put);
        String path = basePath;
        String imgName = UploadUtil.uploadFile(file,path);
        item.setCoverImg(imgName);
        itemService.addItem(item);
        return ResultUtil.resultCode(200,"上传成功");
    }
    /**
     * 修改商品
     */
    @PostMapping("/modifyItem")
    public Map<String,Object> modifyItem(@RequestParam(value = "image") MultipartFile file,
                                         @RequestParam(value = "writer") String writer,
                                         @RequestParam(value = "id") int id,
                                         @RequestParam(value = "name") String name,
                                         @RequestParam(value = "marketprice") double marketprice,
                                         @RequestParam(value = "price") double price,
                                         @RequestParam(value = "producer") String producer,
                                         @RequestParam(value = "description") String description,
                                         @RequestParam(value = "put") boolean put
                                         ){
        Item item = new Item();
        item.setId(id);
        item.setWriter(writer);
        item.setName(name);
        item.setPrice(price);
        item.setMarketPrice(marketprice);
        item.setBirthday(new Timestamp( new Date().getTime()));
        item.setProducer(producer);
        item.setDescription(description);
        item.setPut(put);
        System.out.println("修改起作用了");
        FileUtil.delOneImg(basePath+item.getCoverImg());
        String path = basePath;
        String imgName = UploadUtil.uploadFile(file,path);
        item.setCoverImg(imgName);
        itemService.modifyItem(item);
        System.out.println(item.toString());
        return ResultUtil.resultCode(200,"修改成功");
    }

    /**
     * 修改商品是否上架
     * @param itemId 商品Id
     * @param put true代表上架
     */
    @GetMapping("/modifyPut")
    public Map<String,Object> handlePut(@RequestParam(value = "itemId")int itemId,
                                        @RequestParam(value = "put")boolean put){
        System.out.println(itemId);
        System.out.println(put);
        itemService.modifyItemPut(itemId,put);
        return ResultUtil.resultCode(200,"修改成功");
    }
    /**
     * 按页获取商品
     * @param page 当前页数，从1开始
     * @param pageSize 页数大小
     * @return 包含itemList（列表）和total（总量）
     */
    @GetMapping(value = "/getItemList")
    public Map<String, Object> getItemList(@RequestParam(value = "page")int page,
                                           @RequestParam(value = "pageSize")int pageSize){
        System.out.println("=========================按页得到商品的集合========================");
        Map<String, Object> map = new HashMap<>();
        List<Item> itemList = itemService.getItemsByPage(page, pageSize);
        map.put("itemList",itemList);
        int total = itemService.getItemCount();
        map.put("total",total);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 按页获取商品
     * @param page 当前页数，从1开始
     * @param pageSize 页数大小
     * @return 包含itemList（列表）和total（总量）
     */
    @GetMapping(value = "/getItemSearch")
    public Map<String, Object> getItemSearch(@RequestParam(value = "page")int page,
                                             @RequestParam(value = "pageSize")int pageSize,
                                             @RequestParam(value = "name")String name){
        System.out.println("=========================按页得到商品的集合========================");
        Map<String, Object> map = new HashMap<>();
        List<Item> itemList = itemService.getItemsByPageBySearch(page, pageSize, name);
        map.put("itemList",itemList);
        int total = itemService.getItemCountBySearch(name);
        map.put("total",total);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 获取商品某个商品
     * @param id 商品id
     */
    @GetMapping("/getItem")
    Map<String,Object> getItem(@RequestParam int id){
        System.out.println("==========查询某个商品的数据集合==============");
        Map<String,Object> map = new HashMap<>();
        System.out.println("id:"+id);
        Item item = itemService.getItem(id);
        map.put("item",item);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 删除某个商品
     * @param itemId 商品id
     */
    @GetMapping("/delItem")
    public Map<String,Object> delItem(@RequestParam(value = "itemId")int itemId){
        System.out.println("删除起作用");
        itemService.deleteItem(itemId);
        return ResultUtil.resultCode(200,"删除成功");
    }

}
