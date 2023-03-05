package com.store.service.imp;

import com.store.entity.book.Producer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProducerService {

    int addProducer(Producer producer);

    int deleteProducer(int id);

    int modifyProducer(Producer producer);

    int modifyIsShow(int id);

    int getProducerCount();

    List<Producer> getProducerByPage(int page, int pageSize);

    List<String> getProducerNames();

    Producer getProducerById(int id);

    Producer getProducerByName(String name);
}
