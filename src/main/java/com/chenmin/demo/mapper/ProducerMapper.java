package com.store.mapper;

import com.store.entity.book.Producer;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerMapper {
    int addProducer(Producer producer);
    int deleteProducer(int id);
    int modifyProducer(Producer producer);
    int modifyIsShow(int id);
    int getProducerCount();
    List<Producer> getProducerByPage(int page,int pageSize);
    List<String> getProducerNames();
    Producer getProducerById(int id);
    Producer getProducerByName(String name);
}
