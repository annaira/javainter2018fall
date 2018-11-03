package org.redischool.fall2018project.usecases.shoppingcart;

import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

public class Product {
    private final String name;
    private final double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("name", name).toString();
    }

    public double getPrice() {
        return price;
    }

    public ItemDto toProductDto(){
        return new ItemDto(name, price, 0);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price);
    }
}
