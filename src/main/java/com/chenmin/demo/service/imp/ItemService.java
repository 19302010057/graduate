package com.store.service.imp;

import com.store.entity.book.*;
import com.store.entity.dto.OrderItemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ItemService {

    int addItem(Item item);

    int modifyItem(Item item);

    int modifyItemPut(int id, boolean put);

    int deleteItem(int id);

    List<Item> getItems();

    List<Item> getItemsByPage(int page, int pageSize);

    List<Item> getItemsByPageBySearch(int page, int pageSize, String name);

    List<OrderItemDto> getBatchItemList(int[] ids);

    List<OrderItemDto> getOneItemList(int[] ids);

    List<Item> getNewPutItemList(int page, int pageSize);

    Item getItem(int id);

    List<Item> getProducerItems(String producerName);

    int getItemCount();

    int getItemCountBySearch(String name);
}
