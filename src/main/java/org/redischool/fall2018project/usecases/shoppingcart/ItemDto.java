package org.redischool.fall2018project.usecases.shoppingcart;

import java.util.Objects;

public class ItemDto {

    private String name;
    private double price;
    private int quantity;

    public ItemDto() {
    }

    public ItemDto(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public ShoppingCart.Item toItem() {
        Product product = new Product(name, price);
        return new ShoppingCart.Item(product, quantity);
    }

    public static ItemDto of(ShoppingCart.Item item) {
        return new ItemDto(item.getProduct().getName(), item.getProduct().getPrice(), item.getQuantity());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemDto itemDto = (ItemDto) o;
        return Double.compare(itemDto.price, price) == 0 &&
                quantity == itemDto.quantity &&
                Objects.equals(name, itemDto.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, price, quantity);
    }

    @Override
    public String toString() {
        return "ItemDto{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
