package org.redischool.fall2018project.usecases.shoppingcart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCartDto {

    private List<ItemDto> items;

    public ShoppingCartDto() {
    }

    public ShoppingCart toShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();

        for (ItemDto itemDto: items) {
            ShoppingCart.Item item = itemDto.toItem();
            shoppingCart.add(item.getProduct(), item.getQuantity());
        }

        return shoppingCart;
    }

    public static ShoppingCartDto of(ShoppingCart shoppingCart) {
        ShoppingCartDto shoppingCartDto = new ShoppingCartDto();
        List<ItemDto> newItems = new ArrayList<>();
        for(ShoppingCart.Item item : shoppingCart.getItems()){
            newItems.add(ItemDto.of(item));
        }
        shoppingCartDto.setItems(newItems);
        return shoppingCartDto;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ShoppingCartDto{" +
                "items=" + items +
                '}';
    }
}
