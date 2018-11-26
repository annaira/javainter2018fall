package org.redischool.fall2018project.usecases.shoppingcart;

public interface ShoppingCartRepository {
    void persistShoppingCart(ShoppingCart shoppingCart);

    ShoppingCart fetchShoppingCart();

    ShoppingCart fetchShoppingCart(String user);

    void persistShoppingCart(String user, ShoppingCart shoppingCart);
}
