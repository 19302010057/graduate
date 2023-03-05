package com.store.service;

import com.store.entity.book.Producer;
import com.store.mapper.ProducerMapper;
import com.store.service.imp.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("firstProducer")
public class ProducerServiceImp implements ProducerService {

    @Autowired
    ProducerMapper producerMapper;

    @Override
    public int addProducer(Producer producer) {
        int result = producerMapper.addProducer(producer);
        return result;
    }

    @Override
    public int deleteProducer(int id) {
        int result = producerMapper.deleteProducer(id);
        return result;
    }

    @Override
    public int modifyProducer(Producer producer) {
        int result = producerMapper.modifyProducer(producer);
        return result;
    }

    @Override
    public int modifyIsShow(int id) {
        int result =producerMapper.modifyIsShow(id);
        return result;
    }

    @Override
    public int getProducerCount() {
        return producerMapper.getProducerCount();
    }

    @Override
    public List<Producer> getProducerByPage(int page, int pageSize) {
        int start = (page-1)*pageSize;
        System.out.println(start+":"+pageSize);
        return producerMapper.getProducerByPage(start,pageSize);
    }

    @Override
    public List<String> getProducerNames() {
        return producerMapper.getProducerNames();
    }

    @Override
    public Producer getProducerById(int id) {
        return producerMapper.getProducerById(id);
    }

    @Override
    public Producer getProducerByName(String name) {
        return producerMapper.getProducerByName(name);
    }
}
