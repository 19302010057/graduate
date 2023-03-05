package com.store.entity.book;

import org.springframework.web.multipart.MultipartFile;

public class ItemDto {
    private Item item;
    private MultipartFile file;
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "item=" + item +
                ", file=" + file +
                '}';
    }


}
