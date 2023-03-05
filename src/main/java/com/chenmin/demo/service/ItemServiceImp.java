package com.store.service;

import com.store.entity.book.*;
import com.store.entity.dto.OrderItemDto;
import com.store.mapper.ItemMapper;
import com.store.service.imp.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("firstItem")
public class ItemServiceImp implements ItemService {
    @Autowired
    ItemMapper itemMapper;

    @Override
    public int addItem(Item item) {
        int result = itemMapper.addItem(item);
        if(result>0){
//            int bookId = itemMapper.get(book.getisbn());
//            System.out.println("============bookId===============:"+bookId);
//            book.setId(bookId);
            System.out.println("=======item：======="+item.toString()+"============");
//            redisTemplate.opsForValue().set(book_prefix+book.getId(),book);
//            redisTemplate.opsForValue().set(book_stock+book.getId(),book.getStock());
//            redisTemplate.opsForZSet().add(bookList_prefix,book,book.getRank());
        }
        return result;
    }

    @Override
    public int modifyItem(Item item) {
        Item i = itemMapper.getItem(item.getId());
        int result = itemMapper.modifyItem(item);
//        if(result>0){
//            redisTemplate.opsForValue().set(book_prefix+book.getId(),book);
//            redisTemplate.opsForValue().set(book_stock+book.getId(),book.getStock());
////            redisTemplate.opsForZSet().remove(book1);
//            redisTemplate.opsForZSet().add(bookList_prefix,book,book.getRank());
//        }
        return result;
    }

    @Override
    public int modifyItemPut(int id, boolean put) {
        Item i = itemMapper.getItem(id);
        int result = itemMapper.modifyItemPut(id,put);
//        if(result>0){
//            Book book = bookMapper.getBook(id);
//            redisTemplate.opsForValue().set(book_prefix+book.getId(),book);
////            redisTemplate.opsForZSet().remove(book1);
//            redisTemplate.opsForZSet().add(bookList_prefix,book,book.getRank());
//        }
        return result;
    }

    @Override
    public int deleteItem(int id) {
        int result = itemMapper.deleteItem(id);
//        if(result>0){
//            if(redisTemplate.hasKey(book_prefix+id)){
//                redisTemplate.delete(book_prefix+id);
//            }
//            if(redisTemplate.hasKey(book_stock+id)){
//                redisTemplate.delete(book_stock+id);
//            }
////            redisTemplate.opsForZSet().remove(bookMapper.getBook(id));
//        }
        return result;
    }

    @Override
    public List<Item> getItems() {
        return itemMapper.getItems();
    }

    @Override
    public List<Item> getItemsByPage(int page, int pageSize) {
        int start = (page-1)*pageSize;
//        if(redisTemplate.hasKey(bookList_prefix)){
//            System.out.println("======从缓存中获取图书集合=======");
//            Set range = redisTemplate.opsForZSet().range(bookList_prefix, start, start + pageSize);
//            List<Book> bookList = new ArrayList<>(range);
//            return bookList;
//        }else {
//            return bookMapper.getBooksByPage(start,pageSize);
//        }
        return itemMapper.getItemsByPage(start,pageSize);
    }

    @Override
    public List<Item> getItemsByPageBySearch(int page, int pageSize, String name){
        int start = (page-1)*pageSize;
//        if(redisTemplate.hasKey(bookList_prefix)){
//            System.out.println("======从缓存中获取图书集合=======");
//            Set range = redisTemplate.opsForZSet().range(bookList_prefix, start, start + pageSize);
//            List<Book> bookList = new ArrayList<>(range);
//            return bookList;
//        }else {
//            return bookMapper.getBooksByPage(start,pageSize);
//        }
        return itemMapper.getItemsByPageBySearch(start,name,page,pageSize);
    }
    @Override
    public List<OrderItemDto> getBatchItemList(int[] ids) {
        return itemMapper.getBatchItemList(ids);
    }

    @Override
    public List<OrderItemDto> getOneItemList(int[] ids) {
        return itemMapper.getOneItemList(ids);
    }

    @Override
    public List<Item> getNewPutItemList(int page, int pageSize) {
        int start = (page-1)*pageSize;
        return itemMapper.getNewPutItemList(start,pageSize);
    }

    @Override
    public Item getItem(int id) {
        return itemMapper.getItem(id);
    }

    @Override
    public List<Item> getProducerItems(String producerName) {
        return itemMapper.getProducerItems(producerName);
    }

    @Override
    public int getItemCount() {
        return itemMapper.getItemCount();
    }
    @Override
    public int getItemCountBySearch(String name) {
        return itemMapper.getItemCountBySearch(name);
    }
}
