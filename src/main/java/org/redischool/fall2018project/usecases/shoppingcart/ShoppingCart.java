package org.redischool.fall2018project.usecases.shoppingcart;

import com.google.common.collect.ImmutableList;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

@Document
public class ShoppingCart {

    @Id
    private String id;

    private final Map<String, Item> items = new LinkedHashMap<>();

    public List<Item> items() {
        return ImmutableList.copyOf(items.values());
    }

    ShoppingCart add(Product product, int quantity) {
        if (!items.containsKey(product.getName())) {
            items.put(product.getName(), new Item(product, quantity));
        } else {
            Item existingItem = items.get(product.getName());
            items.put(product.getName(), existingItem.plus(quantity));
        }
        return this;
    }

    public double total() {
        double totalprice = 0;
        for (Item item : items()) {
            totalprice += item.product.getPrice();
        }
        return totalprice;
    }

    public String getId() {
        return id;
    }

    public ShoppingCartDto toShoppingCartDto() {
        return new ShoppingCartDto(id, items());
    }

    static class Item {
        private final Product product;
        private final int quantity;

        public Item(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Item) {
                Item other = (Item) obj;
                return Objects.equals(product, other.product) && quantity == other.quantity;
            } else {
                return false;
            }
        }

        @Override
        public String toString() {
            return toStringHelper(this).add("product", product).add("quantity", quantity).toString();
        }

        private Item plus(int quantity) {
            return new Item(product, this.quantity + quantity);
        }

        @Override
        public int hashCode() {
            return Objects.hash(product, quantity);
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
