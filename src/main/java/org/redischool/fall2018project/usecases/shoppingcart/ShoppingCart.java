package org.redischool.fall2018project.usecases.shoppingcart;

import com.google.common.collect.ImmutableList;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.google.common.base.MoreObjects.toStringHelper;

public class ShoppingCart {
    private final Map<Product, Item> items = new LinkedHashMap<>();

    public List<Item> getItems() {
        return ImmutableList.copyOf(items.values());
    }

    ShoppingCart(){

    }

    ShoppingCart add(Product product, int quantity) {
        if (!items.containsKey(product)) {
            items.put(product, new Item(product, quantity));
        } else {
            Item existingItem = items.get(product);
            items.put(product, existingItem.plus(quantity));
        }
        return this;
    }

    public double total() {
        double totalPrice = 0;
       for(Item item: getItems()){
           totalPrice += item.product.getPrice() * item.getQuantity();
       }
           return totalPrice;
    }

    public void deleteItem(Product product) {
        items.remove(product);
    }

    public void clear() {
        items.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingCart that = (ShoppingCart) o;
        return Objects.equals(items, that.items);
    }

    @Override
    public int hashCode() {
        return Objects.hash(items);
    }

    @Override
    public String toString() {
        return toStringHelper(this).add("items", this.getItems()).toString();
    }

    public double getTotal() {
        List<Item> items = getItems(); // 1 - 4

        double total = 0;
        for (Item item: items) {
            total = total + item.getQuantity() * item.getProduct().getPrice();
        }

        return total;

    }

    // 1. Product product = new Product( "Banana", $10 )
    // 2. Item item  = new Item(product, 2)
    // 3. Item item2 = new Item(new Product("Orange", $5), 1)
    // 4. ShoppingCart cart = new ShoppingCart.setItems(item, item2)
    // 5. cart.getTotal() -> $25
    static class Item {
        private final Product product;
        private final int quantity;

        public Item(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
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
    }
}
