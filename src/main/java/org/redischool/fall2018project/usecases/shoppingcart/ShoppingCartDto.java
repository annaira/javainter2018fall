package org.redischool.fall2018project.usecases.shoppingcart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDto {

    private String id;

    private List<ItemDto> items = new ArrayList<>();

    public ShoppingCartDto() {

    }

    public ShoppingCartDto(String id, List<ShoppingCart.Item> items) {
        this.id = id;
        for (ShoppingCart.Item item : items) {
            this.items.add(new ItemDto(item.getProduct().getName(), item.getProduct().getPrice(), item.getQuantity()));
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }
}
