package com.store.controller;

import com.store.entity.book.Producer;
import com.store.service.imp.ItemService;
import com.store.service.imp.ProducerService;
import com.store.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping(value = "/producer")
public class ProducerController {

    @Autowired
    @Qualifier("firstProducer")
    ProducerService producerService;

    @Autowired
    @Qualifier("firstItem")
    ItemService itemService;


    /**
     * 添加制造商
     * @param producer 制造商
     */
    @PostMapping("/admin/addProducer")
    public Map<String,Object> addProducer(@RequestBody Producer producer){
        Map<String,Object> map = new HashMap<>();
        System.out.println(producer.isShowProducer());
        System.out.println(producer.toString());
        if(producerService.addProducer(producer)>0){
            return ResultUtil.resultSuccess(map);
        }
        return ResultUtil.resultError(map);
    }

    /**
     * 获取制造商列表
     * @param page
     * @param pageSize
     */
    @GetMapping("/getProducerList")
    public Map<String,Object> getProducerList(@RequestParam(value = "page")int page,
                                             @RequestParam(value = "pageSize")int pageSize){
        Map<String,Object> map = new HashMap<>();
        List<Producer> producerList = producerService.getProducerByPage(page,pageSize);
        int num;
        for(int i=0;i<producerList.size();i++){
            num = itemService.getProducerItems(producerList.get(i).getName()).size();
            producerList.get(i).setNum(num);
        }
        int total = producerService.getProducerCount();
        map.put("total",total);
        map.put("producerList",producerList);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 获取所有制造商名
     */
    @GetMapping("/getProducerNames")
    public Map<String,Object> getProducerNames(){
        Map<String,Object> map = new HashMap<>();
        List<String> producerNames = producerService.getProducerNames();
        map.put("producerList",producerNames);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 获取某个制造商
     * @param id 制造商号
     */
    @GetMapping("/getProducer")
    Map<String,Object> getEditProducer(@RequestParam int id){
        Map<String,Object> map = new HashMap<>();
        Producer producer = producerService.getProducerById(id);
        map.put("producer",producer);
        return ResultUtil.resultSuccess(map);
    }

    /**
     * 修改某个制造商
     * @param producer 修改后的制造商对象
     */
    @PostMapping("/modifyProducer")
    Map<String,Object> modifyProducer(@RequestBody Producer producer){
        Map<String,Object> map = new HashMap<>();
        if(producerService.modifyProducer(producer)>0){
            System.out.println("修改成功");
            return ResultUtil.resultSuccess(map);
        }
        return ResultUtil.resultError(map);
    }

    /**
     * 修改某个制造商为可见或不可见（取反操作）
     * @param id 制造商id
     */
    @GetMapping("/modifyShowProducer")
    Map<String,Object> modifyIsShow(@RequestParam int id){
        if(producerService.modifyIsShow(id)>0){
            System.out.println("修改成功");
            return ResultUtil.resultCode(200,"修改成功！");
        }
        return ResultUtil.resultCode(500,"修改失败");
    }

    /**
     * 删除某个制造商
     * @param id 制造商id
     */
    @GetMapping("/delProducer")
    Map<String,Object> delProducer(@RequestParam int id){
        if(producerService.deleteProducer(id)>0){
            System.out.println("删除成功");
            return ResultUtil.resultCode(200,"删除成功");
        }
        return ResultUtil.resultCode(500,"删除失败");
    }

}
